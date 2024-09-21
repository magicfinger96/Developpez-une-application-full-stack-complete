import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../interfaces/post.interface';
import { MessageResponse } from '../../../core/interfaces/message-response.interface';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/posts';

  public feed(order: string = 'desc'): Observable<Post[]> {
    return this.httpClient.get<Post[]>(
      `${this.pathService}?sort=created_at&order=${order}`
    );
  }

  public detail(id: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  public create(form: FormData): Observable<MessageResponse> {
    return this.httpClient.post<MessageResponse>(this.pathService, form);
  }
}