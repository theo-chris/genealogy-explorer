package com.CO3098.MiniWeb.tc225.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.CO3098.MiniWeb.tc225.domain.Person;
import com.CO3098.MiniWeb.tc225.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService implements PersonInterface{
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	ObjectMapper mapper;


	public Iterable<Person> findAllPersons() {
		return personRepository.findAll();
	}
    public Page<Person> listAllByPage(Pageable page){return personRepository.findAll(page);}
	public Person findById(Integer id) {
		return personRepository.findOne(id);
	}
	public void deleteById(Integer id) {
		 personRepository.delete(id);
	}
	public void save(Person person) { personRepository.save(person);
	}

	public List<Person> findByFatherKey(Integer key){
		List<Person> fatherList = new ArrayList<>();
		fatherList.addAll(personRepository.findAllByFatherKey(key));

		return fatherList;

	}
	public List<Person> findByMotherKey(Integer key){
		List<Person> motherList = new ArrayList<>();
		motherList.addAll(personRepository.findAllByMotherKey(key));

		return motherList;
	}


}
