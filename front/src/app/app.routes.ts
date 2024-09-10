import { Routes } from '@angular/router';
import { FeedComponent } from './features/posts/components/feed/feed.component';
import { DetailsComponent } from './features/posts/components/details/details.component';
import { ListComponent } from './features/topics/components/list/list.component';
import { CreateComponent } from './features/posts/components/create/create.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'feed', component: FeedComponent },
  { path: 'feed/details/:id', component: DetailsComponent },
  { path: 'topics', component: ListComponent },
  { path: 'feed/create', component: CreateComponent },
];
