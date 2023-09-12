package com.example.stage.service;

import com.example.stage.POJO.Contrat;
import com.example.stage.POJO.Equipement;
import com.example.stage.wrapper.ContratWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ContratService {
    ResponseEntity<String> addNewContrat(Map<String,String> requestMap);

   /* ResponseEntity<List<Contrat>> getAllContrat(String filterValue);*/
    ResponseEntity<String> updateContrat(Map<String,String> requestMap);
    ResponseEntity<String> deleteContrat(Integer id);
ResponseEntity<List<ContratWrapper>> getAllContrat();
/*ResponseEntity<ContratWrapper> getContratById(Integer id);*/

ResponseEntity<String> generateReport(Map<String,Object> requestMap);


}
