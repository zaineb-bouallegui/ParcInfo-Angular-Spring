package com.example.stage.POJO;

import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "User.findByEmailId",query= "select u from User u where u.email=:email")
@NamedQuery(name = "User.getAllUser", query = "select new com.example.stage.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from User u where u.role='user'")
@NamedQuery(name = "User.updateStatus",query = "update User u set u.status=:status where u.id=:id")
@NamedQuery(name = "User.getAllAdmin", query = "select u.email from User u where u.role='admin'")
@NamedQuery(name = "User.getUserById",query = "select new com.example.stage.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from User u where u.id=:id")

@Entity
@NoArgsConstructor
@Getter
@Setter

@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "CIN")
    private String CIN;
    @Column(name="address")
    private String address;


    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;
    @Column(name = "role")
    private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Equipement> equipements ;




}
