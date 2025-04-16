import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonesComidaDiarioComponent } from './botones-comida-diario.component';

describe('BotonesComidaDiarioComponent', () => {
  let component: BotonesComidaDiarioComponent;
  let fixture: ComponentFixture<BotonesComidaDiarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonesComidaDiarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonesComidaDiarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
