import { Component, inject } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { MatGridListModule } from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [MatGridListModule, MatCardModule, CommonModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss',
})
export class FeedComponent {
  private postsService: PostsService = inject(PostsService);

  public feed$ = this.postsService.feed();
}
