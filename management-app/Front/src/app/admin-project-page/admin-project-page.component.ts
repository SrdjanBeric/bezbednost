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
    this.loadAllUsers();
  }

  loadAllUsers() {
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

  removeEngineerFromProject(projectId: any, softwareEngineerId: any) {
    console.log('Project ID:', projectId);
    console.log('Software Engineer ID:', softwareEngineerId);

    this.projectService.removeEngineer(projectId, softwareEngineerId).subscribe(
      (response) => {
        console.log('Engineer succesfully removed');
        this.loadAllUsers();
      },
      (error) => {
        console.log('There was some kind of error');
      }
    );
  }
}
