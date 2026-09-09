import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface DashboardUser {
  id: number;
  name: string;
}

export interface DashboardSummary {
  totalTasks: number;
  inProgress: number;
  completed: number;
  defects: number;
}

export interface DashboardIteration {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  progress: number;
}

export interface DashboardTask {
  id: number;
  title: string;
  status: string;
  priority: string;
}

export interface DashboardResponse {
  user: DashboardUser;
  summary: DashboardSummary;
  currentIteration: DashboardIteration;
  myWork: DashboardTask[];
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private readonly apiUrl = 'http://localhost:8081/api/dashboard';

  constructor(private http: HttpClient) {}

  getDashboard(): Observable<DashboardResponse> {
    return this.http.get<DashboardResponse>(this.apiUrl);
  }
}
