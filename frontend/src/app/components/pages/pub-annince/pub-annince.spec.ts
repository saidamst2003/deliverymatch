import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PubAnnince } from './pub-annince';

describe('PubAnnince', () => {
  let component: PubAnnince;
  let fixture: ComponentFixture<PubAnnince>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PubAnnince]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PubAnnince);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
