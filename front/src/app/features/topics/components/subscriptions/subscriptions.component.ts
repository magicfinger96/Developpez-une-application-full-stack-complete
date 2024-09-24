import { Component, inject, OnInit } from '@angular/core';
import { TopicCardComponent } from '../topic-card/topic-card.component';
import { CommonModule } from '@angular/common';
import { TopicsService } from '../../services/topics.service';
import { Observable } from 'rxjs';
import { Topic } from '../../interfaces/topic.interface';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * Component of the subscriptions section.
 */
@Component({
  selector: 'app-subscriptions',
  standalone: true,
  imports: [TopicCardComponent, CommonModule],
  templateUrl: './subscriptions.component.html',
  styleUrl: './subscriptions.component.scss',
})
export class SubscriptionsComponent implements OnInit {
  private topicsService: TopicsService = inject(TopicsService);
  private matSnackBar: MatSnackBar = inject(MatSnackBar);
  public subscribedTopics: Topic[] = [];

  ngOnInit() {
    this.loadTopics();
  }

  /**
   * Load all the subscribed topics.
   */
  private loadTopics(): void {
    this.topicsService.subscriptions().subscribe({
      next: (topics: Topic[]) => {
        this.subscribedTopics = topics;
      },
      error: () => {
        this.matSnackBar.open(
          'Une erreur est survenue lors de la récupération des abonnements.',
          'Close',
          { duration: 3000 }
        );
      },
    });
  }

  /**
   * Called when a topic is unsubscribed to reload the subscriptions.
   */
  public onTopicUnsubscribed(): void {
    this.loadTopics();
  }
}
