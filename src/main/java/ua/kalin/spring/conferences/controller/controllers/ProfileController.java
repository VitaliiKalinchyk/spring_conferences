package ua.kalin.spring.conferences.controller.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kalin.spring.conferences.controller.dto.PasswordUpdateDTO;
import ua.kalin.spring.conferences.controller.dto.PersonProfileDTO;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.services.PersonService;
import ua.kalin.spring.conferences.security.PersonDetails;
import ua.kalin.spring.conferences.utils.PasswordUpdateDTOValidator;
import ua.kalin.spring.conferences.utils.PersonProfileDTOValidator;

@Controller
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
    private final PersonService personService;
    private final PersonProfileDTOValidator personValidator;
    private final PasswordUpdateDTOValidator passwordValidator;
    private final ModelMapper modelMapper;

    public ProfileController(PersonService personService, PersonProfileDTOValidator personValidator,
                             PasswordUpdateDTOValidator passwordValidator, ModelMapper modelMapper) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.passwordValidator = passwordValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String profile(Model model, Authentication authentication) {
        Person person = ((PersonDetails) authentication.getPrincipal()).person();
        model.addAttribute("person", modelMapper.map(person, PersonProfileDTO.class));
        return "/profile/index";
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Authentication authentication) {
        Person person = ((PersonDetails) authentication.getPrincipal()).person();
        model.addAttribute("person", modelMapper.map(person, PersonProfileDTO.class));
        return "/profile/edit";
    }

    @GetMapping("/password")
    public String editPassword(Model model, Authentication authentication) {
        Person person = ((PersonDetails) authentication.getPrincipal()).person();
        model.addAttribute("passwordUpdateDTO", modelMapper.map(person, PasswordUpdateDTO.class));
        return "/profile/password";
    }

    @PatchMapping("/edit")
    public String update(@ModelAttribute("person") @Valid PersonProfileDTO personDTO,
                         BindingResult bindingResult, Authentication authentication) {
        personValidator.validate(personDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/profile/edit";
        }
        Person person = modelMapper.map(personDTO, Person.class);
        personService.update(person);
        updateAuthentication(person, authentication);
        log.info(String.format("%s successfully updated his profile", person.getEmail()));
        return "redirect:/profile/edit";
    }

    @PatchMapping("/password")
    public String updatePassword(@ModelAttribute("passwordUpdateDTO") @Valid PasswordUpdateDTO passwordUpdateDTO,
                                 BindingResult bindingResult) {
        passwordValidator.validate(passwordUpdateDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/profile/password";
        }
        personService.updatePassword(passwordUpdateDTO.getId(), passwordUpdateDTO.getNewPassword());
        return "redirect:/profile/password";
    }

    private void updateAuthentication(Person person, Authentication authentication) {
        Person updatedPerson = ((PersonDetails) authentication.getPrincipal()).person();
        updatedPerson.setEmail(person.getEmail());
        updatedPerson.setName(person.getName());
        updatedPerson.setSurname(person.getSurname());
    }
}