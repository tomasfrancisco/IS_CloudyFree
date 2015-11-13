package beans;

import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Stateless
public class UserBean implements UserRemote {
    @PersistenceContext(name = "Users")
    EntityManager em;


    public UserBean() {
    }

    public User createUser(String email, String password) {
        try {
            User user = new User(email, password);
            this.em.persist(user);
            return user;
        } catch (Exception e) {

        }
        return null;
    }

    public boolean modifyUser(User user, String email, String password) {
        try {
            user.setEmail(email);
            user.setPassword(password);

            this.em.persist(user);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public boolean deleteUser(User user) {
        try {
            this.em.getTransaction();
            this.em.remove(user);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    public Collection<User> getAllUsers() { // Just a test method TODO - Needs to be removed on deployment
        try {
            Query q = em.createQuery("from User");
            Collection<User> result = q.getResultList();
            return result;
        } catch (Exception e) {

        }
        return null;
    }

    public User getUser(String email, String password) {
        Query query = em.createQuery("from User where email = :email and password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return null;
    }

    public User getUser(String email) {
        Query query = em.createQuery("from User where email = :email");
        query.setParameter("email", email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {

        }
        return null;
    }
}
