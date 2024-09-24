import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../interfaces/post.interface';
import { MessageResponse } from '../../../core/interfaces/message-response.interface';

/**
 * Service handling the posts.
 */
@Injectable({
  providedIn: 'root',
})
export class PostsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/posts';

  /**
   * Fetch the feed.
   *
   * @param order whether the feed should be sort from the older or most recent post.
   * @returns an array of Post.
   */
  public feed(order: string = 'desc'): Observable<Post[]> {
    return this.httpClient.get<Post[]>(
      `${this.pathService}?sort=creationDate&order=${order}`
    );
  }

  /**
   * Fetch a post.
   *
   * @param id id of the post.
   * @returns the Post.
   */
  public detail(id: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  /**
   * Create a post.
   *
   * @param post post-to-create data
   * @returns a successfull message or an error.
   */
  public create(post: Post): Observable<MessageResponse> {
    return this.httpClient.post<MessageResponse>(this.pathService, post);
  }
}
