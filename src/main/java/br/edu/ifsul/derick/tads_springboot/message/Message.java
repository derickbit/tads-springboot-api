package br.edu.ifsul.derick.tads_springboot.message;

import br.edu.ifsul.derick.tads_springboot.report.Report;
import br.edu.ifsul.derick.tads_springboot.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String body;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "sent_at")
    private LocalDateTime sent_at;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;
}
