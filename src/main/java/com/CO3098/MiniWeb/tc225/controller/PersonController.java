package com.CO3098.MiniWeb.tc225.controller;

import com.CO3098.MiniWeb.tc225.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.CO3098.MiniWeb.tc225.domain.Person;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping(value = {"/GE/person"})
//@RequestMapping("/")
public class PersonController{

	@Autowired
	PersonService personRepository;
	@Autowired
	ObjectMapper mapper;


	//Returns all objects of the database to the html table.
	@RequestMapping(value = {"/"})
	public ModelAndView listAll() {


		return new ModelAndView("personView", "persons", personRepository.findAllPersons());
	}


	//Transition to create a new person, happens when Add person is clicked.
	@RequestMapping(value = "/create")
	public ModelAndView newPerson(){
		return new ModelAndView("addPerson");
	}
	//When add happens this method is accessed, validates and adds the new person to database.
	@RequestMapping(value = {"/add{key}{name}{dob}{m}{f}{g}"}, method = RequestMethod.GET)
	public ObjectNode add(@RequestParam(value = "key") Integer key,
					   @RequestParam(value = "name") String name,
					   @RequestParam(value = "dob", required = false) Integer dob,
					   @RequestParam(value = "m", required = false) Integer motherId,
					   @RequestParam(value = "f", required = false) Integer fatherId,
					   @RequestParam(value = "g", required = false) String gender
	) {

		Person father ;
		Person mother ;

		Object o = personRepository.findById(key);
		ObjectNode objectNode = mapper.createObjectNode();


		if (motherId == null && fatherId == null){
			if (o == null) {
				Person person = new Person(key, name, motherId, fatherId, dob, gender);
				personRepository.save(person);
				objectNode.put("result", "true");
				return objectNode;
			}
		}
		if (motherId != null && fatherId != null &&  !motherId.equals(fatherId)){

			mother = personRepository.findById(motherId);
			father = personRepository.findById(fatherId);
			if (mother == null || father== null){
				//
				objectNode.put("result", "false");
				objectNode.put("message", "key " + key + "  already exists (or m/f id doesnt exist)");
				return objectNode;
			}else if(o == null){
				objectNode.put("result", "true");
				Person person = new Person(key, name, motherId, fatherId, dob, gender);
				personRepository.save(person);
				return objectNode;


			}
		}
		if (motherId == null && fatherId != null){
			objectNode.put("result", "true");
			Person person = new Person(key, name, motherId, fatherId, dob, gender);
			personRepository.save(person);
			return  objectNode;
		}
		if (motherId != null && fatherId == null){
			objectNode.put("result", "true");
			Person person = new Person(key, name, motherId, fatherId, dob, gender);
			personRepository.save(person);
			return  objectNode;
		}
		if (motherId.equals(fatherId)){
			objectNode.put("result", "false");
			objectNode.put("message", "mother and father keys are the same!");
			return objectNode;
		}


		 return objectNode;

	}

	//Adds multiple people to the database via JSON Requests
	@RequestMapping(value = "/addJSON", method = RequestMethod.POST)
	public @ResponseBody
	Object addJSON(
			@RequestBody Person person) {

		Object o = personRepository.findById(person.getKey());

		if (o != null) {
			return "{\"result\": \"false\", \"message\": \"person id already exists!\"}";
		} else if (person.getMotherKey() == null) {
			return "{\"result\": \"false\", \"message\": \"mother id doesnt exist!\"}";
		} else if (person.getFatherKey() == null) {
			return "{\"result\": \"false\", \"message\": \"father id doesnt exist!\"}";
		} else {
			add(person.getKey(), person.getName(),
					person.getDateOfBirth(), person.getMotherKey(),
					person.getFatherKey(), person.getGender());
			if (personRepository.findById(person.getKey()) != null) {
				return "{\"result\": \"true\"}";
			}else{
				return "{\"result\": \"something went wrong!\"}";
			}
		}

	}


	//Delete the person with the given id from the database
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody
	ObjectNode delete(@PathVariable Integer id) {
		ObjectNode objectNode = mapper.createObjectNode();

		if (personRepository.findById(id) != null) {
			personRepository.deleteById(id);
			objectNode.put("result", "true");

			return objectNode;
		} else {

			objectNode.put("result", "false");
			objectNode.put("message", "key " + id + " does not exist!");
			return objectNode;
		}

	}

	//Transition to edit an object.
	@RequestMapping(value = "/edit/{id}")
	public ModelAndView editPerson(@PathVariable Integer id){

		if (personRepository.findById(id) != null) {

			return new ModelAndView("edit", "person", personRepository.findById(id) );
		}else{
			return new ModelAndView("personView", "persons",personRepository.findAllPersons());
		}
	}
	//When edit happens this method is accessed
	@RequestMapping(value = {"/change{key}{name}{dob}{m}{f}{g}"},method = RequestMethod.GET)
	public ModelAndView change(@RequestParam(value = "key") Integer key,
							   @RequestParam(value = "name") String name,
							   @RequestParam(value = "dob", required = false) Integer dob,
							   @RequestParam(value = "m", required = false) Integer motherId,
							   @RequestParam(value = "f", required = false) Integer fatherId,
							   @RequestParam(value = "g", required = false) String gender){


		Person person = new Person(key, name, motherId, fatherId, dob, gender);
			personRepository.deleteById(key);
		if (personRepository.findById(key) == null) {
			personRepository.save(person);

		}

		return new ModelAndView("personView","persons",personRepository.findAllPersons());
	}

