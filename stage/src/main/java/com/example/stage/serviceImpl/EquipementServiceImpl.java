package com.example.stage.serviceImpl;

import com.example.stage.JWT.JwtFilter;
import com.example.stage.POJO.Equipement;
import com.example.stage.POJO.User;
import com.example.stage.constents.StageConstants;
import com.example.stage.dao.EquipementDao;
import com.example.stage.service.EquipementService;
import com.example.stage.service.UserService;
import com.example.stage.utils.StageUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class EquipementServiceImpl implements EquipementService {
    @Autowired
    EquipementDao equipementDao;
    @Autowired
    JwtFilter jwtFilter;


    UserService userService;

    UserServiceImpl userServiceImpl;

    @Override
    public ResponseEntity<String> addNewEquipement(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateEquipementMap(requestMap, false)){
                    equipementDao.save(getEquipentFromMap(requestMap,false));
                    return StageUtils.getResponseEntity("Equipement Added Successfully",HttpStatus.OK);
                    
                }

            }else {
                return StageUtils.getResponseEntity(StageConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Equipement>> getAllEquipement(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return new ResponseEntity<List<Equipement>>(equipementDao.getAllEquipement(),HttpStatus.OK);
            }
            return new ResponseEntity<>(equipementDao.findAll(),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Equipement>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateEquipement(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateEquipementMap(requestMap,true)){
                  Optional optional= equipementDao.findById(Integer.parseInt(requestMap.get("id")));
                  if (!optional.isEmpty()){
                      equipementDao.save(getEquipentFromMap(requestMap,true));
                      return StageUtils.getResponseEntity("Equipement Updatetd Successfully",HttpStatus.OK);

                  }else {
                      return StageUtils.getResponseEntity("Equipement id does not exist",HttpStatus.OK);
                  }

                }
                return StageUtils.getResponseEntity(StageConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);

            }else {
                return StageUtils.getResponseEntity(StageConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteEquipement(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional = equipementDao.findById(id);
                if (!optional.isEmpty()){
                    equipementDao.deleteById(id);
                    return StageUtils.getResponseEntity("Equipement Deleted Successfully",HttpStatus.OK);

                }
                return StageUtils.getResponseEntity("Equipement is does not exist",HttpStatus.OK);

            }else {
                return StageUtils.getResponseEntity(StageConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateEquipementMap(Map<String, String> requestMap, boolean validateIdE) {
        if (requestMap.containsKey("marque") ){
            if (requestMap.containsKey("id") && validateIdE){
                return true;
            }else if (!validateIdE){
                return true;
            }
        }
        return false;
    }
    private Equipement getEquipentFromMap(Map<String ,String> requestMap,Boolean isAdd){
        Equipement equipement= new Equipement();
        if (isAdd){
            equipement.setIdE(Integer.parseInt(requestMap.get("idE")));
        }
        equipement.setMarque(requestMap.get("marque"));
        equipement.setNumSerie(requestMap.get("NumSerie"));
        equipement.setCodeBarre(requestMap.get("CodeBarre"));


        return equipement;
    }
    private Equipement getEquipementsByID(Integer  idE){
        Optional<Equipement> equipementOptional = equipementDao.findById( idE);
        return equipementOptional.orElse(null);
    }

    public void affectEquipementToUser(Integer idE, Integer id) {
        Equipement equipement = getEquipementsByID(idE);
        User user = userServiceImpl.findUserById(id);

        if (equipement != null && user != null) {
            equipement.setUser(user);
            equipementDao.save(equipement);
        } else {

        }
    }
}
