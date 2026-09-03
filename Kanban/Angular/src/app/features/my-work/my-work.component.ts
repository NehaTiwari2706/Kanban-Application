import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent } from '../../shared/sidebar/sidebar.component';

interface Task {
  id: number;
  title: string;
  status: 'TODO' | 'IN_PROGRESS' | 'DONE';
  priority: 'High' | 'Medium' | 'Low';
  assignee: string;
  type: string;
}

@Component({
  selector: 'app-my-work',
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, SidebarComponent],
  templateUrl: './my-work.component.html',
  styleUrl: './my-work.component.css'
})
export class MyWorkComponent {

  searchText = '';

  tasks: Task[] = [

    {
      id: 102,
      title: 'Login',
      status: 'TODO',
      priority: 'High',
      assignee: 'Neha',
      type: 'N'
    },

    {
      id: 103,
      title: 'User Registration',
      status: 'TODO',
      priority: 'Medium',
      assignee: 'Neha',
      type: 'N'
    },

    {
      id: 104,
      title: 'Dashboard UI',
      status: 'TODO',
      priority: 'Low',
      assignee: 'Krishna',
      type: 'K'
    },

    {
      id: 108,
      title: 'Implement JWT',
      status: 'IN_PROGRESS',
      priority: 'Medium',
      assignee: 'Krishna',
      type: 'K'
    },

    {
      id: 109,
      title: 'API Integration',
      status: 'IN_PROGRESS',
      priority: 'High',
      assignee: 'Neha',
      type: 'N'
    },

    {
      id: 110,
      title: 'Security Filter',
      status: 'IN_PROGRESS',
      priority: 'High',
      assignee: 'Prathamesh',
      type: 'P'
    },

    {
      id: 95,
      title: 'Register Page',
      status: 'DONE',
      priority: 'Low',
      assignee: 'Prathamesh',
      type: 'P'
    },

    {
      id: 96,
      title: 'Database Setup',
      status: 'DONE',
      priority: 'Medium',
      assignee: 'Krishna',
      type: 'K'
    },

    {
      id: 97,
      title: 'Project Setup',
      status: 'DONE',
      priority: 'Low',
      assignee: 'Neha',
      type: 'N'
    }

  ];

  getTasks(status: Task['status']): Task[] {

    return this.tasks.filter(task => {

      const matchesStatus =
        task.status === status;

      const matchesSearch =
        task.title
          .toLowerCase()
          .includes(
            this.searchText.toLowerCase()
          );

      return matchesStatus && matchesSearch;

    });

  }

}
