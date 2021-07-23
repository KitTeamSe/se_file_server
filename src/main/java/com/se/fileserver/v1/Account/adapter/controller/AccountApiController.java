package com.se.fileserver.v1.Account.adapter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginTest")
public class AccountApiController {

  @GetMapping("/success")
  public String success(){
    return "success";
  }

}
