import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MenuLayoutComponent } from './core/components/menu-layout/menu-layout.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: '',
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
