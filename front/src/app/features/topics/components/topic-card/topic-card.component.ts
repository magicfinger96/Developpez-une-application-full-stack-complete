import {
  Component,
  EventEmitter,
  inject,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { Topic } from '../../interfaces/topic.interface';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { TopicsService } from '../../services/topics.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageResponse } from '../../../../core/interfaces/message-response.interface';

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
  @Output() topicUnsubscribed = new EventEmitter<void>();

  buttonText!: string;

  private matSnackBar: MatSnackBar = inject(MatSnackBar);
  private topicsService: TopicsService = inject(TopicsService);

  ngOnInit() {
    this.buttonText = this.canSubscribe ? "S'abonner" : 'Se désabonner';
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
   * Subscribes the user to the topic
   * and refresh the button state if it succeeds.
   */
  private subscribe(): void {
    this.topicsService.subscribe(this.topic.id).subscribe({
      next: (response: MessageResponse) => {
        this.topic.subscribed = true;
        this.matSnackBar.open(response.message, 'Close', { duration: 3000 });
      },
      error: (errorResponse) =>
        this.matSnackBar.open(errorResponse.error.message, 'Close', {
          duration: 3000,
        }),
    });
  }

  /**
   * Unsubscribes the user from the topic.
   */
  private unsubscribe(): void {
    this.topicsService.unsubscribe(this.topic.id).subscribe({
      next: (response: MessageResponse) => {
        this.topicUnsubscribed.emit();
        this.matSnackBar.open(response.message, 'Close', { duration: 3000 });
      },
      error: (errorResponse) =>
        this.matSnackBar.open(errorResponse.error.message, 'Close', {
          duration: 3000,
        }),
    });
  }
}
