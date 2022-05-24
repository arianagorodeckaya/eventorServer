package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public User findByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        List<User> userList = session.createQuery("from User").getResultList();
        return userList.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public User findById(int id) {
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
    public void deleteUser(int id){
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class,id);
        session.delete(user);
    }

    @Override
    @Transactional
    public Page<User> findAll(Pageable pageable) {
        Query query = entityManager.createQuery("select a from User a");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<User> users = query.getResultList();

        Query queryCount = entityManager.createQuery("Select count(a.id) From User a");
        long count = (long) queryCount.getSingleResult();

        return new PageImpl<User>(users, pageable, count);
    }

    @Override
    public Long countUsers() {
        return (Long) entityManager.createQuery("Select count(a.id) From User a").getSingleResult();
    }
}
