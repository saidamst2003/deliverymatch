import { TestBed } from '@angular/core/testing';
import {AnnonceService} from './annonce';


describe('Annonce', () => {
  let service: AnnonceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnnonceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
