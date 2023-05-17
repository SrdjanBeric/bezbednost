import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../service/project.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-engineer-page',
  templateUrl: './add-engineer-page.component.html',
  styleUrls: ['./add-engineer-page.component.css'],
})
export class AddEngineerPageComponent implements OnInit {
  projectId: number = 0;
  engineers: any[] = [];
  project: any;

  constructor(
    private projectService: ProjectService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const project = JSON.parse(params['project']);
      this.project = project;
      this.projectId = project.id;
      this.loadAvailableEngineers();
    });
  }

  loadAvailableEngineers() {
    this.projectService
      .getAvailableEngineers(this.projectId)
      .subscribe((response) => {
        
        this.engineers = response;
        console.log(this.engineers);
        console.log(this.project);
      });
  }

  addEngineer(engineerId: number) {
    const requestDto = {
      softwareEngineerId: engineerId,
      projectId: this.project.id,
      workDescription: this.project.name,
      startDate: this.project.startDate,
      endDate: this.project.endDate,
    };

    this.projectService.addEnginer(requestDto).subscribe(
      (response) => {
        console.log(`Engineer succesfully added`);
        this.loadAvailableEngineers();
      },
      (error) => {
        console.log('Error adding engineer:', error);
      }
    );
  }
}
