import { SnackbarService } from './../services/snackbar.service';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { UserService } from './../services/user.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  hide = true;
  loginForm:any = FormGroup; // Use correct type declaration
  responseMessage: any;

  constructor(
    private formBuilder: FormBuilder, // Correct variable naming convention
    private router: Router,
    private userService: UserService, // Correct variable naming convention
    public dialogRef: MatDialogRef<LoginComponent>,
    private ngxService: NgxUiLoaderService,
    private snackbarService: SnackbarService // Inject SnackbarService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      password: [null, [Validators.required]]
    });
  }

  handleSubmit() {
    this.ngxService.start();
    var formData = this.loginForm.value;
    var data = {
      email: formData.email,
      password: formData.password
    };
    this.userService.login(data).subscribe((response: any) => {
        this.ngxService.stop();
        this.dialogRef.close();
        localStorage.setItem('token', response.token);
        console.log('Token généré après connexion :', response.token);

        this.router.navigate(['/cafe/dashboard']);
      },
      (error) => {
        this.ngxService.stop()
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error); // Use snackbarService here
      }
    );
  }
}
