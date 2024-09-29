package edu.andrewisnew.java.spring.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/persons/{id}")
public class SinglePersonController {
    private PersonService personService;

    /**
     * Будет выполняться перед каждым запросом.
     *
     * Finds the person managed by the methods in
     * this controller and adds it to the model
     * @param id
     * the id of the Person instance to retrieve
     * @return person
     */
    @ModelAttribute
    protected Person person(@PathVariable Long id) {
        Person person = new Person();
        Optional<Person> personOpt = personService.findById(id);
        return personOpt.orElse(person);
    }
    /**
     * Handles requests to show detail about one person.
     */
    @GetMapping
    public String show() {
        return "persons/show";
    }
    @GetMapping("/edit")
    public String edit() {
        return "persons/edit";
    }
    @PostMapping
    public String save(@Validated Person person,
                       BindingResult result, Locale locale) {

        return "persons/show";
    }

    record Person(){}

    class PersonService {

        public Optional<Person> findById(Long id) {
            return Optional.of(new Person());
        }
    }
}
