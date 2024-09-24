import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PostsService } from '../../services/posts.service';
import { Post } from '../../interfaces/post.interface';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { CommentsComponent } from "../comments/comments.component";

/**
 * Component of the Post detail page.
 */
@Component({
  selector: 'app-details',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    RouterModule,
    CommentsComponent
],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss',
})
export class DetailsComponent implements OnInit {
  private route: ActivatedRoute = inject(ActivatedRoute);
  private postsService: PostsService = inject(PostsService);

  postId: string;
  public post: Post | undefined;

  constructor() {
    this.postId = this.route.snapshot.paramMap.get('id')!;
  }

  public ngOnInit(): void {
    this.fetchPost();
  }

  /**
   * Fetch the post data.
   */
  private fetchPost(): void {
    this.postsService.detail(this.postId).subscribe((post: Post) => {
      this.post = post;
    });
  }
}
