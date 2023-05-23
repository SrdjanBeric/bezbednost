import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Route } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { ProjectService } from '../service/project.service';
@Component({
  selector: 'app-user-projects',
  templateUrl: './user-projects.component.html',
  styleUrls: ['./user-projects.component.css']
})
export class ManagerProjectsComponent {
  
  constructor(private projectService: ProjectService, private router: Router,private route: ActivatedRoute) {}
  projects: any[] = [];

  ngOnInit(): void {
    this.loadAllUsers();
  }

  loadAllUsers() {
    this.projectService.engineerProjects().subscribe((projectResponse) => {
      this.projects = projectResponse;
      console.log(this.projects);
    });
  }
  goBack() {
    this.router.navigate(['/user-profile']);
  }
  
}
