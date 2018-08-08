package com.x.controller;

import com.x.api.UserService;
import com.x.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findUserById")
    @ResponseBody
    public Users findUserById(Integer id){
        Users users = this.userService.findById(id);
        return users;
    }


}
