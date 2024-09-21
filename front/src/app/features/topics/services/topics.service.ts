import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';
import { MessageResponse } from '../../../core/interfaces/message-response.interface';

/**
 * Service handling the topics.
 */
@Injectable({
  providedIn: 'root',
})
export class TopicsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/topics';

  /**
   * Makes an API call to fetch all the topics.
   *
   * @returns an observable containing a list of Topic.
   */
  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }

  /**
   * Makes an API call to fetch the subscriptions of the user.
   *
   * @returns an observable containing a list of Topic.
   */
  public subscriptions(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/subscribed`);
  }

  /**
   * Makes an API call to subscribe the user to a topic.
   *
   * @param topicId the topic id.
   * @returns an observable emitting the response to the call.
   */
  public subscribe(topicId: number): Observable<MessageResponse> {
    return this.httpClient.post<MessageResponse>(
      `${this.pathService}/${topicId}/subscribe`,
      null
    );
  }

  /**
   * Makes an API call to unsubscribe the user from a topic.
   *
   * @param topicId the topic id.
   * @returns an observable emitting the response to the call.
   */
  public unsubscribe(topicId: number): Observable<MessageResponse> {
    return this.httpClient.delete<MessageResponse>(
      `${this.pathService}/${topicId}/subscribe`
    );
  }
}
