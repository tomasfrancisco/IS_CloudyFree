package beans;

import models.Music;
import models.Playlist;
import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Stateless
public class PlaylistBean implements PlaylistRemote {
    @PersistenceContext(name = "Playlists")
    EntityManager em;

    public PlaylistBean() {
    }

    public Playlist createPlaylist(String name, User owner) {
        try {
            Playlist playlist = new Playlist(name, owner);
            this.em.persist(playlist);
            return playlist;
        } catch (Exception e) {

        }
        return null;
    }

    public boolean modifyPlaylist(Playlist playlist, String newName) {
        try {
            playlist.setName(newName);
            this.em.persist(playlist);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public boolean deletePlaylist(Playlist playlist) {
        try {
            this.em.getTransaction();
            this.em.remove(playlist);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public boolean addMusicPlaylist(Playlist playlist, Music music) {
        Playlist persistPlaylist = this.em.find(Playlist.class, playlist.getId());
        Music persistMusic = this.em.find(Music.class, music.getId());
        try {
            persistPlaylist.getMusics().add(music);
            persistMusic.getPlaylists().add(playlist);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeMusicPlaylist(Playlist playlist, Music music) {
        Playlist persistPlaylist = this.em.find(Playlist.class, playlist.getId());
        Music persistMusic = this.em.find(Music.class, music.getId());
        try {
            persistMusic.getPlaylists().remove(playlist);
            persistPlaylist.getMusics().remove(music);
            this.em.persist(persistMusic);
            this.em.persist(persistPlaylist);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Collection<Playlist> getAll(User user, boolean isAscendent) {
        Query query = null;
        if(isAscendent)
            query = this.em.createQuery("from Playlist where owner = :user order by name asc");
        else
            query = this.em.createQuery("from Playlist where owner = :user order by name desc");

        query.setParameter("user", user);
        return query.getResultList();
    }

    public Collection<Music> getMusicsPlaylist(String id) {
        Query query = this.em.createQuery("from Playlist where id = :id");
        query.setParameter("id", Long.parseLong(id));

        Playlist playlist = (Playlist) query.getSingleResult();
        return playlist.getMusics();
    }

    public Playlist getPlaylist(String id) {
        Query query = this.em.createQuery("from Playlist where id = :id");
        query.setParameter("id", Long.parseLong(id));

        Playlist playlist = (Playlist) query.getSingleResult();
        return playlist;
    }
}
