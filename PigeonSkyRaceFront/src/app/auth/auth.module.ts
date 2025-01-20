import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import {ReactiveFormsModule} from '@angular/forms';
import { LogoutComponent } from './logout/logout.component';
import {HttpClientModule} from '@angular/common/http';
import {AuthService} from './auth.service';
import {EffectsModule} from '@ngrx/effects';


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    LogoutComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,

  ],
  providers: [AuthService],
})
export class AuthModule { }
