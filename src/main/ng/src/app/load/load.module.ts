import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoadiComponent } from './loadi/loadi.component';

@NgModule({
  declarations: [LoadiComponent],
  imports: [
    CommonModule
  ],
  providers: [],
  exports: [LoadiComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LoadModule { }
