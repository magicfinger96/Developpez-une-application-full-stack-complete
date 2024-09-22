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
export class FeedComponent implements OnInit, OnDestroy {
  private postsService: PostsService = inject(PostsService);
  private feedSubscription: Subscription = new Subscription();
  private matSnackBar: MatSnackBar = inject(MatSnackBar);

  public sortOrder: string = 'desc';
  public posts!: Post[];

  public ngOnInit(): void {
    this.fetchFeed(this.sortOrder);
  }

  public ngOnDestroy(): void {
    this.feedSubscription.unsubscribe();
  }

  public onSortClicked(): void {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.fetchFeed(this.sortOrder);
  }

  private fetchFeed(sortOrder: string): void {
    this.feedSubscription.unsubscribe();
    this.feedSubscription = this.postsService.feed(sortOrder).subscribe({
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
