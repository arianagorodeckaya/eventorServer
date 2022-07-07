package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Integer> {
    public Page<Event> findByConfirmation(Boolean confirmation, Pageable pageable);
    public List<Event> findByEndDateIsGreaterThan(Date date);
    public List<Event> findByEndDateIsGreaterThanAndConfirmation(Date date, Boolean confirmation);
    public List<Event> findByEndDateIsLessThan(Date date);
    public List<Event> findByEndDateIsGreaterThanAndCreator(Date date, User user);
    public List<Event> findByEndDateIsLessThanAndCreator(Date date, User user);
    public Long countEventsByPriceIsLessThanEqual(float price);
    public Long countEventsByPriceIsGreaterThan(float price);
    public Long countEventsByStartDateIsGreaterThan(Date date);
    public Long countEventsByStartDateIsLessThanAndEndDateIsGreaterThan(Date start, Date end);
    public Long countEventsByEndDateIsLessThan(Date date);

}
