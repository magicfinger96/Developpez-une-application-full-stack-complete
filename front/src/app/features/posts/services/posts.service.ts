import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FeedResponse } from '../interfaces/feedResponse.interface';

@Injectable({
  providedIn: 'root',
})
export class PostsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/posts';

  public feed(): Observable<FeedResponse> {
    return this.httpClient.get<FeedResponse>(this.pathService);
  }
}
