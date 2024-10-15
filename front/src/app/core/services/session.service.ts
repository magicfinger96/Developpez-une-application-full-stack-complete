import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

/**
 * Service handling the user session.
 */
@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;

  private isLoggedSubject 

  constructor(){
    this.isLogged = localStorage.getItem('token') != null;
    this.isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  }

  /**
   * Returns whether the user is logged or not as an observable.
   * 
   * @returns the observable.
   */
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  /**
   * Log the user in.
   * Save the JWT in the local storage.
   * 
   * @param token token used to make authenticated API calls.
   */
  public logIn(token: string): void {
    localStorage.setItem('token', token);
    this.isLogged = true;
    this.next();
  }

  /**
   * Log the user out.
   * Remove the JWT from the local storage.
   */
  public logOut(): void {
    localStorage.removeItem('token');
    this.isLogged = false;
    this.next();
  }

  /**
   * Emits a log info to the observers.
   */
  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
