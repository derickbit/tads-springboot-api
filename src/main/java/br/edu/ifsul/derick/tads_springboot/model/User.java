package br.edu.ifsul.derick.tads_springboot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partida> partidas;

    // Altere aqui para "author"
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<br.edu.ifsul.derick.tads_springboot.model.ForumTopic> forumTopics;

    // Altere aqui para "author" também (um usuário é autor de um patch note)
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatchNote> patchNotes;

    // Altere aqui para "reporter" (um usuário que reporta)
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;
}