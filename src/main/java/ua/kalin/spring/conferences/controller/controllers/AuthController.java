package ua.kalin.spring.conferences.controller.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kalin.spring.conferences.controller.dto.PersonRegistrationDTO;
import ua.kalin.spring.conferences.model.models.Person;
import ua.kalin.spring.conferences.model.services.RegistrationService;
import ua.kalin.spring.conferences.utils.PersonRegistrationDTOValidator;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonRegistrationDTOValidator personRegistrationDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(RegistrationService registrationService,
                          PersonRegistrationDTOValidator personRegistrationDTOValidator,
                          ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.personRegistrationDTOValidator = personRegistrationDTOValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") PersonRegistrationDTO person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid PersonRegistrationDTO person,
                               BindingResult bindingResult) {
        personRegistrationDTOValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        registrationService.register(modelMapper.map(person, Person.class));
        log.info(String.format("%s registered", person.getEmail()));
        return "redirect:/auth/login";
    }
}