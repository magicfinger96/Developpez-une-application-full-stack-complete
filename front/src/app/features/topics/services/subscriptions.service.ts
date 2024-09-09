import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from '../interfaces/subscription.interface';
import { Response } from '../interfaces/response.interface';
import { SubscriptionRequest } from '../interfaces/subscription-request.interface';

/**
 * Service for the subscriptions.
 */
@Injectable({
  providedIn: 'root',
})
export class SubscriptionsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/subscriptions';

  /**
   * Returns all the subscriptions of the user.
   * 
   * @returns an array of subscription.
   */
  public mine(): Observable<Subscription[]> {
    return this.httpClient.get<Subscription[]>(`${this.pathService}`);
  }

  /**
   * Adds a subscription to a topic.
   * 
   * @param request contains the topic id.
   * @returns an observable emitting the response to the call.
   */
  public add(request: SubscriptionRequest): Observable<Response> {
    return this.httpClient.post<Response>(`${this.pathService}`, request);
  }

  /**
   * Removes a subscription from a topic.
   * 
   * @param topicId the topic id.
   * @returns an observable emitting the response to the call.
   */
  public remove(topicId : number): Observable<Response> {
    return this.httpClient.delete<Response>(`${this.pathService}?topic_id=${topicId}`);
  }

}
