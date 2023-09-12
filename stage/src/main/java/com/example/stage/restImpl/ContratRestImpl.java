package com.example.stage.restImpl;

import com.example.stage.constents.StageConstants;
import com.example.stage.dao.ContratDao;
import com.example.stage.dao.UserDao;
import com.example.stage.rest.ContratRest;
import com.example.stage.service.ContratService;
import com.example.stage.utils.StageUtils;
import com.example.stage.wrapper.ContratWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class ContratRestImpl implements ContratRest {
@Autowired
    ContratService contratService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ContratDao contratDao;

    @Override
    public ResponseEntity<String> addNewContrat(Map<String, String> requestMap) {
        try {
            return contratService.addNewContrat(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


   /* @Override
    public ResponseEntity<List<Contrat>> getAllContrat(String filterValue) {
        try {
            return contratService.getAllContrat(filterValue);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @Override
    public ResponseEntity<String> updateContrat(Map<String, String> requestMap) {
        try {
            return contratService.updateContrat(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteContrat(Integer id) {

        try {
            return contratService.deleteContrat(id);
        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ContratWrapper>> getAllContact() {
        try {
            return contratService.getAllContrat();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }



   /* @Override
    public ResponseEntity<ContratWrapper> getContratById(Integer id) {
        try {
            return new ResponseEntity<>(contratDao.getContratById(id), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return new ResponseEntity<>(new ContratWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);

    }*/



    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try {
            return contratService.generateReport(requestMap);

        }catch (Exception ex){
            ex.printStackTrace();}
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
