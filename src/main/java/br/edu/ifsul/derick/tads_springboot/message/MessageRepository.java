package br.edu.ifsul.derick.tads_springboot.message;

import br.edu.ifsul.derick.tads_springboot.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
