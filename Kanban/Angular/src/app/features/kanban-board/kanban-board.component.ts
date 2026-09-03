import { Component } from '@angular/core';
import { LayoutList, LucideAngularModule } from 'lucide-angular';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent } from '../../shared/sidebar/sidebar.component';

@Component({
  selector: 'app-kanban-board',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, LucideAngularModule],
  templateUrl: './kanban-board.component.html',
  styleUrl: './kanban-board.component.css'
})
export class KanbanBoardComponent {
  readonly listIcon = LayoutList;
}
