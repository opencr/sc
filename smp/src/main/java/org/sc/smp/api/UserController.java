package org.sc.smp.api;

import org.sc.smp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/selectList")
    public Object selectList(){
        return userService.selectList();
    }

    @GetMapping("/findList")
    public Object findList(){
        return userService.findList();
    }
}
