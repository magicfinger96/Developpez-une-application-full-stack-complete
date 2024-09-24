import { Component, inject } from '@angular/core';
import { TopicsService } from '../../services/topics.service';
import { TopicCardComponent } from '../topic-card/topic-card.component';
import { CommonModule } from '@angular/common';

/**
 * Component of the topics list page.
 */
@Component({
  selector: 'app-list',
  standalone: true,
  imports: [TopicCardComponent, CommonModule],
  templateUrl: './list.component.html',
  styleUrl: './list.component.scss',
})
export class ListComponent {
  private topicsService: TopicsService = inject(TopicsService);
  public list$ = this.topicsService.all();
}
