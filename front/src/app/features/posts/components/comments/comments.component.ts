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

@Component({
  selector: 'app-comments',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './comments.component.html',
  styleUrl: './comments.component.scss',
})
export class CommentsComponent {
  private commentsService: CommentsService = inject(CommentsService);
  private fb: FormBuilder = inject(FormBuilder);

  @Input() postId!: number;

  public comments$!: Observable<Comment[]>;
  public commentForm!: FormGroup;

  constructor() {
    this.initMessageForm();
  }

  public ngOnInit(): void {
    this.comments$ = this.commentsService.all(this.postId);
  }

  public sendMessage(): void {
    const commentRequest = {
      postId: this.postId,
      message: this.commentForm.value.message,
    } as CommentRequest;

    this.commentsService.send(commentRequest).subscribe((_) => {
      this.initMessageForm();
    });
  }

  private initMessageForm() {
    this.commentForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }
}
