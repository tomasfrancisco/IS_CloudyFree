package beans;

import models.User;

import javax.ejb.Remote;
import java.util.Collection;

@Remote
public interface UserRemote {
    public void createUser(String email, String password);
    public Collection<User> getAllUsers();
}
