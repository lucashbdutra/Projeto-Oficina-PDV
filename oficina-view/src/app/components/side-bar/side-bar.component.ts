import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/services/local-storage.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  isAdmin : boolean = false;
  constructor(
    private router: Router,
    private localStorage: LocalStorageService
  ) {

   }

  ngOnInit(): void {
    const isAdmin = this.localStorage.get('admin');
    if(isAdmin === 'true'){
      this.isAdmin = true;
    }
  }

  onLogOut(){
    this.localStorage.clear();
    this.router.navigate(['login']);
  }

}
