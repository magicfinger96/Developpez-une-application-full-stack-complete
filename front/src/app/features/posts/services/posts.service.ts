import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FeedResponse } from '../interfaces/feedResponse.interface';
import { Post } from '../interfaces/post.interface';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/posts';

  public feed(order : string = 'desc'): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.pathService}?sort=created_at&order=${order}`);
  }
}
