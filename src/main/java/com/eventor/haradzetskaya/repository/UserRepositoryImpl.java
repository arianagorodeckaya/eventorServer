package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    // define field for entitymanager
    private EntityManager entityManager;


    // set up constructor injection
    @Autowired
    public UserRepositoryImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        List<User> userList = session.createQuery("from User").getResultList();
        return userList.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public User getById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class,id);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        User oldUser = session.get(User.class,user.getId());
        oldUser.setName(user.getName());
        oldUser.setPw_hash(user.getPw_hash());
        oldUser.setEmail(user.getEmail());
        oldUser.setBirthday(user.getBirthday());
        oldUser.setPhone(user.getPhone());
        oldUser.setPhoto(user.getPhoto());
        oldUser.setWork(user.getWork());
//        oldUser.setEvents(user.getEvents());
//        oldUser.setCreatorEvents(user.getCreatorEvents());
        session.update(oldUser);
        return oldUser;
    }

    @Override
    @Transactional
    public void deleteUser(int id){
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class,id);
        session.delete(user);
    }

    @Override
    @Transactional
    public List<User> getAll() {
        Session session = entityManager.unwrap(Session.class);
        List<User> userList = session.createQuery("from User").getResultList();
        return userList;
    }
}
