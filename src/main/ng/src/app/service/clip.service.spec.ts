import { TestBed } from '@angular/core/testing';

import { ClipService } from './clip.service';

describe('ClipService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ClipService = TestBed.get(ClipService);
    expect(service).toBeTruthy();
  });
});
