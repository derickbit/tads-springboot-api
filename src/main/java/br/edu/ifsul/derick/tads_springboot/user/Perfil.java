package br.edu.ifsul.derick.tads_springboot.user;

import com.fasterxml.jackson.annotation.JsonIgnore; // <--- Import novo e essencial
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Entity(name = "Perfil")
@Table(name = "perfis")
@NoArgsConstructor
@Getter
@Setter
public class Perfil implements GrantedAuthority {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // "perfis" é o nome do campo na classe User.
    // O @JsonIgnore diz: "Quando transformar Perfil em texto, NÃO mostre a lista de usuários".
    @ManyToMany(mappedBy = "perfis")
    @JsonIgnore
    private List<User> usuarios;

    @Override
    public String getAuthority() {
        // Isso é o que o Spring Security vai ler, ex: "ROLE_ADMIN"
        return nome;
    }
}