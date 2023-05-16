import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../service/project.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-project-page',
  templateUrl: './admin-project-page.component.html',
  styleUrls: ['./admin-project-page.component.css'],
})
export class AdminProjectPageComponent implements OnInit {
  constructor(private projectService: ProjectService, private router: Router) {}
  projects: any[] = [];

  ngOnInit(): void {
    this.projectService.allUsers().subscribe((projectResponse) => {
      this.projects = projectResponse;
      console.log(this.projects);
    });
  }

  addEngineerToProject(project: any): void {
    this.router.navigate(['/add-engineer'], {
      queryParams: { project: JSON.stringify(project) },
    });
    console.log('Add engineer to project:', project);
  }
}
