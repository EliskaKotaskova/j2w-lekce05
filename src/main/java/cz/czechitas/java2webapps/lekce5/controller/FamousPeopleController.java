package cz.czechitas.java2webapps.lekce5.controller;

import cz.czechitas.java2webapps.lekce5.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class FamousPeopleController {
  private final List<Person> people;

  public FamousPeopleController() {
    people = new ArrayList<>();
    people.add(new Person("Bill", "Gates", LocalDate.of(1955, 10,3)));
    people.add(new Person("Angela", "Merkelová", LocalDate.of(1989, 2, 17)));
    people.add(new Person("Greta", "Blankytná", LocalDate.of(1998, 6, 29)));
  }


  @GetMapping("/")
  public ModelAndView list() {
    ModelAndView result = new ModelAndView("index");
    result.addObject("people", people);
    return result;
  }

  @GetMapping(value = "/", params = "query")
  public ModelAndView query(String query) {
    List<Person> filteredPeople = people.stream()
            .filter(person -> person.getGivenName().contains(query) || person.getLastName().contains(query))
            .collect(Collectors.toList());
    ModelAndView result = new ModelAndView("index");
    result.addObject("people", filteredPeople);
    result.addObject("query", query);
    return result;
  }

  @PostMapping(value = "/", params = {"givenName", "lastName", "birthDate"})
  public String append(Person person) {
    people.add(person);
    return "redirect:/";
  }

  @PostMapping(value = "/", params = {"id"})
  public String delete(int id) {
    people.remove(id);
    return "redirect:/";
  }

}
