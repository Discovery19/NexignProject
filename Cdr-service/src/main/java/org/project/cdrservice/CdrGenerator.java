package org.project.cdrservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.cdrservice.config.ApplicationConfig;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CdrGenerator {

    private static final Random random = new Random();
    private static final int NUM_RECORDS_IN_FILE = 10;
    private final ApplicationConfig.Generator generator;
    private static final List<Long> PHONE_NUMBERS_LIST = List.of(
            79787478615L, 79259876543L,
            79001234567L, 79009876543L,
            79161234500L, 79259876500L,
            79161200567L, 79259200543L,
            79201234567L, 79209876543L

    );

    //TEST METHOD TO USE IN SCHEDULER
    public void generateTestCdr(int month, int year) {
        String fileName = String.format("cdr_test_%02d_%d.txt", month, year);
        try (PrintWriter writer = new PrintWriter(new FileWriter("cdr_files/" + fileName))) {
            String callerNumber = String.valueOf(79787478614L);
            String calleeNumber = String.valueOf(78007178614L);
            long startTime = calculateMinTime(month, year);
            long endTime = startTime + 5 + random.nextInt(60);
            String callType = random.nextBoolean() ? "01" : "02";
            String cdrRecord = String.format("%s,%s,%s,%d,%d", callType, callerNumber, calleeNumber, startTime, endTime);
            writer.println(cdrRecord);
            System.out.println("CDR файл успешно сгенерирован: " + fileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public void generateCdrFiles() {
        int numFiles = generator.numberFiles();
        int month = generator.lastMonth();
        int year = generator.year();
        try (ExecutorService executor = Executors.newFixedThreadPool(numFiles)) {

            for (int i = 1; i <= numFiles; i++) {
                String fileName = String.format("cdr_%02d_%d_%d.txt", month, year, i);
                executor.submit(() -> generateCdrFile(fileName, month, year));
            }
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        } catch (InterruptedException e) {
            log.error("Error while generating CDR files: " + e.getMessage());
        }
    }

    private void generateCdrFile(String fileName, int month, int year) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("cdr_files/" + fileName))) {

            long startTime = calculateMinTime(month, year);
            for (int i = 0; i < NUM_RECORDS_IN_FILE; i++) {
                String callerNumber = generatePhoneNumber();
                String calleeNumber = generatePhoneNumber();

                long endTime = startTime + 5 + random.nextInt(160);

                String callType = random.nextBoolean() ? "01" : "02";

                String cdrRecord = String.format("%s,%s,%s,%d,%d", callType, callerNumber, calleeNumber, startTime, endTime);
                writer.println(cdrRecord);

                startTime = endTime + 1 + random.nextInt(80);
            }

            log.info("CDR файл успешно сгенерирован: " + fileName);
        } catch (IOException e) {
            log.error("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private long calculateMinTime(int month, int year) {
        long startTime = calculateStartTimeOfYear(year);
        for (int m = 1; m < month; m++) {
            int daysInMonth = switch (m) {
                case 2 -> year % 4 == 0 && (year % 100 != 0 || year % 400 == 0) ? 29 : 28;
                case 4, 6, 9, 11 -> 30;
                default -> 31;
            };
            startTime += daysInMonth * 24 * 60 * 60;
        }
        return startTime;
    }

    private long calculateStartTimeOfYear(int year) {
        long startTime = 0;
        for (int y = 1970; y < year; y++) {
            startTime += (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) ? 366 : 365;
        }
        return startTime * 24 * 60 * 60;
    }

    private String generatePhoneNumber() {
        return PHONE_NUMBERS_LIST.get(random.nextInt(PHONE_NUMBERS_LIST.size())).toString();
    }
}
