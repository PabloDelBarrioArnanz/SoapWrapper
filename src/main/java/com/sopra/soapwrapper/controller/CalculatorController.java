package com.sopra.soapwrapper.controller;

import com.sopra.soapwrapper.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

  @Autowired private CalculatorService calculatorService;

  @GetMapping("/add")
  public ResponseEntity<Integer> add(@RequestParam int numberA, @RequestParam int numberB) {
    return new ResponseEntity<>(
      calculatorService.add(numberA, numberB), HttpStatus.OK);
  }

  @GetMapping("/subtract")
  public ResponseEntity<Integer> subtract(@RequestParam int numberA, @RequestParam int numberB) {
    return new ResponseEntity<>(
      calculatorService.subtract(numberA, numberB), HttpStatus.OK);
  }

  @GetMapping("/multiply")
  public ResponseEntity<Integer> multiply(@RequestParam int numberA, @RequestParam int numberB) {
    return new ResponseEntity<>(
      calculatorService.multiply(numberA, numberB), HttpStatus.OK);
  }

  @GetMapping("/divide")
  public ResponseEntity<Integer> divide(@RequestParam int numberA, @RequestParam int numberB) {
    return new ResponseEntity<>(
      calculatorService.divide(numberA, numberB), HttpStatus.OK);
  }
}
