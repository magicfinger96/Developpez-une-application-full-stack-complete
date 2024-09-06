import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';
import { UpdateResponse } from '../interfaces/updateResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient) { }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  public update(form: FormData): Observable<UpdateResponse> {
    return this.httpClient.put<UpdateResponse>(`${this.pathService}/me`, form);
  }
}
