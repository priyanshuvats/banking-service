package com.setu.bank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping
	public String getStatus() {
		return "Application is up and running";
	}
    
}
