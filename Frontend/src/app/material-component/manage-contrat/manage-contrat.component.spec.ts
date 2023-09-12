import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageContratComponent } from './manage-contrat.component';

describe('ManageContratComponent', () => {
  let component: ManageContratComponent;
  let fixture: ComponentFixture<ManageContratComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageContratComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageContratComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
