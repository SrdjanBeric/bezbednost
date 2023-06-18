import { Component, OnInit } from '@angular/core';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-logs-preview',
  templateUrl: './logs-preview.component.html',
  styleUrls: ['./logs-preview.component.css'],
})
export class LogsPreviewComponent implements OnInit {
  constructor(private adminService: AdminService) {}
  logs: any[] | undefined;
  ngOnInit(): void {
    this.fetchLogs();
  }

  fetchLogs(): void {
    this.adminService.getCriticalLogs().subscribe(
      (response) => {
        this.logs = response;
      },
      (error) => {
        console.error('Failed to fetch logs:', error);
      }
    );
  }

  formatDate(timestamp: number[]): string {
    const [year, month, day, hour, minute, second] = timestamp;
    const date = new Date(year, month - 1, day, hour, minute, second);
    return date.toLocaleString();
  }

  getLogLevelClass(logLevel: string): string {
    if (logLevel === 'ERROR') {
      return 'error-row';
    } else if (logLevel === 'FATAL') {
      return 'fatal-row';
    } else {
      return '';
    }
  }
}
