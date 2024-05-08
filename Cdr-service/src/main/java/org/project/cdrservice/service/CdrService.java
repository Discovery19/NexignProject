package org.project.cdrservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cdrservice.kafka.CdrRequest;
import org.project.cdrservice.kafka.NotificationService;

import org.project.cdrservice.model.Cdr;
import org.project.cdrservice.repository.CdrRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
@RequiredArgsConstructor
public class CdrService {
    private final CdrRepository cdrRepository;
    private final NotificationService notificationService;

    public void processCdrFiles() {

        log.info("processing files");
        String cdrFolderPath = "cdr_files";
        try {
            Files.walk(Paths.get(cdrFolderPath))
                    .filter(Files::isRegularFile)
                    .forEach(this::processCdrFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCdrFile(Path filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Cdr cdr = getCdr(fields);
                cdr = cdrRepository.saveAndFlush(cdr);
                log.info("cdr send " + cdr);
                CdrRequest cdrRequest = new CdrRequest(cdr.getId(), cdr.getType(), cdr.getContactNumber(), cdr.getAnotherNumber(), cdr.getStartCall(), cdr.getEndCall());
                cdrRepository.saveAndFlush(cdr);
                notificationService.sendNotification(cdrRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Cdr getCdr(String[] fields) {
        short type = Short.parseShort(fields[0]);
        Long contactNumber = Long.parseLong(fields[1]);
        Long anotherNumber = Long.parseLong(fields[2]);
        Long callStart = Long.parseLong(fields[3]);
        Long callEnd = Long.parseLong(fields[4]);

        Cdr cdr = new Cdr();
        cdr.setType(type);
        cdr.setContactNumber(contactNumber);
        cdr.setAnotherNumber(anotherNumber);
        cdr.setStartCall(callStart);
        cdr.setEndCall(callEnd);
        return cdr;
    }
}
