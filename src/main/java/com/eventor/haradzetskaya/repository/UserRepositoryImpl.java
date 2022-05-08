package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public List<User> getAll() {
        Session session = entityManager.unwrap(Session.class);
        List<User> userList = session.createQuery("from User").getResultList();
        return userList;
    }

}
