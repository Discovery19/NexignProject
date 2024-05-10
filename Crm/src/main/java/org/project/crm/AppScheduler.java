package org.project.crm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.crm.controllers.CrmController;
import org.project.crm.dto.ChangeTariffMsisdnBody;
import org.project.crm.dto.PayMsisdnBody;
import org.project.crm.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Класс AppScheduler представляет собой компонент Spring, отвечающий за выполнение периодических задач в приложении.
 * Он содержит методы для обновления данных пользователей и отправки соответствующих запросов к CRM-сервису.
 * Класс использует аннотации Spring для управления периодичностью выполнения задач и внедрения зависимостей.
 * Для каждой периодической задачи определен метод, который выбирает случайных пользователей и отправляет запросы
 * на изменение тарифа или оплату услуг в CRM-сервис.
 * Используется генератор случайных чисел для выбора пользователей и определения параметров запросов.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class AppScheduler {
    private final UserService userService;
    private final CrmController crmController;
    private final static Random RANDOM = new Random();

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        var users = userService.getAllUsersForScheduler();
        int n = RANDOM.nextInt(1, 5);
        for (int i = 0; i < n; i++) {
            crmController.payMsisdnPatch(
                    users.get(RANDOM.nextInt(users.size())).getId().toString(),
                    new PayMsisdnBody(RANDOM.nextInt(0, 50)));
        }
        n = RANDOM.nextInt(1, 3);
        for (int i = 0; i < n; i++) {
            var u = users.get(RANDOM.nextInt(users.size()));
            var tar = u.getTariffId() == 11L ? 12L : 11L;
            crmController.changeTariffMsisdnPatch(u.getId().toString(), new ChangeTariffMsisdnBody(u.getId().toString(), tar));
        }
        log.info("update scheduler");
    }
}
