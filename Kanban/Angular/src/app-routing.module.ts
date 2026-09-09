import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './app/features/HomePage/home.component';
import { LoginComponent } from './app/features/LoginPage/login.component';
import { RegisterComponent } from './app/features/RegisterPage/register.component';
import { KanbanBoardComponent } from './app/features/kanban-board/kanban-board.component';
import { MyWorkComponent } from './app/features/my-work/my-work.component';
import { ProjectsComponent } from './app/features/projects/projects.component';
import { SettingsComponent } from './app/features/settings/settings.component';
import { TeamsComponent } from './app/features/teams/teams.component';
import { AnalyticsComponent } from './app/features/analytics/analytics.component';
import { LayoutComponent } from './app/shared/layout/layout.component';
import { AuthGuard } from './app/core/guard/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'kanban', component: KanbanBoardComponent },
      { path: 'my-work', component: MyWorkComponent },
      { path: 'projects', component: ProjectsComponent },
      { path: 'settings', component: SettingsComponent },
      { path: 'teams', component: TeamsComponent },
      { path: 'analytics', component: AnalyticsComponent }
    ]
  },
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
