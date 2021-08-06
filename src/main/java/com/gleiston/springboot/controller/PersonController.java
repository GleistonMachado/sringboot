package com.gleiston.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gleiston.springboot.model.Person;
import com.gleiston.springboot.repository.PersonRepository;

@Controller
@RequestMapping("/people")
public class PersonController {
	
	@Autowired
	public PersonRepository personRepository;
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("person", new Person());
		return "people/create";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors() || (person.getName().isEmpty() || person.getEmail().isEmpty()) ) {
			return "people/create";
		}
		personRepository.save(person);
		return "redirect:/people/list";
	}
	
	@GetMapping("/list")
	public String findAll(Model model) {
		List<Person> list = personRepository.findAll();
		model.addAttribute("list", list);
		return "/people/index";
	}
	
	@GetMapping("edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		Person person = personRepository.findById(id);
		model.addAttribute("person", person);
		return "people/edit";
		
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("person") Person person, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors() || (person.getName().isEmpty() || person.getEmail().isEmpty())) {
			return "people/edit";
		}
		
		Person personExist = personRepository.findById(id);
		
		if(personExist != null) {
			personExist.setId(person.getId());
			personExist.setName(person.getName());
			personExist.setEmail(person.getEmail());
				
			personRepository.save(personExist);
			
			return "redirect:/people/list";
			
		} else {
	
			return "people/edit";
		}
		
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		
		Person person = personRepository.findById(id);
		
		if(person != null) {
			personRepository.delete(person);
		}
		
		return "redirect:/people/list";
	}
	
	@PostMapping("/search")
	public String findByName(@RequestParam("name") String name, Model model) {
		
		if(!name.isEmpty()) {
			
			List<Person> person = personRepository.findByName(name);
			
			if(!person.isEmpty()) {
				model.addAttribute("person", person);
				
			} else {
				model.addAttribute("msg", "Usuário não existe!");
			}
		}
		
		return "/home/index";
		
	}
	
	
	
	
	
	
	
	


}
