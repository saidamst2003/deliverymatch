import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MesTrajet } from './mes-trajet';

describe('MesTrajet', () => {
  let component: MesTrajet;
  let fixture: ComponentFixture<MesTrajet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MesTrajet]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MesTrajet);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
