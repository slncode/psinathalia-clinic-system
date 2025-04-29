package com.psinathalia.clinic.system.repository;

import com.psinathalia.clinic.system.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT DISTINCT p FROM Paciente p " +
            "LEFT JOIN FETCH p.pessoa pes " +
            "LEFT JOIN FETCH p.endereco " +
            "ORDER BY pes.nome")
    List<Paciente> findAllWithPessoaAndEnderecoOrdered();
}
