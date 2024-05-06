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

@Slf4j
@Component
@RequiredArgsConstructor
public class AppScheduler {
    private final UserService userService;
    private final CrmController crmController;
    Random random = new Random();

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
//        var users = userService.getAllUsersForScheduler();
//        int n = random.nextInt(1, 5);
//        for (int i = 0; i < n; i++) {
//            crmController.payMsisdnPatch(
//                    users.get(random.nextInt(users.size())).getId().toString(),
//                    new PayMsisdnBody(random.nextInt(0, 50)));
//        }
//        n = random.nextInt(1, 3);
//        for (int i = 0; i < n; i++) {
//            var u = users.get(random.nextInt(users.size()));
//            var tar = u.getTariffId() == 11L ? 12L : 11L;
//            crmController.changeTariffMsisdnPatch(u.getId().toString(), new ChangeTariffMsisdnBody(u.getId().toString(), tar));
//        }

        log.info("update scheduler");
    }
}
