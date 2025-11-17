package br.edu.ifsul.derick.tads_springboot.autenticacao;

import br.edu.ifsul.derick.tads_springboot.user.User;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;

/*
 * Como o professor explicou, este repositório é SÓ para autenticação.
 * Ele não tem CRUD, por isso estende 'Repository' e não 'JpaRepository'.
 */
public interface AutenticacaoRepository extends Repository<User, Long> {

    // O Spring Security vai usar isso para buscar o usuário pelo email
    @Query("SELECT u FROM User u JOIN FETCH u.perfis WHERE u.email = :email")
    User findByEmail(String email);
}