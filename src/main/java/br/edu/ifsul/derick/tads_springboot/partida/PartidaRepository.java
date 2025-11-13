package br.edu.ifsul.derick.tads_springboot.partida;

import br.edu.ifsul.derick.tads_springboot.partida.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
}
