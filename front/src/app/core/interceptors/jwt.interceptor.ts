import { HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { SessionService } from '../services/session.service';
import { inject } from '@angular/core';

/**
 * Adds a JWT on the header.
 *
 * @param request request to modify.
 * @param next next processing step.
 * @returns the modified request.
 */
export function jwtInterceptor(
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
) {
  let sessionService: SessionService = inject(SessionService);

  if (sessionService.isLogged) {
    let token: string | null = localStorage.getItem('token');
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
  return next(request);
}
