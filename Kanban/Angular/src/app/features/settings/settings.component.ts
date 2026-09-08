import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent {
  activeTab = 'Profile';

  tabs = [
    'Profile',
    'Notifications',
    'Appearance',
    'Security',
    'Integrations'
  ];

  user = {
    name: 'Neha Tiwari',
    email: 'neha.tiwari@example.com',
    role: 'Developer',
    team: 'Development Team',
    bio: ''
  };

  selectTab(tab: string): void {
    this.activeTab = tab;
  }

  saveChanges(): void {
    console.log('Profile saved', this.user);
  }
}
