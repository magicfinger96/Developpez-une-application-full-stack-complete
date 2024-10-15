import { Routes } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { AuthComponent } from './auth.component';

/**
 * The routes of any authentication page.
 */
export const AUTH_ROUTES: Routes = [
  {
    path: '',
    component: AuthComponent,
    children: [
      {
        path: 'login',
        title: 'Connexion',
        component: LoginComponent,
        data: { title: 'Se connecter' },
      },
      {
        path: 'register',
        title: 'Inscription',
        component: RegisterComponent,
        data: { title: 'Inscription' },
      },
    ],
  },
];
