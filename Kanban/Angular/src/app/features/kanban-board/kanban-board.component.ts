import { Component, OnInit } from '@angular/core';
import { LayoutList, LucideAngularModule } from 'lucide-angular';
import { DashboardService, DashboardResponse } from '../../services/dashboard.service';

@Component({
  selector: 'app-kanban-board',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './kanban-board.component.html',
  styleUrl: './kanban-board.component.css'
})
export class KanbanBoardComponent implements OnInit {
  readonly listIcon = LayoutList;

  dashboard?: DashboardResponse;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getDashboard().subscribe({
      next: (data) => {
        this.dashboard = data;
      },
      error: (err) => {
        console.error('Dashboard load failed', err);
      }
    });
  }
}
