import { Component, inject, Input, OnInit } from '@angular/core';
import { Topic } from '../../interfaces/topic.interface';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { SubscriptionsService } from '../../services/subscriptions.service';
import { map, Observable, of } from 'rxjs';

/**
 * Component handling a topic card.
 */
@Component({
  selector: 'app-topic-card',
  standalone: true,
  imports: [MatButtonModule, CommonModule],
  templateUrl: './topic-card.component.html',
  styleUrl: './topic-card.component.scss',
})
export class TopicCardComponent implements OnInit {
  @Input() topic!: Topic;
  @Input() canSubscribe!: boolean;

  buttonText!: string;
  disabledButtonText!: string;

  private subscriptionsService: SubscriptionsService =
    inject(SubscriptionsService);
  public isDisabled$!: Observable<boolean>;

  ngOnInit() {
    this.buttonText = this.canSubscribe ? "S'abonner" : 'Se désabonner';
    this.disabledButtonText = this.canSubscribe ? 'Abonné(e)' : 'Désabonné(e)';

    if (this.canSubscribe) {
      this.refreshDisableState();
    } else {
      this.isDisabled$ = of(false);
    }
  }

  /**
   * Called when the card button is clicked.
   */
  onButtonClicked(): void {
    if (this.canSubscribe) {
      this.subscribe();
    } else {
      this.unsubscribe();
    }
  }

  /**
   * Subscribes the user to the topic.
   */
  private subscribe(): void {
    this.subscriptionsService
      .add({ topic_id: this.topic.id })
      .subscribe((_) => {
        this.refreshDisableState();
      });
  }

  /**
   * Unsubscribes the user from the topic.
   */
  private unsubscribe(): void {
    this.subscriptionsService.remove(this.topic.id).subscribe((_) => {
      this.refreshDisableState();
    });
  }

  /**
   * Enable or disable the button of the card.
   *
   * Enable it if the user is subscribed to the topic and he can unsubscribe.
   * Or if the user isn't subscribed to the topic and he can subscribe.
   */
  private refreshDisableState(): void {
    this.isDisabled$ = this.subscriptionsService.mine().pipe(
      map((subscriptions) => {
        let isSubscribed: boolean = subscriptions.find(
          (subscription) => subscription.topic_id === this.topic.id
        )
          ? true
          : false;
        return this.canSubscribe ? isSubscribed : !isSubscribed;
      })
    );
  }
}
