package beans;

import models.Music;
import models.Playlist;
import models.User;

import java.util.Collection;

public interface PlaylistRemote {
    public Playlist createPlaylist(String name, User owner);
    public boolean modifyPlaylist(Playlist playlist, String newName);
    public boolean deletePlaylist(Playlist playlist);
    public boolean addMusicPlaylist(Playlist playlist, Music music);
    public boolean removeMusicPlaylist(Playlist playlist, Music music);
    public Collection<Playlist> getAll(User user, boolean isAscendent);
    public Collection<Music> getMusicsPlaylist(String id);
    public Playlist getPlaylist(String id);
}
