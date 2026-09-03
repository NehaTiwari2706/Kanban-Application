import { Component } from '@angular/core';
import { Bell, ChevronDown, LucideAngularModule, Mail } from 'lucide-angular';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [LucideAngularModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  readonly mailIcon = Mail;
  readonly notificationIcon = Bell;
  readonly chevronIcon = ChevronDown;
}
