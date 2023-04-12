export interface CreateCertificateDto {
  commonNameSubject: string; // input polje tekst obican
  nameSubject: string;       // input tekst
  surnameSubject: string;    // input tekst
  usernameSubject: string;   // input tekst
  countrySubject: string;    // input tekst
  startDate?: Date;   //date picker
  endDate?: Date; //date picker
  authoritySubject: string; //dropdown lista gde cu birati dal je root ca ili ee
  issuerSerialNumber: string; // dropdown lista koja bi trebala da pokaze moje sertifikate i uzimam samo ID 
  ownerUsername: string;  // korisnicko ime nekog korisnika iz dropdown liste kom pravim sertifikat zovem available subject user
}
