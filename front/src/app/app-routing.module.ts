import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { FeedComponent } from './features/posts/components/feed/feed.component';
import { DetailsComponent } from './features/posts/components/details/details.component';
import { ListComponent } from './features/topics/components/list/list.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'profile', component: ListComponent },
  { path: 'feed', component: FeedComponent },
  { path: 'feed/details/:id', component: DetailsComponent },
  { path: 'topics', component: ListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
