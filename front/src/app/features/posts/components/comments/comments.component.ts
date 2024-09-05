import { Component, inject, Input } from '@angular/core';
import { CommentsService } from '../../services/comments.service';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Comment } from '../../interfaces/comment.interface';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';

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
    const comment = {
      post_id: this.postId,
      author_id: 1, // TODO this.sessionService.user?.id,
      message: this.commentForm.value.message,
    } as Comment;

    this.commentsService.send(comment).subscribe((_) => {
      this.initMessageForm();
    });
  }

  private initMessageForm() {
    this.commentForm = this.fb.group({
      message: ['', [Validators.required, Validators.min(10)]],
    });
  }
}
