import { Component, inject } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { PostCardComponent } from '../post-card/post-card.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [
    MatGridListModule,
    MatCardModule,
    CommonModule,
    MatButtonModule,
    ScrollingModule,
    PostCardComponent,
    RouterLink
  ],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss',
})
export class FeedComponent {
  private postsService: PostsService = inject(PostsService);

  public sortOrder : string = "desc";
  public feed$ = this.postsService.feed();

  public onSortClicked(): void {
    this.sortOrder = this.sortOrder === "asc" ? "desc" : "asc";
    this.feed$ = this.postsService.feed(this.sortOrder);
  }
}
