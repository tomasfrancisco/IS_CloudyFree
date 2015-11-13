package beans;

import models.User;

import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface UserRemote {
    public User createUser(String email, String password);
    public boolean modifyUser(User user, String email, String password);
    public boolean deleteUser(User user);
    public User getUser(String email, String password);
    public User getUser(String email);
    public Collection<User> getAllUsers();
}
