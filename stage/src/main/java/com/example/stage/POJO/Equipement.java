package com.example.stage.POJO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@NamedQuery(name = "Equipement.getAllEquipement", query = "select e from Equipement  e ")

@Entity
@NoArgsConstructor
@Getter
@Setter

@Table(name="Equipement")
public class Equipement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idE;
    @Column(name = "marque")
    private String marque;
    @Column(name = "NumSerie")
    private String NumSerie;
    @Column(name = "CodeBarre")
    private String CodeBarre;
    @Column(name = "Component")
    @Enumerated(EnumType.STRING)
    private Component component;
    @ManyToOne
    User user;
}
