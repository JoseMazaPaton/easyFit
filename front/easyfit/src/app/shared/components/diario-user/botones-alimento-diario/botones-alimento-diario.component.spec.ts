import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonesAlimentoDiarioComponent } from './botones-alimento-diario.component';

describe('BotonesAlimentoDiarioComponent', () => {
  let component: BotonesAlimentoDiarioComponent;
  let fixture: ComponentFixture<BotonesAlimentoDiarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonesAlimentoDiarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonesAlimentoDiarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
