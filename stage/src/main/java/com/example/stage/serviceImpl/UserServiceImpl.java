package com.example.stage.serviceImpl;

import com.example.stage.JWT.CustomerUserDetailsService;
import com.example.stage.JWT.JwtFilter;
import com.example.stage.JWT.JwtUtil;
import com.example.stage.POJO.Equipement;
import com.example.stage.POJO.User;
import com.example.stage.constents.StageConstants;
import com.example.stage.dao.UserDao;
import com.example.stage.service.UserService;
import com.example.stage.utils.EmailUtils;
import com.example.stage.utils.StageUtils;
import com.example.stage.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired

    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    EmailUtils emailUtils;



    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    String encodedPassword = passwordEncoder.encode(requestMap.get("password"));
                    User newUser = getUserFromMap(requestMap);
                    newUser.setPassword(encodedPassword); // Enregistrez le mot de passe encodé
                    userDao.save(newUser);
                    return StageUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return StageUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return StageUtils.getResponseEntity(StageConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetail().getEmail(),
                                    customerUserDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval. " + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception ex) {
            log.error("{}", ex);

        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials. " + "\"}",
                HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(),HttpStatus.OK);

            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception ex){
            ex.printStackTrace();

        }
        return new  ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
             Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
             if (!optional.isEmpty()){
                 userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                 sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                 return StageUtils.getResponseEntity("User Status Updated Successfully", HttpStatus.OK);

             }else{
                 StageUtils.getResponseEntity("User id does not exist", HttpStatus.OK);
             }

            }else {
                return StageUtils.getResponseEntity(StageConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return StageUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (userObj != null) {
                if (passwordEncoder.matches(requestMap.get("oldPassword"), userObj.getPassword())) {
                    // Le mot de passe actuel est correct
                    String newPassword = requestMap.get("newPassword");
                    String hashedPassword = passwordEncoder.encode(newPassword);
                    userObj.setPassword(hashedPassword);
                    userDao.save(userObj);
                    return StageUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                } else {
                    return StageUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
                }
            }
            return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            // Gérer les exceptions
        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail()))

                emailUtils.forgotMail(user.getEmail(), "Credentials by ParcInfo Management System",user.getPassword());
                return StageUtils.getResponseEntity("Check your mail for Credentials", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            if(jwtFilter.isAdmin()){
                Optional optional = userDao.findById(id);
                if (!optional.isEmpty()){
                    userDao.deleteById(id);
                    return StageUtils.getResponseEntity("User Deleted Successfully",HttpStatus.OK);

                }
                return StageUtils.getResponseEntity("User is does not exist",HttpStatus.OK);

            }else {
                return StageUtils.getResponseEntity(StageConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();

        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<UserWrapper> getUserById(Integer id) {
        try {
            return new ResponseEntity<>(userDao.getUserById(id),HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new UserWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addNewUser(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if (ValidateUserMap(requestMap, false)){
                    userDao.save(getUserFromMapp(requestMap,false));
                    return StageUtils.getResponseEntity("User Added Successfully",HttpStatus.OK);

                }

            }else {
                return StageUtils.getResponseEntity(StageConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StageUtils.getResponseEntity(StageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private User getUserFromMapp(Map<String, String> requestMap, boolean isAdd) {
        User user= new User();
        if (isAdd){
            user.setId(Integer.parseInt(requestMap.get("id")));
        }
        user.setName(requestMap.get("name"));
        user.setCIN(requestMap.get("CIN"));
        user.setAddress(requestMap.get("address"));
        user.setContactNumber(requestMap.get("contactNumber"));



        System.out.println("id :"+user.getId());
        System.out.println("name: " + user.getId());
        System.out.println("CIN: " + user.getCIN());
        System.out.println("address: " + user.getAddress());
        System.out.println("contactNumber: " + user.getContactNumber());


        return user;
    }

    private boolean ValidateUserMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")   ){
            if (requestMap.containsKey("id") && validateId){
                return true;
            }else if (!validateId){
                return true;
            }
        }
        return false;
    }


    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved", "USER:- "+ user +" \n is approved by \nADMIN:-" + jwtFilter.getCurrentUser(),allAdmin);

        }else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled", "USER:- "+ user +" \n is disabled by \nADMIN:-" + jwtFilter.getCurrentUser(),allAdmin);

        }
    }
    public User findUserById(Integer id) {
        Optional<User> userOptional = userDao.findById(id);
        return userOptional.orElse(null);
    }
}
