import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../../core/interfaces/user.interface';
import { AuthSuccess } from '../interfaces/auth-success.interface';
import { RegisterRequest } from '../interfaces/register-request.interface';
import { LoginRequest } from '../interfaces/login-request.interface';
import { MessageResponse } from '../../../core/interfaces/message-response.interface';

/**
 * Service handling the user authentication.
 */
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'api/auth';

  httpClient: HttpClient = inject(HttpClient);

  /**
   * Fetch the authenticated user.
   * 
   * @returns a user.
   */
  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  /**
   * Update the authenticated user informations.
   * 
   * @param form the new information to save.
   * @returns a generic response containing a successfull message.
   */
  public update(form: FormData): Observable<MessageResponse> {
    return this.httpClient.put<MessageResponse>(`${this.pathService}/me`, form);
  }

  /**
   * Register a user.
   * 
   * @param registerRequest data to create the user.
   * @returns an AuthSuccess object containing the JWT.
   */
  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(
      `${this.pathService}/register`,
      registerRequest
    );
  }


  /**
   * Log a user in.
   * 
   * @param loginRequest data to log in.
   * @returns an AuthSuccess object containing the JWT.
   */
  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(
      `${this.pathService}/login`,
      loginRequest
    );
  }
}
