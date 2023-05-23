export class AETPRDTO {
    projectId: number = 0;
    name: string = '';
    startDate:any=new Date();
    endDate:any=new Date();
    softwareEngineerId:number=0;


  
    public constructor(obj?: any) {
      if (obj) {
        this.softwareEngineerId = obj.softwareEngineerId;
        this.projectId = obj.projectId;
        this.name = obj.username;
        this.endDate = obj.endDate;
        this.startDate=obj.startDate;
      }
    }
  }
