import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { ProjectService } from '../service/project.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-project-admin',
  templateUrl: './create-project-admin.component.html',
  styleUrls: ['./create-project-admin.component.css'],
})
export class CreateProjectAdminComponent implements OnInit {
  constructor(private projectServ: ProjectService) {}

  name: string = '';
  password: string = '';

  ngOnInit(): void {}

  submitForm(form: NgForm) {
    if (form.invalid) {
      console.log(`Your form is invalid`);
    } else {
      const ProjectRequest = {
        name: form.value.name,
        startDate: form.value.startDate,
        endDate: form.value.endDate,
        managerId: form.value.managerId,
      };

      this.projectServ.addProject(ProjectRequest).subscribe(
        (response) => {
          console.log('Project successfully created');
        },
        (error) => {
          console.log('Error creating project', error);
        }
      );
    }
  }
}
