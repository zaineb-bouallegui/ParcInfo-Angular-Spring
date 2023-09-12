package com.example.stage.service;

import com.example.stage.POJO.Equipement;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EquipementService {

    ResponseEntity<String> addNewEquipement(Map<String,String> requestMap);
    ResponseEntity<List<Equipement>> getAllEquipement(String filterValue);
    ResponseEntity<String> updateEquipement(Map<String,String> requestMap);
    ResponseEntity<String> deleteEquipement(Integer idE);

}
