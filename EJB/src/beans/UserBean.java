package beans;

import models.User;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Stateful
public class UserBean implements UserRemote {
    @PersistenceContext(name = "Users")
    EntityManager em;


    public UserBean() {
    }

    @Override
    public void createUser(String email, String password) {
        User user = new User(email, password);
        this.em.persist(user);
    }

    @Override
    public Collection<User> getAllUsers() {
        Query q = em.createQuery("from User");
        Collection<User> result = q.getResultList();
        return result;
    }
}