	//Returns all details of a specific person. Can be accessed at the table of persons.
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Object get(@PathVariable Integer id) {
		Person person = personRepository.findById(id);
		ObjectNode objectNode = mapper.createObjectNode();
		if (person == null) {

			objectNode.put("result", "false");
			objectNode.put("message", "key " + id + " does not exist!");

			return objectNode;
		} else {


			return prettyPrintPerson(person);
		}

	}

	//Returns the family tree of all the people in the database
	@RequestMapping(value="familyTree", method = RequestMethod.GET)
	public ModelAndView showTree(){

		TreeMap<Integer,Person> treeMap = new TreeMap<>();
		int counter = 1;
		for (Person p: personRepository.findAllPersons()
			 ) {
			if (p.getFatherKey() == null && p.getMotherKey() == null){
				treeMap.put(counter,p);
			}
			counter++;
		}

		List<Person> list = new ArrayList<>(treeMap.values());

	return new ModelAndView("tree", "persons", list );
	}



	//Returns all objects of database in a pretty format! Accessed at corresponnding button in UI
	@RequestMapping(value="/printJSON", method = RequestMethod.GET , produces = "application/json")
	public @ResponseBody Object prettyPrinnt() throws JsonProcessingException{


		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String  toRet = gson.toJson(personRepository.findAllPersons());
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(toRet);
		System.out.println(gson.toJson(je));
		return gson.toJson(je);
	}

	//Method to Pretty-print objects
	private Object prettyPrintPerson(Object o){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String  toRet = gson.toJson(o);

		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(toRet);
		return gson.toJson(je);
	}

	//returns PAGINATED objects
	@RequestMapping(value="/persons", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody  Object listPeople(Pageable pageable){



		return personRepository.listAllByPage(pageable);
	}
	//Generates the ancestors of given id
	@RequestMapping(value = "/ancestors/{id}", method = RequestMethod.GET , produces = "application/json")
	public @ResponseBody
	Object getAncestors(@PathVariable Integer id) {
		ObjectNode objectNode = mapper.createObjectNode();
		//Case where given id does not exist
		if (personRepository.findById(id) == null){
			objectNode.put("result", "false");
			objectNode.put("message", "key " + id + " does not exist!");
			return objectNode;
		}else {

			if (personRepository.findById(id).getFatherKey() == null && personRepository.findById(id).getMotherKey() == null){
				return objectNode.put("key", id.toString());
			}else {
				//Setting up output, and then sending id to a method which
				// will recursively concatenate the parents on this object
				objectNode = objectNode.put("key", id.toString()).putPOJO("parents", eachAncestor(id));
			}
		}


		return objectNode;
	}

	private ObjectNode eachAncestor(Integer key) {
		Person person = personRepository.findById(key);
		ObjectNode toRet = mapper.createObjectNode();
		ObjectNode motherSide = mapper.createObjectNode();
		ObjectNode fatherSide = mapper.createObjectNode();

		//recursively finding all ancestors of the given persons mother
		if (person.getMotherKey() != null) {
				motherSide.put("key",person.getMotherKey());
				Person nextMother = personRepository.findById(person.getMotherKey());
				if ( nextMother.getMotherKey() != null) {
					motherSide.putPOJO("parents", eachAncestor(person.getMotherKey()));
				}
		}
		//recursively finding all ancestors of the given persons father
		if (person.getFatherKey() != null) {

			fatherSide.put("key", person.getFatherKey());
			Person nextFather = personRepository.findById(person.getFatherKey());
			if(nextFather.getFatherKey() != null){
				fatherSide.putPOJO("parents", eachAncestor(person.getFatherKey()));
			}
		}

		//At this point the recursion has given the desired output, which is added on m and f sides
		//otherwise the output would not be correct!
		toRet.putPOJO("m", motherSide).putPOJO("f", fatherSide);
		return toRet;
	}

	//Generates descendants of given id
	@RequestMapping(value = "/descendants/{id}", method = RequestMethod.GET)
	public @ResponseBody Object getDescendants(@PathVariable Integer id){
		ObjectNode objectNode = mapper.createObjectNode();
		Person person = personRepository.findById(id);

		if (person == null){
			objectNode.put("result", "false");
			objectNode.put("message", "key " + id + " does not exist!");
			return objectNode;
		}



		return eachDescendant(person);
	}
	private ObjectNode eachDescendant(Person person){
		ObjectNode objectNode = mapper.createObjectNode();
		List<Person> fatherList = personRepository.findByFatherKey(person.getKey());
		List<Person> motherList = personRepository.findByMotherKey(person.getKey());
		ArrayNode arrayNode ;
		objectNode.put("key",person.getKey().toString());

		if (fatherList.isEmpty() && motherList.isEmpty()){
			return objectNode;
		}else{
			arrayNode = objectNode.putArray("children");
			if (fatherList.isEmpty()){
				for (Person p : motherList){
					arrayNode.addPOJO(eachDescendant(p));
				}
			}else{
				for (Person p: fatherList){
					arrayNode.addPOJO(eachDescendant(p));
				}
			}
		}
		return objectNode;
	}

	//To communicate with Ajax to check availability of keys
	@RequestMapping(value={"/keyAvailability"} , method = RequestMethod.GET)
	public void availableKey (HttpServletRequest req, HttpServletResponse response) throws IOException {
//		Person person =personRepository.findById(key);
		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html");

		try {
			Integer keyToCheck = Integer.parseInt(req.getParameter("key"));
			Person personToCheck = personRepository.findById(keyToCheck);
			if (personToCheck == null) {
				printWriter.println("{\"thekey\":false}"); // true
			} else {
				printWriter.println("{\"thekey\":true}"); // 0 means false
			}
		} catch (Exception e) {
			printWriter.println("{\"result\": \"something went wrong!\"}");
			e.printStackTrace();
		}

	}

}
