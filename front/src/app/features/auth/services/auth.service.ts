import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../../core/interfaces/user.interface';
import { AuthSuccess } from '../interfaces/auth-success.interface';
import { RegisterRequest } from '../interfaces/register-request.interface';
import { LoginRequest } from '../interfaces/login-request.interface';
import { MessageResponse } from '../../../core/interfaces/message-response.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'api/auth';

  httpClient: HttpClient = inject(HttpClient);

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  public update(form: FormData): Observable<MessageResponse> {
    return this.httpClient.put<MessageResponse>(`${this.pathService}/me`, form);
  }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(
      `${this.pathService}/register`,
      registerRequest
    );
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(
      `${this.pathService}/login`,
      loginRequest
    );
  }
}
