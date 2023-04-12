import { Role } from "./role";
import { Authority } from "./authority";

export interface User{
     id:number;
     username:string;
     password:string;
     role: Role;
     enabled: boolean;
     credentialsNonExpired: boolean;
     accountNonExpired: boolean;
     authorities: Authority[];
     accountNonLocked: boolean;
}