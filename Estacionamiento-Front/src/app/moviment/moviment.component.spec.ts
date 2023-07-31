import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovimentComponent } from './moviment.component';

describe('MovimentComponent', () => {
  let component: MovimentComponent;
  let fixture: ComponentFixture<MovimentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MovimentComponent]
    });
    fixture = TestBed.createComponent(MovimentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
