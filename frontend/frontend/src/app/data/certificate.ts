// certificate.interface.ts
import { Subject } from './subject';
import { Issuer } from './issuer';

export interface Certificate {
  subject: Subject;
  issuer: Issuer;
  serialNumber: String;
  startDate: Date;
  endDate: Date;
}
