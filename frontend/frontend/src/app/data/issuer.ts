import { CryptoKey } from 'webcrypto-core';

export interface Issuer {
  privateKey: CryptoKey;
  serialNumber: String;
  x500Name: String;
}
