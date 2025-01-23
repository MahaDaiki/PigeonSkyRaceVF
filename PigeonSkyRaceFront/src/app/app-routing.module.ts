import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { authGuard } from './auth/guards/auth.guard';

const routes: Routes = [
  { path: '', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
