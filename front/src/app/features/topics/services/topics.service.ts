import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root',
})
export class TopicsService {
  httpClient = inject(HttpClient);
  private pathService = 'api/topics';

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }
}
