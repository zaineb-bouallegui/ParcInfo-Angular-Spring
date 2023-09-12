package com.example.stage.wrapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContratWrapper {

    Integer id;
    String NomSociete;
    String DateDebut;
    String DateFin;

    public ContratWrapper(Integer id, String NomSociete, String DateDebut,String DateFin) {
        this.id = id;
        this.NomSociete=NomSociete;
        this.DateDebut=DateDebut;
        this.DateFin=DateFin;

    }


}
