import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './app/HomePage/home.component';
import { LoginComponent } from './app/LoginPage/login.component';
import { RegisterComponent } from './app/RegisterPage/register.component';
import { KanbanBoardComponent } from './app/kanban-board/kanban-board.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'kanban', component: KanbanBoardComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: '/home' } // Wildcard route - must be last
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
