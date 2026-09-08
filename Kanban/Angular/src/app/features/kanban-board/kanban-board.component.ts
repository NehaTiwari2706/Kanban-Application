import { Component } from '@angular/core';
import { LayoutList, LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-kanban-board',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './kanban-board.component.html',
  styleUrl: './kanban-board.component.css'
})
export class KanbanBoardComponent {
  readonly listIcon = LayoutList;
}
