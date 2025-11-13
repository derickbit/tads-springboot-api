package br.edu.ifsul.derick.tads_springboot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Adicione esta linha para alinhar com o professor
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
    // Aqui poderíamos adicionar buscas customizadas, como o professor fez com findByNome
    // Ex: List<User> findByName(String name);
}