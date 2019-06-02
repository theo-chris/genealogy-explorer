package com.CO3098.MiniWeb.tc225.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.CO3098.MiniWeb.tc225.domain.Person;

import java.util.List;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person,Integer> {


    List<Person> findAllByMotherKey(Integer key);
    List<Person> findAllByFatherKey(Integer key);
}
