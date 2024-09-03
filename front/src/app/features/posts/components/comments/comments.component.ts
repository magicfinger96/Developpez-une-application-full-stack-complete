import { Component, inject, Input } from '@angular/core';
import { CommentsService } from '../../services/comments.service';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Comment } from '../../interfaces/comment.interface';

@Component({
  selector: 'app-comments',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './comments.component.html',
  styleUrl: './comments.component.scss',
})
export class CommentsComponent {
  private commentsService: CommentsService = inject(CommentsService);

  @Input() postId!: number;

  public comments$!: Observable<Comment[]>;

  public ngOnInit(): void {
    this.comments$ = this.commentsService.all(this.postId);
  }
}
