import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadiComponent } from './loadi.component';

describe('LoadiComponent', () => {
  let component: LoadiComponent;
  let fixture: ComponentFixture<LoadiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
