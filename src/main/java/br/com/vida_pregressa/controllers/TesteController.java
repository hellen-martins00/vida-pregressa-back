package br.com.vida_pregressa.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

    @GetMapping(path = {"/vp"})
    public String ola() {
        return  "Olá Vida Pregressa!";
    }

}
