import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../interfaces/comment.interface';

@Injectable({
  providedIn: 'root',
})
export class CommentsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/comments';

  public all(postId: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(
      `${this.pathService}?post_id=${postId}`
    );
  }

  public send(comment: Comment): Observable<void> {
    return this.httpClient.post<void>(this.pathService, comment);
  } 
}
