package no.adrsolheim.nightfly.repository;

import no.adrsolheim.nightfly.dto.AlbumDto;
import no.adrsolheim.nightfly.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    boolean existsById(Integer id);
    List<Album> findAll();
    Album save(Album album);
}
