import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Team {
  name: string;
  members: number;
  sprint: string;
  status: 'Active' | 'On Hold';
}

@Component({
  selector: 'app-teams',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {
  searchText = '';

  teams: Team[] = [
    {
      name: 'Development Team',
      members: 8,
      sprint: 'Sprint 12',
      status: 'Active'
    },
    {
      name: 'QA Team',
      members: 5,
      sprint: 'Sprint 7',
      status: 'Active'
    },
    {
      name: 'Design Team',
      members: 5,
      sprint: 'Sprint 5',
      status: 'Active'
    },
    {
      name: 'Product Team',
      members: 4,
      sprint: 'Sprint 6',
      status: 'Active'
    },
    {
      name: 'DevOps Team',
      members: 4,
      sprint: 'Sprint 4',
      status: 'On Hold'
    }
  ];

  get filteredTeams(): Team[] {
    return this.teams.filter(team =>
      team.name.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }
}
