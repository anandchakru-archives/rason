import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GrowliComponent } from './growli/growli.component';
import { GrowliService } from './growli.service';

@NgModule({
  declarations: [GrowliComponent],
  imports: [
    CommonModule
  ],
  providers: [GrowliService],
  exports: [GrowliComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrowlModule { }
