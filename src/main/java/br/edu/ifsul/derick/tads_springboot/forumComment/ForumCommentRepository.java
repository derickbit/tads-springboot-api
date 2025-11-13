package br.edu.ifsul.derick.tads_springboot.forumComment;

import br.edu.ifsul.derick.tads_springboot.forumComment.ForumComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumCommentRepository extends JpaRepository<ForumComment, Long> {
}
