import { SnackbarService } from './../../../services/snackbar.service';
import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ContratService } from 'src/app/services/contrat.service';
import { GlobalConstants } from 'src/app/shared/global-constants';


@Component({
  selector: 'app-contrat',
  templateUrl: './contrat.component.html',
  styleUrls: ['./contrat.component.scss']
})
export class ContratComponent implements OnInit {

  onAddContrat = new EventEmitter();
  onEditContrat = new EventEmitter();
  contratForm:any = FormGroup;
  dialogAction:any = "Add";
  action:any = "Add";
  responseMessage:any;
  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
  private formBuilder:FormBuilder,
  private contratService:ContratService,
  public dialogRef:MatDialogRef<ContratComponent>,
  private snackbarService:SnackbarService) { }

  ngOnInit(): void {
    this.contratForm= this.formBuilder.group({
      nomSociete:['',[Validators.required,Validators.pattern(GlobalConstants.nameRegex)]],
      dateDebut:['',[Validators.required]],
      dateFin:['',[Validators.required]],

    });
    if(this.dialogData.action === 'Edit'){
      this.dialogAction ="Edit";
      this.action ="Update"
      this.contratForm.patchValue(this.dialogData.data);
    }
}
handleSubmit(){
  console.log(this.contratForm.value);
  if(this.dialogAction === "Edit"){
    this.edit();
  }else{
    this.add();
  }
}
  add(){
    var formData = this.contratForm.value;

    var data ={
      nomSociete: formData.nomSociete,
      dateDebut: formData.dateDebut,
      dateFin: formData.dateFin,

    }
    console.log('ZZZZZZZZZ',this.contratForm.value);
   
    this.contratService.add(data).subscribe((response:any)=>{
      this.dialogRef.close();
      this.onAddContrat.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage,"success");
    },(error)=>{
      console.log(error);
      if(error.error?.message){
        this.responseMessage=error.error?.message;
      }else{
        this.responseMessage =GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    });
  }

  edit(){
    console.log('Data to be sent:', this.contratForm.value);
    var formData = this.contratForm.value;
    var data ={
      id:this.dialogData.data.id,
      nomSociete: formData.nomSociete,
      dateDebut: formData.dateDebut,
      dateFin:formData.dateFin
    }
    this.contratService.update(data).subscribe((response:any)=>{
      
      this.dialogRef.close();
      this.onEditContrat.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage,"success");
    },(error)=>{
      console.log(error);
      if(error.error?.message){
        this.responseMessage=error.error?.message;
      }else{
        this.responseMessage =GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    });
  }

}
