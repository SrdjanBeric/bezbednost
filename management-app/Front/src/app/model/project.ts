export class Project {
    id: number = 0;
    name: string = '';
    startDate:any=new Date();
    endDate:any=new Date();
    public constructor(obj?: any) {
      if (obj) {

        this.id = obj.id;
        this.name = obj.username;
        this.endDate = obj.endDate;
        this.startDate=obj.startDate;
      }
    }
  }
