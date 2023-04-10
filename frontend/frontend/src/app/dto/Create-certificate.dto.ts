export interface CreateCertificateDto {
  commonNameSubject: string;
  nameSubject: string;
  surnameSubject: string;
  usernameSubject: string;
  countrySubject: string;
  startDate?: Date;
  endDate?: Date;
  authoritySubject: string;
  issuerSerialNumber: string;
}
