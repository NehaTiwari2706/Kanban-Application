import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import {
  BarChart3,
  ClipboardList,
  Folder,
  LayoutDashboard,
  LucideAngularModule,
  Settings,
  Users
} from 'lucide-angular';

interface NavItem {
  label: string;
  icon: any;
  route: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, LucideAngularModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  navItems: NavItem[] = [
    {
      label: 'Dashboard',
      icon: LayoutDashboard,
      route: '/kanban'
    },
    {
      label: 'My Work',
      icon: ClipboardList,
      route: '/my-work'
    },  
    {
      label: 'Projects',
      icon: Folder,
      route: '/projects'
    },
    {
      label: 'Teams',
      icon: Users,
      route: '/teams'
    },
    {
      label: 'Analytics',
      icon: BarChart3,
      route: '/analytics'
    },
    {
      label: 'Settings',
      icon: Settings,
      route: '/settings'
    }
  ];
}
