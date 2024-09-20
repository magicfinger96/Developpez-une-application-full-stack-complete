import { Component, inject, OnInit } from '@angular/core';
import { TopicCardComponent } from '../topic-card/topic-card.component';
import { CommonModule } from '@angular/common';
import { TopicsService } from '../../services/topics.service';
import { Observable } from 'rxjs';
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

  public subscribedTopics$!: Observable<Topic[]>;

  ngOnInit() {
    this.loadTopics();
  }

  private loadTopics(): void {
    this.subscribedTopics$ = this.topicsService.subscriptions();
  }
  }
}
