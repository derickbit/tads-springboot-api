package br.edu.ifsul.derick.tads_springboot.partida;

import br.edu.ifsul.derick.tads_springboot.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String game;

    private int score;

    @Column(name = "match_date")
    private LocalDateTime match_date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
