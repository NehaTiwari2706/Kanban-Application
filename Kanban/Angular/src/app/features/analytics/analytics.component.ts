import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-analytics',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
})
export class AnalyticsComponent {
  stats = {
    totalTasks: 24,
    completed: 13,
    inProgress: 8,
    defects: 3
  };

  teams = [
    {
      name: 'Development Team',
      completed: 8,
      inProgress: 4,
      total: 12,
      rate: 67
    },
    {
      name: 'QA Team',
      completed: 5,
      inProgress: 2,
      total: 7,
      rate: 71
    },
    {
      name: 'Design Team',
      completed: 3,
      inProgress: 1,
      total: 4,
      rate: 75
    },
    {
      name: 'Product Team',
      completed: 2,
      inProgress: 1,
      total: 3,
      rate: 67
    }
  ];
}
