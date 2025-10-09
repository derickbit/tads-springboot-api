package br.edu.ifsul.derick.tads_springboot.repository;

import br.edu.ifsul.derick.tads_springboot.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
}
