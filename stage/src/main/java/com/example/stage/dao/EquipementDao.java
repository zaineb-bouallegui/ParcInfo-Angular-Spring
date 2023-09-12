package com.example.stage.dao;

import com.example.stage.POJO.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipementDao extends JpaRepository<Equipement,Integer> {


    List<Equipement> getAllEquipement();
}
