package no.adrsolheim.nightfly.controller;

import no.adrsolheim.nightfly.dto.AlbumDto;
import no.adrsolheim.nightfly.model.Album;
import no.adrsolheim.nightfly.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumDto> addAlbum(@RequestBody AlbumDto albumDto) {
        // Status code: 201
        return ResponseEntity.status(HttpStatus.CREATED).body(albumService.save(albumDto));
    }

    @GetMapping
    public ResponseEntity<List<AlbumDto>> getAllSubreddits() {
        return ResponseEntity.status(HttpStatus.OK).body(albumService.getAll());
    }
}
