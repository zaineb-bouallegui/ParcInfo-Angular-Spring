package com.example.stage.POJO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Contrat.getAllContrat", query = "select new   com.example.stage.wrapper.ContratWrapper(c.id,c.NomSociete,c.DateDebut,c.DateFin) from Contrat c")
/*@NamedQuery(name = "Contrat.getContratById",query = "select new com.example.stage.wrapper.ContratWrapper(c.id,c.NomSociete,c.DateDebut,c.DateFin) from Contrat c where c.id=:id")*/
@Entity
@NoArgsConstructor
@Getter
@Setter

@Table(name="Contrat")
public class Contrat implements Serializable {
    private static final long serialVersionUID = 123456L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "NomSociete")
    private String NomSociete;
    @Column(name = "DateDebut")
    private String DateDebut;
    @Column(name = "DateFin")
    private String DateFin;

  /*  @Column(name = "contratdetails",columnDefinition = "json")
    private String contratdetails;*/

    @Column(name = "createdby")
    private String createdby;





}
