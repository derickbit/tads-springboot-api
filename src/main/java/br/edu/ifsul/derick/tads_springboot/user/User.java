package br.edu.ifsul.derick.tads_springboot.user;

import br.edu.ifsul.derick.tads_springboot.forumComment.ForumComment;
import br.edu.ifsul.derick.tads_springboot.forumTopic.ForumTopic;
import br.edu.ifsul.derick.tads_springboot.partida.Partida;
import br.edu.ifsul.derick.tads_springboot.patchNote.PatchNote;
import br.edu.ifsul.derick.tads_springboot.report.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor // Adiciona um construtor vazio (necessário para o JPA)
@AllArgsConstructor // Adiciona um construtor com todos os argumentos (Lombok)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password_hash;

    @Column(name = "avatar_url")
    private String avatar_url;

    @Column
    private String role;

    @Column(name = "email_verified_at")
    private LocalDateTime email_verified_at;

    // --- Relacionamentos ---
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

    // --- NOVOS CONSTRUTORES (para DTOs) ---

    // Construtor para POST (Criar)
    public User(UserDtoPost dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.password_hash = dto.password_hash();
        this.role = dto.role();
    }

    // Construtor para PUT (Atualizar)
    public User(Long id, UserDtoPut dto) {
        this.id = id;
        this.name = dto.name();
        this.email = dto.email();
        this.role = dto.role();
    }
}