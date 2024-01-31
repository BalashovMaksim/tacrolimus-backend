package com.tacrolimus.backend.repository;

import com.tacrolimus.backend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    @Query("SELECT p FROM Person p " +
            "WHERE MONTH(p.birthday) = :startMonth " +
            "AND DAY(p.birthday) >= :startDay " +
            "OR MONTH(p.birthday) = :endMonth " +
            "AND DAY(p.birthday) <= :endDay " +
            "ORDER BY MONTH(p.birthday), DAY(p.birthday)")
    List<Person> findAllByBirthdayBetween(@Param("startMonth") int startMonth, @Param("startDay") int startDay, @Param("endMonth") int endMonth, @Param("endDay") int endDay);
}
