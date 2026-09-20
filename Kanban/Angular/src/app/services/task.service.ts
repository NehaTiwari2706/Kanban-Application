import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface MyWorkTask {
  id: number;
  title: string;
  status: 'TODO' | 'IN_PROGRESS' | 'DONE';
  priority: 'HIGH' | 'MEDIUM' | 'LOW';
  assignee: string;
  type: string;
}

export interface MyWorkPageResponse {
  success: boolean;
  message: string;
  data: {
    todo: any[];
    inProgress: any[];
    done: any[];
  };
  pagination?: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private readonly apiUrl = 'http://localhost:8081/api/v1/tasks';

  constructor(private http: HttpClient) {}

  getMyWorkTasks(search = ''): Observable<MyWorkPageResponse> {
    let params = new HttpParams();
    if (search) {
      params = params.set('search', search);
    }
    return this.http.get<MyWorkPageResponse>(`${this.apiUrl}/my-work`, { params });
  }

  createTask(payload: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, payload);
  }
}
