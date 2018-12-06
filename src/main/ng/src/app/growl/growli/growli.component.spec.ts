import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GrowliComponent } from './growli.component';

describe('GrowliComponent', () => {
  let component: GrowliComponent;
  let fixture: ComponentFixture<GrowliComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GrowliComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GrowliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
