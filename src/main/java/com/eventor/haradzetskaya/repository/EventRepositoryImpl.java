package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.Event;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    public Event findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Event.class,id);
    }

    @Override
    @Transactional
    public List<Event> findAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("SELECT a FROM Event a", Event.class).getResultList();
    }

    @Override
    @Transactional
    public List<Event> findActiveAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createNativeQuery("Select * from event where event.end_date_time/1000 >= CAST(strftime('%s',datetime('now')) as integer)", Event.class).getResultList();
    }

    @Override
    @Transactional
    public List<Event> findExpiredAll() {
        Session session = entityManager.unwrap(Session.class);
        return session.createNativeQuery("Select * from event where event.end_date_time/1000 < CAST(strftime('%s',datetime('now')) as integer)",Event.class).getResultList();
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
    public void deleteEvent(int id) {
        Session session = entityManager.unwrap(Session.class);
        Event event = session.get(Event.class,id);
        session.delete(event);
    }


    @Override
    public Page<Event> findConfirmedAll(Pageable pageable) {
            Query query = entityManager.createQuery("select a from Event a where a.confirmation=true");
            int pageNumber = pageable.getPageNumber();
            int pageSize = pageable.getPageSize();
            query.setFirstResult((pageNumber) * pageSize);
            query.setMaxResults(pageSize);
            List<Event> events = query.getResultList();

            Query queryCount = entityManager.createQuery("Select count(a.id) From Event a where a.confirmation=true");
            long count = (long) queryCount.getSingleResult();

            return new PageImpl<Event>(events, pageable, count);
    }

    @Override
    public Page<Event> findUnconfirmedAll(Pageable pageable) {
        Query query = entityManager.createQuery("select a from Event a where a.confirmation=false");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<Event> events = query.getResultList();

        Query queryCount = entityManager.createQuery("Select count(a.id) From Event a where a.confirmation=false ");
        long count = (long) queryCount.getSingleResult();

        return new PageImpl<Event>(events, pageable, count);
    }

    @Override
    public Page<Event> findNullConfirmedEvents(Pageable pageable){
        Query query = entityManager.createQuery("select a from Event a where a.confirmation is NULL ");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<Event> events = query.getResultList();

        Query queryCount = entityManager.createQuery("Select count(a.id) From Event a where a.confirmation is NULL ");
        long count = (long) queryCount.getSingleResult();

        return new PageImpl<Event>(events, pageable, count);
    }
}
