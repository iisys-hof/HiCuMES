import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductionNumbersTableDynamicComponent } from './production-numbers-table-dynamic.component';

describe('ProductionNumbersTableDynamicComponent', () => {
  let component: ProductionNumbersTableDynamicComponent;
  let fixture: ComponentFixture<ProductionNumbersTableDynamicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductionNumbersTableDynamicComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductionNumbersTableDynamicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
