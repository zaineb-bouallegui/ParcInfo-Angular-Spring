package com.example.stage.rest;

import com.example.stage.POJO.Contrat;

import com.example.stage.wrapper.ContratWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")

@RequestMapping(path = "/contrat")
public interface ContratRest {

@PostMapping(path = "/add")
ResponseEntity<String> addNewContrat(@RequestBody Map<String,String> requestMap);
   /* @GetMapping(path = "/get")
    ResponseEntity<List<Contrat>> getAllContrat(@RequestBody(required = false)
                                                      String filterValue);*/
    @PostMapping(path = "/update")
    ResponseEntity<String> updateContrat(@RequestBody Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteContrat(@PathVariable Integer id);

  /*  @PostMapping(path = "/generateReport")
    ResponseEntity<String> generateReport(@RequestBody Map<String,Object>requestMap);


*/
    @GetMapping(path = "/get")
    ResponseEntity<List<ContratWrapper>> getAllContact();
/*@GetMapping(path = "/getById/{id}")
    ResponseEntity<ContratWrapper> getContratById(@PathVariable Integer id);*/

@PostMapping(path = "/generateReport")
ResponseEntity<String> generateReport(@RequestBody Map<String,Object>requestMap);


}