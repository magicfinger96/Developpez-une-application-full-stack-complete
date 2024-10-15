import { Routes } from '@angular/router';
import { FeedComponent } from './components/feed/feed.component';
import { DetailsComponent } from './components/details/details.component';
import { CreateComponent } from './components/create/create.component';

/**
 * The routes of any page belonging to the posts.
 */
export const POSTS_ROUTES: Routes = [
  { path: '', component: FeedComponent },
  { path: 'details/:id', component: DetailsComponent },
  { path: 'create', component: CreateComponent },
];
