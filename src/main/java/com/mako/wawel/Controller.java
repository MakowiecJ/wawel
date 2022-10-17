package com.mako.wawel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class Controller {
    @GetMapping("")
    public String getContent() {
        return "Testowe RestApi kina wawel";
    }
}
