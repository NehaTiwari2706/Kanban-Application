import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TaskService, MyWorkTask, MyWorkPageResponse } from '../../services/task.service';

@Component({
  selector: 'app-my-work',
  standalone: true,
  imports: [CommonModule, FormsModule],
  providers: [TaskService],
  templateUrl: './my-work.component.html',
  styleUrl: './my-work.component.css'
})
export class MyWorkComponent implements OnInit {

  searchText = '';
  isCreateTaskModalOpen = false;
  tasks: MyWorkTask[] = [];

  newTask: {
    title: string;
    status: 'TODO' | 'IN_PROGRESS' | 'DONE';
    priority: 'HIGH' | 'MEDIUM' | 'LOW';
    assignee: string;
    userStoryId: string;
    estimatedTime: string;
    actualTime: string;
    description: string;
  } = {
    title: '',
    status: 'TODO',
    priority: 'MEDIUM',
    assignee: '',
    userStoryId: '1',
    estimatedTime: '01',
    actualTime: '00',
    description: ''
  };

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  getTasks(status: 'TODO' | 'IN_PROGRESS' | 'DONE'): MyWorkTask[] {
    return this.tasks.filter(task => task.status === status && task.title.toLowerCase().includes(this.searchText.toLowerCase()));
  }

  loadTasks(): void {
    this.taskService.getMyWorkTasks(this.searchText).subscribe({
      next: (response: MyWorkPageResponse) => {
        const data = response?.data ?? response;
        this.tasks = this.normalizeTasks(data);
      },
      error: () => {
        this.tasks = [];
      }
    });
  }

  openCreateTaskModal(): void {
    this.isCreateTaskModalOpen = true;
  }

  closeCreateTaskModal(): void {
    this.isCreateTaskModalOpen = false;
    this.resetNewTaskForm();
  }

  submitNewTask(): void {
    const title = this.newTask.title.trim();
    const assignee = this.newTask.assignee.trim();

    if (!title || !assignee) {
      return;
    }

    const payload = {
      title,
      description: this.newTask.description.trim(),
      priority: this.newTask.priority.toUpperCase(),
      status: this.newTask.status,
      assigneeId: 1,
      assignedToId: 1,
      userStoryId: Number(this.newTask.userStoryId || 1),
      projectId: Number(this.newTask.userStoryId || 1),
      dueDate: new Date().toISOString().slice(0, 10)
    };

    this.taskService.createTask(payload).subscribe({
      next: () => {
        this.closeCreateTaskModal();
        this.loadTasks();
      },
      error: () => {
        this.closeCreateTaskModal();
      }
    });
  }

  private normalizeTasks(data: any): MyWorkTask[] {
    const grouped = [data?.todo ?? [], data?.inProgress ?? [], data?.done ?? []].flat();
    return grouped.map((task: any) => ({
      id: task.id,
      title: task.title ?? 'Untitled task',
      status: this.normalizeStatus(task.status),
      priority: this.normalizePriority(task.priority),
      assignee: task.assignee?.name ?? task.assignee ?? 'Unassigned',
      type: task.assignee?.initials ?? 'U'
    }));
  }

  private normalizeStatus(status: string): 'TODO' | 'IN_PROGRESS' | 'DONE' {
    const normalized = (status ?? '').toUpperCase();
    if (normalized === 'IN_PROGRESS') {
      return 'IN_PROGRESS';
    }
    if (normalized === 'DONE') {
      return 'DONE';
    }
    return 'TODO';
  }

  private normalizePriority(priority: string): 'HIGH' | 'MEDIUM' | 'LOW' {
    const normalized = (priority ?? '').toUpperCase();
    if (normalized === 'HIGH') {
      return 'HIGH';
    }
    if (normalized === 'LOW') {
      return 'LOW';
    }
    return 'MEDIUM';
  }

  private resetNewTaskForm(): void {
    this.newTask = {
      title: '',
      status: 'TODO',
      priority: 'MEDIUM',
      assignee: '',
      userStoryId: '1',
      estimatedTime: '01',
      actualTime: '00',
      description: ''
    };
  }

}
