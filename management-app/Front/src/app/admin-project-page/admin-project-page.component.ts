import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../service/project.service';

@Component({
  selector: 'app-admin-project-page',
  templateUrl: './admin-project-page.component.html',
  styleUrls: ['./admin-project-page.component.css'],
})
export class AdminProjectPageComponent implements OnInit {
  constructor(private projectService: ProjectService) {}
  projects: any[] = [];

  ngOnInit(): void {
    this.projectService.allUsers().subscribe((projectResponse) => {
      this.projects = projectResponse;
      console.log(this.projects);
    });
  }
}
