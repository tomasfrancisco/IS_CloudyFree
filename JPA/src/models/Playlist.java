package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "playlist")
public class Playlist implements Serializable {
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(name = "name", nullable = false)
    @Basic
    private String name;
    @ManyToOne
    private User owner;
    @ManyToMany(mappedBy = "playlists", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Collection<Music> musics;

    public Playlist(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public Playlist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<Music> getMusics() {
        return musics;
    }

    public void setMusics(Collection<Music> musics) {
        this.musics = musics;
    }
}
