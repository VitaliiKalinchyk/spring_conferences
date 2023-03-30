package ua.kalin.spring.conferences.controller.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kalin.spring.conferences.controller.dto.PersonDTO;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.models.Role;
import ua.kalin.spring.conferences.model.services.PersonService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/person")
@Slf4j
public class PersonController {
    private final PersonService personService;
    private final ModelMapper modelMapper;

    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping({"/{pageNo}", ""})
    public String index(@PathVariable(value = "pageNo", required = false) Integer pageNo,
                        @RequestParam(required = false, defaultValue = "") String searchBy,
                        @RequestParam(required = false, defaultValue = "id") String sortField,
                        @RequestParam(required = false, defaultValue = "asc") String sortOrder,
                        Model model) {

        pageNo = Objects.requireNonNullElse(pageNo, 1);
        Page<Person> page = personService.getAll(searchBy, sortField, sortOrder, pageNo - 1);

        addAttributes(pageNo, searchBy, sortField, sortOrder, model, page);

        return "/person/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", modelMapper.map(personService.getById(id), PersonDTO.class));
        model.addAttribute("roles", Arrays.asList(Role.values()));
        return "/person/show";
    }

    @PatchMapping("/set-role/{id}")
    public String changeRole(@PathVariable("id") int id,
                             @RequestParam("email") String email,
                             @RequestParam("role") String role) {
        personService.setRole(id, role);
        log.info(String.format("%s changed role to %s", email, role));
        return "redirect:/person/show/" + id;
    }

    @PatchMapping("/block/{id}")
    public String blockPerson(@PathVariable("id") int id,
                              @RequestParam("blocked") boolean blocked,
                              @RequestParam("url") String url,
                              @RequestParam("email") String email) {
        if (blocked) {
            log.info(String.format("%s is unblocked", email));
            personService.unblock(id);
        } else {
            log.info(String.format("%s is blocked", email));
            personService.block(id);
        }
        return "redirect:" + url;
    }

    private void addAttributes(Integer pageNo, String searchBy, String sortField, String sortOrder,
                               Model model, Page<Person> page) {
        List<PersonDTO> persons = page.stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .toList();
        model.addAttribute("persons", persons);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("fromPage", Math.max(1, pageNo - 2));
        model.addAttribute("toPage", Math.min(page.getTotalPages(), pageNo + 2));
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("searchBy", searchBy);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("reverseSortOrder", sortOrder.equals("asc") ? "desc" : "asc");
    }
}