import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/comment.interface';
import { CommentRequest } from '../interfaces/comment-request.interface';
import { MessageResponse } from '../../../core/interfaces/message-response.interface';

/**
 * Service handling the comments.
 */
@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/comments';

  /**
   * Fetch all the comment of a post.
   * @param postId id of the post.
   * @returns an array of Comment.
   */
  public all(postId: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(
      `${this.pathService}?post_id=${postId}`
    );
  }

  /**
   * Request a comment creation.
   * 
   * @param commentRequest data to request comment creation
   * @returns a successfull message or an error.
   */
  public send(commentRequest: CommentRequest): Observable<MessageResponse> {
    return this.httpClient.post<MessageResponse>(
      this.pathService,
      commentRequest
    );
  }
}
