import { Component, inject, OnInit } from '@angular/core';
import { TopicCardComponent } from '../topic-card/topic-card.component';
import { CommonModule } from '@angular/common';
import { TopicsService } from '../../services/topics.service';
import { SubscriptionsService } from '../../services/subscriptions.service';
import { forkJoin, map, Observable } from 'rxjs';
import { Topic } from '../../interfaces/topic.interface';

@Component({
  selector: 'app-subscriptions',
  standalone: true,
  imports: [TopicCardComponent, CommonModule],
  templateUrl: './subscriptions.component.html',
  styleUrl: './subscriptions.component.scss',
})
export class SubscriptionsComponent implements OnInit {
  private topicsService: TopicsService = inject(TopicsService);
  private subscriptionsService: SubscriptionsService =
    inject(SubscriptionsService);

  public topics$ = this.topicsService.all();
  public subscribedTopics$!: Observable<Topic[]>;

  ngOnInit() {
    this.loadTopics();
  }

  private loadTopics(): void {
    this.subscribedTopics$ = forkJoin([
      this.subscriptionsService.mine(),
      this.topicsService.all(),
    ]).pipe(
      map(([subscriptions, topics]) => {
        {
          return topics.filter((topic) =>
            subscriptions.find(
              (subscription) => subscription.topic_id === topic.id
            )
          );
        }
      })
    );
  }
}
