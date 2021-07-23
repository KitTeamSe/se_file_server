package com.se.fileserver.v1.user.adapter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginTest")
public class LoginTestController {

  @GetMapping("/success")
  public String success(){
    return "success";
  }
}
