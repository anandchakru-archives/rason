import { TestBed } from '@angular/core/testing';

import { GrowliService } from './growli.service';

describe('GrowliService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GrowliService = TestBed.get(GrowliService);
    expect(service).toBeTruthy();
  });
});
