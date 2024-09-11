import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MenuLayoutComponent } from './core/components/menu-layout/menu-layout.component';
import { UnauthGuard } from './core/guards/unauth.guard';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', canActivate: [UnauthGuard], component: HomeComponent },

  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () =>
      import('./features/auth/auth.routes').then((m) => m.AUTH_ROUTES),
  },

  {
    path: '',
    canActivate: [AuthGuard],
    component: MenuLayoutComponent,
    children: [
      {
        path: 'feed',
        loadChildren: () =>
          import('./features/posts/posts.routes').then((m) => m.POSTS_ROUTES),
      },

      {
        path: 'topics',
        loadComponent: () =>
          import('./features/topics/components/list/list.component').then(
            (m) => m.ListComponent
          ),
      },

      {
        path: 'profile',
        loadComponent: () =>
          import('./pages/profile/profile.component').then(
            (m) => m.ProfileComponent
          ),
      },
    ],
  },
];
