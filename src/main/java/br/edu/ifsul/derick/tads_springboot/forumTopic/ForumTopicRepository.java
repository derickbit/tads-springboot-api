package br.edu.ifsul.derick.tads_springboot.forumTopic;

import br.edu.ifsul.derick.tads_springboot.forumTopic.ForumTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumTopicRepository extends JpaRepository<ForumTopic, Long> {
}
