package br.edu.ifsul.derick.tads_springboot.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    // Método para buscar um perfil pelo nome (ex: "ROLE_USER")
    Perfil findByNome(String nome);
}