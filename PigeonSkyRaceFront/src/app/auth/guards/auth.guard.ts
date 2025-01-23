import {CanActivateFn, Router} from '@angular/router';
import {TokenService} from '../services/token.service';
import {inject} from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const tokenService = inject(TokenService);

  if (tokenService.isTokenValid()) {
    return true;
  }

  router.navigate(['/auth/login'], {
    queryParams: { returnUrl: state.url },
  });
  return false;
};
