import { CryptoKey } from 'webcrypto-core';

export interface Subject {
  publicKey: CryptoKey;
  x500Name: String;
  serialNumber: String;
}
