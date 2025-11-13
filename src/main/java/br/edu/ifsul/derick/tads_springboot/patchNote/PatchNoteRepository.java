package br.edu.ifsul.derick.tads_springboot.patchNote;

import br.edu.ifsul.derick.tads_springboot.patchNote.PatchNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatchNoteRepository extends JpaRepository<PatchNote, Long> {
}
