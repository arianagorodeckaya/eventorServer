package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EventRepositoryImpl implements EventRepository{

    private EntityManager entityManager;


    // set up constructor injection
    @Autowired
    public EventRepositoryImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


    @Override
    @Transactional
    public Event getById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Event.class,id);
    }

    @Override
    @Transactional
    public List<Event> getAll() {
        return null;
    }

    @Override
    @Transactional
    public Event saveEvent(Event event) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(event);
        return event;
    }

    @Override
    @Transactional
    public Event updateEvent(User user) {
        return null;
    }

    @Override
    @Transactional
    public void deleteEvent(int id) {

    }
}
