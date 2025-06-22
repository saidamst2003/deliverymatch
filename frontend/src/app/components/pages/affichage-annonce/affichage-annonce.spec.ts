import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AffichageAnnonce } from './affichage-annonce';

describe('AffichageAnnonce', () => {
  let component: AffichageAnnonce;
  let fixture: ComponentFixture<AffichageAnnonce>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AffichageAnnonce]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AffichageAnnonce);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
