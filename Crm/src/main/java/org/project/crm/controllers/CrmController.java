package org.project.crm.controllers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.project.crm.service.UserService;
import org.project.crm.dto.ChangeTariffMsisdnBody;
import org.project.crm.dto.InfoAbonent;
import org.project.crm.dto.PayMsisdnBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@OpenAPIDefinition(info = @Info(title = "Foos API", version = "v1"))
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
//@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class CrmController implements CrmApi{
    private final UserService userService;
    //manag
    @Override
    public ResponseEntity<?> savePost(InfoAbonent body) {
        userService.createUser(body);
        return ResponseEntity.ok("save post");
    }
    //user
    @Override
    public ResponseEntity<?> payMsisdnPatch(String msisdn, PayMsisdnBody body) {
        userService.pay(Long.valueOf(msisdn), body.money());
        return ResponseEntity.ok("pay");
    }

    //manag
    @Override
    public ResponseEntity<?> changeTariffMsisdnPatch(String msisdn, ChangeTariffMsisdnBody body) {
        userService.changeTariff(Long.valueOf(msisdn), body.tariffId());
        return ResponseEntity.ok("changeTariff");
    }
    @Override
    public ResponseEntity<?> checkUser(String msisdn) {
        return ResponseEntity.ok(userService.getUser(Long.valueOf(msisdn)));
    }
}
