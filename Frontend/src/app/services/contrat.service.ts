import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class ContratService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }


  add(data:any){
   /// const headers=new HttpHeaders.set("Autorization",
    //this.localStorage.retrieve('authentificationToken'))
    return this.httpClient.post(this.url+
      "/contrat/add",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
    }

  

  
  update(data:any){
    return this.httpClient.post(this.url+ 
      "/contrat/update",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }
  
  getContrat(){
    return this.httpClient.get(this.url+"/contrat/get");
  }

  delete(id:any){
    return this.httpClient.post(this.url+ 
      "/contrat/delete/"+id,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
   
  }
}
