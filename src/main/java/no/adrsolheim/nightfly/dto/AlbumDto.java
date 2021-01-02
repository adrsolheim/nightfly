package no.adrsolheim.nightfly.dto;

public class AlbumDto {
    private Integer id;
    private String title;
    private String artist;
    private Integer rating;

    public AlbumDto() {

    }

    public AlbumDto(String title, String artist, Integer rating) {
        this.title = title;
        this.artist = artist;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
