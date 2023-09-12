package com.example.stage.restImpl;

import com.example.stage.POJO.Equipement;
import com.example.stage.constents.StageConstants;
import com.example.stage.rest.EquipementRest;
import com.example.stage.service.EquipementService;
import com.example.stage.serviceImpl.EquipementServiceImpl;
import com.example.stage.utils.StageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EquipementRestImpl implements EquipementRest {
    @Autowired
    EquipementService equipementService;
    EquipementServiceImpl equipementServiceimpl;
    @Override
    public ResponseEntity<String> addNewEquipement(Map<String, String> requestMap) {
        try {
            return equipementService.addNewEquipement(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Equipement>> getAllEquipement(String filterValue) {
        try {
            return equipementService.getAllEquipement(filterValue);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateEquipement(Map<String, String> requestMap) {
        try {
            return equipementService.updateEquipement(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteEquipement(Integer idE) {
        try {
            return equipementService.deleteEquipement(idE);
        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/assign-equipement/{equipementId}/to-user/{userId}")
    public String assignEquipementToUser(@PathVariable Integer idE, @PathVariable Integer id) {
        equipementServiceimpl.affectEquipementToUser(idE, id);
        return "Equipement assigned to user.";
    }

}

