import { Component, Input } from '@angular/core';
import { Topic } from '../../interfaces/topic.interface';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-topic-card',
  standalone: true,
  imports: [MatButtonModule, CommonModule],
  templateUrl: './topic-card.component.html',
  styleUrl: './topic-card.component.scss',
})
export class TopicCardComponent {
  @Input() topic!: Topic;
}
