export interface AnnonceTrajetDTO {
  id: number;
  lieuDepart: string;
  etapesIntermediaires: string[];
  destination: string;
  capaciteDisponible: number;
  dateCreation: Date;
  typeMarchandiseAcceptee: string;
}
