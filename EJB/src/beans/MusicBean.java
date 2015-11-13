package beans;

import models.Music;
import models.Playlist;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Stateless
public class MusicBean implements MusicRemote {
    @PersistenceContext(name = "Musics")
    EntityManager em;

    public MusicBean() {
    }

    public Music createMusic(String title, String artist, String album, int year, String path) {
        try {
            Music music = new Music(title, artist, album, year, path);
            this.em.persist(music);
            return music;
        } catch (Exception e) {

        }
        return null;
    }

    public Music modifyMusic(Music music, String title, String artist, String album, int year, String path) {
        Music persist = this.em.find(Music.class, music.getId());
        try {
            if(title != null)
                persist.setTitle(title);
            if(artist != null)
                persist.setArtist(artist);
            if(album != null)
                persist.setAlbum(album);
            if(year != 0)
                persist.setYear(year);
            if(path != null)
                persist.setPath(path);
            return music;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean detachMusic(Music music) {
        try {
            music.setUser(null);
            this.em.persist(music);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public Music getMusic(String id) {
        try {
            Query query = this.em.createQuery("from Music where id = :id");
            query.setParameter("id", Long.parseLong(id));
            return (Music) query.getSingleResult();
        } catch (Exception e) {

        }
        return null;
    }

    public Collection<Music> getAll() {
        Query query = this.em.createQuery("from Music ");
        return query.getResultList();
    }

    public Collection<Music> searchMusic(String criteria) {
        try {
            Query query = this.em.createQuery("from Music where LOWER(title) like :criteria or lower(artist) like :criteria");
            query.setParameter("criteria", "%" + criteria.toLowerCase() + "%");
            return query.getResultList();
        } catch (Exception e) {

        }
        return null;
    }
}
