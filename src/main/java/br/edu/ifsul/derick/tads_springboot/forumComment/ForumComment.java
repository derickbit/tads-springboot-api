package br.edu.ifsul.derick.tads_springboot.forumComment;

import br.edu.ifsul.derick.tads_springboot.forumTopic.ForumTopic;
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
@Table(name = "forum_comments")
public class ForumComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String body;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "forum_topic_id", nullable = false)
    private ForumTopic forumTopic;
}
