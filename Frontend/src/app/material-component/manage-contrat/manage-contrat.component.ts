import { filter } from 'rxjs/operators';
import { Router } from '@angular/router';
import { SnackbarService } from './../../services/snackbar.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ContratService } from './../../services/contrat.service';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { ContratComponent } from '../dialog/contrat/contrat.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';

@Component({
  selector: 'app-manage-contrat',
  templateUrl: './manage-contrat.component.html',
  styleUrls: ['./manage-contrat.component.scss']
})
export class ManageContratComponent implements OnInit {

  displayedColumns: string[] = ['nomSociete','dateDebut','dateFin','edit'];
  dataSource:any;
  //length1:any;
  responseMessage:any;
  constructor(private contratService:ContratService,
    private ngxService:NgxUiLoaderService,
    private dialog:MatDialog,
    private snackbarService:SnackbarService,
    private router:Router) { }

  ngOnInit(): void {
    
    this.ngxService.start();
    this.tableData();
  }
  tableData(){
    this.contratService.getContrat().subscribe((response:any)=>{
      this.ngxService.stop();
      this.dataSource = new MatTableDataSource(response);
    },(error:any)=>{
      this.ngxService.stop();
      console.log(error.error?.message);
      if(error.error?.message){
        this.responseMessage = error.error?.message;
      }
      else{
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
    })
  }
applyFilter(event:Event){
  const filterValue = (event.target as HTMLInputElement).value;
  this.dataSource.filter= filterValue.trim().toLowerCase();
}

handleAddAction(){
  const dialogConfig = new MatDialogConfig();
  dialogConfig.data={
    action:'Add'
  };
  dialogConfig.width="850px";
  const dialogRef = this.dialog.open(ContratComponent,dialogConfig);
  this.router.events.subscribe(()=>{
    dialogRef.close();
  });
  const sub = dialogRef.componentInstance.onAddContrat.subscribe((response)=>{
    this.tableData();
  })

}
handleEditAction(values:any){
  const dialogConfig = new MatDialogConfig();
  dialogConfig.data={
    action:'Edit',
    data:values
  };
  dialogConfig.width="850px";
  const dialogRef = this.dialog.open(ContratComponent,dialogConfig);
  this.router.events.subscribe(()=>{
    dialogRef.close();
  });
  const sub = dialogRef.componentInstance.onEditContrat.subscribe((response)=>{
    this.tableData();
  })

}
handleDeleteAction(values:any){
  const dialogConfig = new MatDialogConfig();
  dialogConfig.data = {
    message:'delete '+values.name+' contact',
    confirmation:true
  }
  const dialogRef = this.dialog.open(ConfirmationComponent,dialogConfig);
  const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((response)=>{
    this.ngxService.start();
    this.deleteContact(values.id);
    dialogRef.close();


  })
}
deleteContact(id:any){
  this.contratService.delete(id).subscribe((response:any)=>{
    this.ngxService.stop();
    this.tableData();
    this.responseMessage = response?.message;
    this.snackbarService.openSnackBar(this.responseMessage,"success");

  },(error:any)=>{
    this.ngxService.stop();
      console.log(error);
      if(error.error?.message){
        this.responseMessage = error.error?.message;
      }
      else{
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
  })
}

downloadReportAction(values:any){}}
