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

/**
 * CrmController является контроллером Spring, который обрабатывает запросы, связанные с CRM-сервисом.
 * Он реализует интерфейс CrmApi, определяя методы для сохранения информации об абоненте, оплаты услуг,
 * изменения тарифа и проверки информации о пользователе.
 * Контроллер использует сервис UserService для выполнения операций с пользователями.
 * Необходима базовая аутентификация для доступа к контроллеру.
 */


@Controller
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
public class CrmController implements CrmApi {
    private final UserService userService;

    @Override
    public ResponseEntity<?> savePost(InfoAbonent body) {
        return ResponseEntity.ok(userService.createUser(body));
    }

    @Override
    public ResponseEntity<?> payMsisdnPatch(String msisdn, PayMsisdnBody body) {
        return ResponseEntity.ok(userService.pay(Long.valueOf(msisdn), body.money()));
    }

    @Override
    public ResponseEntity<?> changeTariffMsisdnPatch(String msisdn, ChangeTariffMsisdnBody body) {
        return ResponseEntity.ok(userService.changeTariff(Long.valueOf(msisdn), body.tariffId()));
    }

    @Override
    public ResponseEntity<?> checkUser(String msisdn) {
        return ResponseEntity.ok(userService.getUser(Long.valueOf(msisdn)));
    }
}
