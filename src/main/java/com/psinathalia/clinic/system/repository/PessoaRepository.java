package com.psinathalia.clinic.system.repository;

import com.psinathalia.clinic.system.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByEmail(String email);
    Optional<Pessoa> findByCpf(String cpf);
}
