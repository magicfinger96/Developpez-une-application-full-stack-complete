import { Component, inject, Input } from '@angular/core';
import { CommentsService } from '../../services/comments.service';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Comment } from '../../interfaces/comment.interface';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { CommentRequest } from '../../interfaces/commentRequest.interface';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageResponse } from '../../../../core/interfaces/message-response.interface';

/**
 * Component of the comments section.
 */
@Component({
  selector: 'app-comments',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './comments.component.html',
  styleUrl: './comments.component.scss',
})
export class CommentsComponent {
  private commentsService: CommentsService = inject(CommentsService);
  private matSnackBar: MatSnackBar = inject(MatSnackBar);
  private fb: FormBuilder = inject(FormBuilder);

  @Input() postId!: number;

  public comments$!: Observable<Comment[]>;
  public commentForm!: FormGroup;

  constructor() {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    this.fetchComments();
  }

  /**
   * Request a comment creation.
   */
  public sendMessage(): void {
    const commentRequest = {
      postId: this.postId,
      message: this.commentForm.value.message,
    } as CommentRequest;

    this.commentsService.send(commentRequest).subscribe({
      next: (response: MessageResponse) => {
        this.initMessageForm();
        this.fetchComments();
      },
      error: () =>
        this.matSnackBar.open("Une erreur est survenue !", 'Close', {
          duration: 3000,
        }),
    });
  }

  /**
   * Initializes the form.
   */
  private initMessageForm() {
    this.commentForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }

  /**
   * Fetch all the comments of a post.
   */
  private fetchComments(): void {
    this.comments$ = this.commentsService.all(this.postId);
  }
}
