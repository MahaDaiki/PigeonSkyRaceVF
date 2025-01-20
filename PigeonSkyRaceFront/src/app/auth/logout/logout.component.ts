import { Component } from '@angular/core';
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-logout',
  standalone: false,

  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent {
  constructor(private store: Store) {}

  // onLogout(): void {
  //   this.store.dispatch(AuthActions.logout());
  // }

}
