package com.example.stage.dao;

import com.example.stage.POJO.Contrat;

import com.example.stage.wrapper.ContratWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ContratDao extends JpaRepository<Contrat, Integer> {
    List<ContratWrapper> getAllContrat();

  /* ContratWrapper getContratById(@Param("id") Integer id);*/
}
