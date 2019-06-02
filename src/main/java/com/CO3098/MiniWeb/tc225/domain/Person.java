package com.CO3098.MiniWeb.tc225.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Entity
public class Person {

	@Id
	@Column(name="id",unique = true,nullable = false)
	Integer id ;

	@Column(name="name",nullable = false)
	String name = null;

	@Column(name="motherId")
	Integer motherKey = null;

	@Column(name="fatherId")
	Integer fatherKey= null;

	@Column(name="gender")
	String gender= null;

	@Column(name="dateOfBirth")
	Integer dateOfBirth= null; //19921210->December 10th 1992

	public Person() {
		super();

	}







	public Person(Integer key, String name, Integer motherKey, Integer fatherKey, Integer dateOfBirth, String gender) {
		super();
		this.id = key;
		this.name = name;
		this.motherKey = motherKey;
		this.fatherKey = fatherKey;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
	}

	@JsonProperty("key")
	public Integer getKey() {
		return id;
	}
	public void setKey(Integer key) {
		this.id = key;
	}
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("m")
	public Integer getMotherKey() {
		return motherKey;
	}
	public void setMotherKey(Integer motherKey) {
		this.motherKey = motherKey;
	}
	@JsonProperty("f")
	public Integer getFatherKey() {
		return fatherKey;
	}
	public void setFatherKey(Integer fatherKey) {
		this.fatherKey = fatherKey;
	}
	
	@JsonProperty("dob")
	public Integer getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Integer dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@JsonProperty("g")
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	



	

	

}