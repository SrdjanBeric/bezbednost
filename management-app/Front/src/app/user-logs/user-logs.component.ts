import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-user-logs',
  templateUrl: './user-logs.component.html',
  styleUrls: ['./user-logs.component.css'],
})
export class UserLogsComponent implements OnInit {
  constructor(
    private adminService: AdminService,
    private route: ActivatedRoute
  ) {}
  logs: any[] | undefined;

  ngOnInit(): void {
    this.fetchLogs();
  }

  fetchLogs(): void {
    this.route.params.subscribe((params) => {
      const userId = params['userId'];
      this.adminService.getUserLogs(userId).subscribe(
        (response) => {
          this.logs = response;
        },
        (error) => {
          console.error('Failed to fetch logs:', error);
        }
      );
    });
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
