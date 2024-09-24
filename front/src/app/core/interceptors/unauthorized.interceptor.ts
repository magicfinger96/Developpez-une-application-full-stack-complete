import {
  HttpErrorResponse,
  HttpHandlerFn,
  HttpRequest,
} from '@angular/common/http';
import { SessionService } from '../services/session.service';
import { inject } from '@angular/core';
import { catchError, EMPTY, throwError } from 'rxjs';
import { Router } from '@angular/router';

/**
 * Checks the response error code, redirects to the home page if it's an unauthorized one (401).
 *
 * @param request request we intercepting the response.
 * @param next next processing step.
 * @returns the modified request.
 */
export function unauthorizedInterceptor(
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
) {
  let sessionService: SessionService = inject(SessionService);
  let router: Router = inject(Router);

  return next(request).pipe(
    catchError((err: HttpErrorResponse) => {
      if (err.status === 401 && router.url != '/login') {
        router.navigate(['/']);
        sessionService.logOut();
        return EMPTY;
      }
      return throwError(() => err);
    })
  );
}
