package org.project.cdrservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cdrservice.CdrGenerator;
import org.project.cdrservice.service.CdrService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppScheduler {
    private final CdrGenerator generator;
    private final CdrService cdrService;
    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        log.info("scheduler");
        generator.generateCdrFiles();
        cdrService.processCdrFiles();
    }
}
