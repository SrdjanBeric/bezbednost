export class User {
    id: number = 0;
    email: string = '';
    password:string='';
    username:string='';
    active:boolean=false;
    role:any='';
    skills:string[]=[];


  
    public constructor(obj?: any) {
      if (obj) {
        this.email = obj.email;
        this.password = obj.password;
        this.id = obj.id;
        this.username = obj.username;
        this.active = obj.active;
        this.role = obj.role;
        this.skills=obj.skills;
      }
    }
  }
