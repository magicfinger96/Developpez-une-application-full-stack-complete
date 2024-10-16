import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { PostsService } from '../../services/posts.service';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { PostCardComponent } from '../post-card/post-card.component';
import { RouterLink } from '@angular/router';
import { Post } from '../../interfaces/post.interface';
import { Subscription } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * Component of the feed page.
 */
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
    RouterLink,
  ],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss',
})
export class FeedComponent implements OnInit {
  private postsService: PostsService = inject(PostsService);
  private matSnackBar: MatSnackBar = inject(MatSnackBar);

  public sortOrder: string = 'desc';
  public posts!: Post[];

  public ngOnInit(): void {
    this.fetchFeed(this.sortOrder);
  }

  /**
   * Order the feed from the latest posts to the oldest or reverse.
   */
  public onSortClicked(): void {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.fetchFeed(this.sortOrder);
  }

  /**
   * Fetch the feed.
   * 
   * @param sortOrder feed sorting order.
   */
  private fetchFeed(sortOrder: string): void {
    this.postsService.feed(sortOrder).subscribe({
      next: (posts: Post[]) => {
        this.posts = posts;
      },
      error: (errorResponse) =>
        this.matSnackBar.open(
          `Une erreur est survenue lors de la récupération des articles.`,
          'Close',
          { duration: 3000 }
        ),
    });
  }
}
