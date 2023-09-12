import { SnackbarService } from './../services/snackbar.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';

import { UserService } from './../services/user.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { GlobalConstants } from '../shared/global-constants';


@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
  forgotPasswordForm:any = FormGroup;
  responseMessage:any;

  constructor(private FormBuilder:FormBuilder,
    private userService:UserService,
    public dialogRef:MatDialogRef<ForgotPasswordComponent>,
    private ngxSerice:NgxUiLoaderService,
    private SnackbarService:SnackbarService) { }

  ngOnInit(): void {
    this.forgotPasswordForm = this.FormBuilder.group({
      email:[null,[Validators.required,Validators.pattern(GlobalConstants.emailRegex)]]
    });
  }
    handleSubmit(){
      this.ngxSerice.start();
      var formData = this.forgotPasswordForm.value;
      var data = {
        email:formData.email
      }
      this.userService.forgotPassword(data).subscribe((response:any)=>{
        this.ngxSerice.stop();
        this.responseMessage = response?.message;
        this.dialogRef.close();
        this.SnackbarService.openSnackBar(this.responseMessage,"");
      },(error)=>{
        this.ngxSerice.stop();
        if(error.error?.message){
          this.responseMessage = error.error?.message;
        }
        else{
          this.responseMessage = GlobalConstants.genericError;
        }
        this.SnackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);

      })
    }

}
