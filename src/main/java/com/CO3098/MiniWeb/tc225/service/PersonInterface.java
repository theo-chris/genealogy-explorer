package com.CO3098.MiniWeb.tc225.service;

import com.CO3098.MiniWeb.tc225.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonInterface {
    Page<Person> listAllByPage(Pageable pageable);
}
