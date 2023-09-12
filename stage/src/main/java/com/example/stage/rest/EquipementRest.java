package com.example.stage.rest;

import com.example.stage.POJO.Equipement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin("*")

@RequestMapping(path = "/equipement")
public interface EquipementRest {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewEquipement(@RequestBody(required = true)
                                            Map<String,String> requestMap);
    @GetMapping(path = "/get")
    ResponseEntity<List<Equipement>> getAllEquipement(@RequestBody(required = false)
                                                      String filterValue);
    @PostMapping(path = "/update")
    ResponseEntity<String> updateEquipement(@RequestBody(required = true)
                                            Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteEquipement(@PathVariable Integer id);




}
