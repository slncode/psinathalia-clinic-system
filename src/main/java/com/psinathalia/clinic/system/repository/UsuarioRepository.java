package com.psinathalia.clinic.system.repository;

import com.psinathalia.clinic.system.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    Optional<Usuario> findByCpfAndSenha(String cpf, String senha);
}
