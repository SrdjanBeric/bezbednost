export interface CertificateDto {
  commonNameSubject: string;
  nameSubject: string;
  surnameSubject: string;
  usernameSubject: string;
  countrySubject: string;
  serialNumberSubject: string;
  startDate?: Date;
  endDate?: Date;
  authoritySubject: string;
  commonNameIssuer?: string;
  nameIssuer?: string;
  surnameIssuer?: string;
  usernameIssuer?: string;
  countryIssuer?: string;
  serialNumberIssuer?: string;
  authorityIssuer?: string;
  keyUsages?: number[];
}
