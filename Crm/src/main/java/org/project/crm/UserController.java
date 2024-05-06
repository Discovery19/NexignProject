package org.project.crm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/welcome")
    public String welcome(){
        return "This is unprotected page";
    }

    @GetMapping("/users")
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public String pageForUser(){
        return "This is page for only users";
    }


    @GetMapping("/admins")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String pageForAdmins(){
        return "This is page for only admins";
    }


    @GetMapping("/all")
    public String pageForAll(){
        return "This is page for all employees";
    }
}
