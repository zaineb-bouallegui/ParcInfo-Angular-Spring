package com.example.stage.serviceImpl;

import com.example.stage.JWT.JwtFilter;
import com.example.stage.POJO.Contrat;

import com.example.stage.constents.StageConstants;
import com.example.stage.dao.ContratDao;
import com.example.stage.service.ContratService;
import com.example.stage.utils.StageUtils;

import com.example.stage.wrapper.ContratWrapper;

import com.google.gson.JsonArray;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.swing.text.DocumentFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class ContratServiceImpl implements ContratService {
@Autowired
    JwtFilter jwtFilter;
@Autowired
    ContratDao contratDao;
    @Override
    public ResponseEntity<String> addNewContrat(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateContratMap(requestMap, false)){
                    contratDao.save(getContratFromMap(requestMap,false));
                    return StageUtils.getResponseEntity("Contract Added Successfully", HttpStatus.OK);

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

  /*  @Override
    public ResponseEntity<List<Contrat>> getAllContrat(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
                log.info("Inside if");
                return new ResponseEntity<List<Contrat>>(contratDao.getAllContrat(),HttpStatus.OK);
            }
            return new ResponseEntity<>(contratDao.findAll(),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Contrat>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/
    @Override
    public ResponseEntity<String> updateContrat(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (validateContratMap(requestMap,true)){
                    Optional<Contrat> optional= contratDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()){
                        Contrat contrat = getContratFromMap(requestMap,true);
                        contratDao.save(contrat);
                        return StageUtils.getResponseEntity("Contrat Updatetd Successfully",HttpStatus.OK);

                    }else {
                        return StageUtils.getResponseEntity("Contrat id does not exist",HttpStatus.OK);
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
    public ResponseEntity<String> deleteContrat(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional = contratDao.findById(id);
                if (!optional.isEmpty()){
                    contratDao.deleteById(id);
                    return StageUtils.getResponseEntity("contrat Deleted Successfully",HttpStatus.OK);

                }
                return StageUtils.getResponseEntity("contrat is does not exist",HttpStatus.OK);

            }else {
                return StageUtils.getResponseEntity(StageConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    /*@Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Inside generateReport");
        try {
            String fileName;
            if(validateRequestMap(requestMap)){
                if(requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate"));
                fileName = (String) requestMap.get("uuid");

            }else{
                fileName =
            }
            return StageUtils.getResponseEntity("Required data not found",HttpStatus.BAD_REQUEST);

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }*/

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("nomSociete") &&
                requestMap.containsKey("dateDebut") &&
                requestMap.containsKey("dateFin");

    }

    private Contrat getContratFromMap(Map<String, String> requestMap, boolean isAdd) {
        Contrat contrat= new Contrat();
        if (isAdd){
            contrat.setId(Integer.parseInt(requestMap.get("id")));
        }

        contrat.setNomSociete(requestMap.get("NomSociete"));
        contrat.setDateDebut(requestMap.get("DateDebut"));
        contrat.setDateFin(requestMap.get("DateFin"));


        System.out.println("id: " + contrat.getId());
        System.out.println("NomSociete: " + contrat.getNomSociete());
        System.out.println("Debut: " + contrat.getDateDebut());
        System.out.println("Fin: " + contrat.getDateFin());


        return contrat;
    }

    private boolean validateContratMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("NomSociete") ){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<List<ContratWrapper>> getAllContrat() {
        try {
            return new ResponseEntity<>(contratDao.getAllContrat(),HttpStatus.OK);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
         log.info("inside generateReport");
       try {
           String fileName;
           if(validateRequestMaps(requestMap)){
               if (requestMap.containsKey("inGenerate") && !(Boolean) requestMap.get("inGenerate")){
               fileName = (String) requestMap.get("uuid");


           }else {
               fileName=StageUtils.getUUID();
               requestMap.put("uuid",fileName);
               insertContrat(requestMap);
           }
               String data= "NomSociete: "+requestMap.get("NomSociete") +"\n"+"DateDebut: "+requestMap.get("DateDebut")+
                       "\n"+"DateDebut: "+requestMap.get("DateFin") + "\n" + "DateFin" + requestMap.get("DateFin");
               Document document = new Document() ;
               PdfWriter.getInstance(document,new FileOutputStream(StageConstants.STORE_LOCATION+"\\"+fileName+".pdf"));

               document.open();
               setRectangleInPdf(document);
               Paragraph chunk = new Paragraph("Syetem de Gestion des Contrats", getFont("Header"));
               chunk.setAlignment(Element.ALIGN_CENTER);
               document.add(chunk);

               Paragraph paragraph =new Paragraph(data+"\n \n", getFont("Data"));
               document.add(paragraph);


               PdfPTable table = new PdfPTable(3);
               table.setWidthPercentage(100);
               addTableHeader(table);
               JSONArray jsonArray =StageUtils.getJsonArrayFromString((String) requestMap.get("contratDetails"));
               for (int i=0; i< jsonArray.length(); i++){
                   addRows(table,StageUtils.getMapFromJson(jsonArray.getString(i)));

               }
               document.add(table);

               Paragraph footer = new Paragraph("Thank you ",getFont("data"));
               document.add(footer);
               document.close();
               return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);

           }

           return StageUtils.getResponseEntity("Required data not find",HttpStatus.BAD_REQUEST);

       }catch (Exception ex){
           ex.printStackTrace();

       }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);


    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("NomSociete"));
        table.addCell((String) data.get("DateDebut"));
        table.addCell((String) data.get("DateFin"));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("NomSociete","DateDebut","DateFin")
                .forEach(columnTiltle ->{
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorder(2);
                    header.setPhrase(new Phrase(columnTiltle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type){
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK) ;
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rec = new Rectangle(577,825,18,15);
        rec.enableBorderSide(1);
        rec.enableBorderSide(2);
        rec.enableBorderSide(4);
        rec.enableBorderSide(8);
        rec.setBackgroundColor(BaseColor.BLACK);
        rec.setBorderWidth(1);
        document.add(rec);

    }

    private void insertContrat(Map<String, Object> requestMap) {
        try {
            Contrat contrat = new Contrat();
            contrat.setUuid((String) requestMap.get("uuid"));
            contrat.setNomSociete((String)requestMap.get("NomSociete"));
            contrat.setDateDebut((String)requestMap.get("DateDebut"));
            contrat.setDateFin((String)requestMap.get("DateFin"));
          /*  contrat.setContratdetails((String)requestMap.get("contratdetails"));*/
            contrat.setCreatedby(jwtFilter.getCurrentUser());
            contratDao.save(contrat);

        }catch (Exception ex){
            ex.printStackTrace();

        }
    }

    private boolean validateRequestMaps(Map<String, Object> requestMap) {
        return requestMap.containsKey("NomSociete") &&
                requestMap.containsKey("DateDebut") &&
                requestMap.containsKey("DateFin");
    }

   /* @Override
    public ResponseEntity<ContratWrapper> getContratById(Integer id) {
        try {
            return new ResponseEntity<>(ContratDao.getContratById(id), HttpStatus.OK)

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ContratWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);

    }*/


}
