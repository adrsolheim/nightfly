package no.adrsolheim.nightfly.service;

import no.adrsolheim.nightfly.dto.AlbumDto;
import no.adrsolheim.nightfly.model.Album;
import no.adrsolheim.nightfly.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Transactional
    public AlbumDto save(AlbumDto albumDto) {
        Album album = new Album();
        album.setArtist(albumDto.getArtist());
        album.setTitle(albumDto.getTitle());
        album.setRating(albumDto.getRating());

        Album save = albumRepository.save(album);
        albumDto.setId(save.getAlbumId());
        return albumDto;
    }

    private void mapAlbumDto(AlbumDto albumDto) {
        // TODO: delegate reponsibility of creatin AlbumDto to builder
    }

    private AlbumDto mapAlbum(Album album) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setId(album.getAlbumId());
        albumDto.setRating(album.getRating());
        albumDto.setTitle(album.getTitle());
        albumDto.setArtist(album.getArtist());
        return albumDto;
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAll() {
        List<Album> albums = albumRepository.findAll();
        List<AlbumDto> result = new ArrayList<AlbumDto>();
        for(Album a : albums) {
            AlbumDto adto = new AlbumDto();
            adto.setId(a.getAlbumId());
            adto.setArtist(a.getArtist());
            adto.setRating(a.getRating());
            adto.setTitle(a.getTitle());
            result.add(adto);
        }
        return result;
    }

    @Transactional
    public AlbumDto getAlbum(int id) {
        AlbumDto albumDto = mapAlbum(albumRepository.getOne(id));
        return albumDto;
    }
}
