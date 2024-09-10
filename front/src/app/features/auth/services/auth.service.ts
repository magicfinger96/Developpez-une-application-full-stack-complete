import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from '../../topics/interfaces/response.interface';
import { User } from '../../../core/interfaces/user.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'api/auth';

  httpClient: HttpClient = inject(HttpClient);

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  public update(form: FormData): Observable<Response> {
    return this.httpClient.put<Response>(`${this.pathService}/me`, form);
  }
}
