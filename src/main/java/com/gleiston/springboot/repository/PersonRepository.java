package com.gleiston.springboot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gleiston.springboot.model.Person;

@Repository
@Transactional
public interface PersonRepository  extends JpaRepository<Person, Integer>{
	public Person findById(Long id);
	
	@Query("SELECT p FROM Person p WHERE p.name LIKE %?1%")
	public List<Person> findByName(String name);
}
