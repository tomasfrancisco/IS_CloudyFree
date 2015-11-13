package beans;

import models.Music;

import java.util.Collection;

public interface MusicRemote {

    public Music createMusic(String title, String artist, String album, int year, String path);
    public Music modifyMusic(Music music, String title, String artist, String album, int year, String path);
    public boolean detachMusic(Music music);
    public Music getMusic(String id);
    public Collection<Music> getAll();
    public Collection<Music> searchMusic(String criteria);
}
