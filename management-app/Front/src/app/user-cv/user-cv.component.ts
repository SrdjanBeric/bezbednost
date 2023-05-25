import { Component, OnInit ,ChangeDetectorRef} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-user-cv',
  templateUrl: './user-cv.component.html',
  styleUrls: ['./user-cv.component.css']
})
export class UserCVComponent {
  
  public skills: String[]=[];
  public skillToAppend: String=new String();
  public skillToDelete: String=new String();


  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router,    private changeDetection: ChangeDetectorRef    ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.userService.getSkills().subscribe(res => {
       this.skills = res as string[];
       if(res==null)
        this.skills=[];
      })
    });
  }
  goBack() {
    this.router.navigate(['/user-profile/']);
  }
  addSkill()
  {
    console.log(this.skillToAppend);
    this.skills.push(this.skillToAppend);
    this.userService.updateSkills(this.skills).subscribe();
  }
  removeSkill()
  {
    console.log(this.skillToDelete);
    this.skills.splice(this.skills.indexOf(
      this.skillToDelete
      ),1);
      this.userService.updateSkills(this.skills).subscribe();

  }
  onFileSelected(event:any) {
    const selectedFile = event.target.files[0];
    this.readFileContent(selectedFile).then(res=>{
      this.skills=res;
      console.log(this.skills);
    }

    );
    this.userService.uploadCV(selectedFile).subscribe();
  }
  readFileContent(file: File): Promise<string[]> {
    return new Promise<string[]>((resolve, reject) => {
      if (!file) {
        resolve([]);
      }
      const reader = new FileReader();
      reader.onload = function(e) {
        const text = reader.result?.toString() as string;
        const lines = text.split('\n');
        resolve(lines);
        console.log(lines);
      };
      reader.readAsText(file);
    });
  }
  onKey(event: any) {
    const inputValue = event.target.value;
    this.skillToAppend=inputValue;

    console.log(inputValue);
  }
  
}
