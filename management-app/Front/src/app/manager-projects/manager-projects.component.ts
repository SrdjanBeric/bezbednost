import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Route } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { ProjectService } from '../service/project.service';
@Component({
  selector: 'app-manager-projects',
  templateUrl: './manager-projects.component.html',
  styleUrls: ['./manager-projects.component.css']
})
export class ManagerProjectsComponent {
  
  constructor(private projectService: ProjectService, private router: Router,private route: ActivatedRoute) {}
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
  goBack() {
    this.router.navigate(['/manager-profile/{id}', { id: this.route.snapshot.paramMap.get('id')}]);
  }
  EditCV()
  {

  }
}
