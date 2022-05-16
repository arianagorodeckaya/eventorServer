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
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT a FROM Event a", Event.class).getResultList();
    }

    @Override
    @Transactional
    public List<Event> getActiveAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT a FROM Event a where a.status='SCHEDULE'", Event.class).getResultList();
    }

    @Override
    @Transactional
    public List<Event> getExpiredAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT a FROM Event a where a.status='ENDED' or a.archive=true", Event.class).getResultList();
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
    public Event updateEvent(Event event) {
        Session session = entityManager.unwrap(Session.class);
        Event oldEvent = session.get(Event.class,event.getId());
        oldEvent.setArchive(event.isArchive());
        oldEvent.setConfirmation(event.isConfirmation());
        oldEvent.setDate(event.getDate());
        oldEvent.setDiscription(event.getDiscription());
        oldEvent.setImage(event.getImage());
        oldEvent.setPrice(event.getPrice());
        oldEvent.setStatus(event.getStatus());
//        oldUser.setEvents(user.getEvents());
        oldEvent.setCreator(event.getCreator());
        oldEvent.setLatitude(event.getLatitude());
        oldEvent.setLongitude(event.getLongitude());
        oldEvent.setUsers(event.getUsers());
        session.update(oldEvent);
        return oldEvent;
    }

    @Override
    @Transactional
    public void deleteEvent(int id) {

    }
}
