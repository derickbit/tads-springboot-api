package br.edu.ifsul.derick.tads_springboot.user;

// Importações de outras entidades do seu projeto
import br.edu.ifsul.derick.tads_springboot.forumComment.ForumComment;
import br.edu.ifsul.derick.tads_springboot.forumTopic.ForumTopic;
import br.edu.ifsul.derick.tads_springboot.partida.Partida;
import br.edu.ifsul.derick.tads_springboot.patchNote.PatchNote;
import br.edu.ifsul.derick.tads_springboot.report.Report;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// Importações do Spring Security (NOVAS)
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// Importações do Java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // Mantive o nome da sua tabela
public class User implements UserDetails { // Implementa UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // RENOMEADO (de password_hash para senha)
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "avatar_url")
    private String avatar_url;

    // O campo "role" antigo foi REMOVIDO

    @Column(name = "email_verified_at")
    private LocalDateTime email_verified_at;

    // --- Relacionamentos da Aplicação (iguais a antes) ---
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partida> partidas;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForumTopic> forumTopics;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatchNote> patchNotes;
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ForumComment> forumComments;

    // --- Relacionamento de Segurança (NOVO) ---
    @ManyToMany(fetch = FetchType.EAGER) // EAGER é crucial para segurança
    @JoinTable(
            name = "users_perfis", // Nome da nova tabela de junção
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private List<Perfil> perfis; // Lista de perfis

    // --- Construtores de DTO (Adaptados) ---
    // Precisamos ajustar o DTO 'password_hash' para 'senha'
    public User(UserDtoPost dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.senha = dto.senha(); // MUDOU
        // A "role" do DTO será tratada no Controller
    }

    // Este construtor também não mexe mais na role
    public User(Long id, UserDtoPut dto) {
        this.id = id;
        this.name = dto.name();
        this.email = dto.email();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte a List<Perfil> em uma List<SimpleGrantedAuthority>
        // O Spring Security entende isso perfeitamente.
        return perfis.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getNome()))
                .collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return this.senha; // Retorna a senha
    }

    @Override
    public String getUsername() {
        return this.email; // O email é o nosso "username"
    }

    // Para simplificar, vamos deixar tudo como 'true'
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
        // No futuro, você pode mudar isso para:
        // return this.email_verified_at != null;
    }
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }
}