import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Project {
  name: string;
  description: string;
  status: 'Active' | 'On Hold' | 'Completed';
  members: number;
  progress: number;
}

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.css']
})
export class ProjectsComponent {

  searchText = '';
  statusFilter = 'All';

  projects: Project[] = [
    {
      name: 'Kanban Board',
      description: 'Project management system',
      status: 'Active',
      members: 5,
      progress: 72
    },
    {
      name: 'Time Heist Game',
      description: 'Web game project',
      status: 'Active',
      members: 4,
      progress: 45
    },
    {
      name: 'E-Commerce Platform',
      description: 'Online shopping platform',
      status: 'Active',
      members: 6,
      progress: 60
    },
    {
      name: 'Mobile App',
      description: 'Android application',
      status: 'On Hold',
      members: 3,
      progress: 20
    },
    {
      name: 'CRM System',
      description: 'Customer management system',
      status: 'Completed',
      members: 4,
      progress: 100
    }
  ];

  get filteredProjects(): Project[] {
    return this.projects.filter(project => {

      const matchesSearch =
        project.name
          .toLowerCase()
          .includes(this.searchText.toLowerCase());

      const matchesStatus =
        this.statusFilter === 'All' ||
        project.status === this.statusFilter;

      return matchesSearch && matchesStatus;
    });
  }

  get totalProjects(): number {
    return this.projects.length;
  }

  get activeProjects(): number {
    return this.projects.filter(p => p.status === 'Active').length;
  }

  get completedProjects(): number {
    return this.projects.filter(p => p.status === 'Completed').length;
  }

  get onHoldProjects(): number {
    return this.projects.filter(p => p.status === 'On Hold').length;
  }
}