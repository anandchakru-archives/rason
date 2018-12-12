import { TestBed } from '@angular/core/testing';

import { DebounceService } from './debounce.service';

describe('DebounceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DebounceService = TestBed.get(DebounceService);
    expect(service).toBeTruthy();
  });
});
