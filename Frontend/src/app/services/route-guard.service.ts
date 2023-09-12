import { SnackbarService } from './snackbar.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import jwt_Decode from 'jwt-decode';
import { GlobalConstants } from '../shared/global-constants';
import { ActivatedRouteSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {

  constructor(public auth:AuthService,
    public router:Router,
    private snackbarService:SnackbarService,) { }

    canActivate(route:ActivatedRouteSnapshot):boolean{
      let expectedRoleArray = route.data;
      expectedRoleArray = expectedRoleArray.expectedRole;


      const token:any = localStorage.getItem('token');

      var tokenPayload:any;
      try{
        tokenPayload = jwt_Decode(token);
      }
      catch(err){
        localStorage.clear();
        this.router.navigate(['/']);
      }

      let expectedRole= ''; 

      for(let i =0; i < expectedRoleArray.length ;i++){
        if(expectedRoleArray[i] == tokenPayload.role){
          expectedRole = tokenPayload.role;
        }
      }

      if(tokenPayload.role == 'user' || tokenPayload.role == 'admin'){
        if(this.auth.isAuthenticated() && tokenPayload.role == expectedRole){
          return true;
        }
        this.snackbarService.openSnackBar(GlobalConstants.unauthorized,GlobalConstants.error);
        this.router.navigate(['/cafe/dashboard']);
        return false;
      }else{
        this.router.navigate(['/']);
        localStorage.clear();
        return false;
        
      }

    }
}
