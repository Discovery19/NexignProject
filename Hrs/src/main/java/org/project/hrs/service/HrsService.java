package org.project.hrs.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.hrs.requests.BrtRequest;
import org.project.hrs.requests.HrsRequest;
import org.project.hrs.model.Tariff;
import org.project.hrs.model.TariffComponent;
import org.project.hrs.model.TariffStatus;
import org.project.hrs.model.TypeComponent;
import org.project.hrs.repositories.TariffComponentRepository;
import org.project.hrs.repositories.TariffRepository;
import org.project.hrs.repositories.TariffStatusRepository;
import org.project.hrs.repositories.TypeComponentRepository;
import org.project.hrs.requests.MonthRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class HrsService {
    private static Integer month= null;
    private final TariffRepository tariffRepository;
    private final TypeComponentRepository typeComponentRepository;
    private final TariffComponentRepository tariffComponentRepository;
    private final TariffStatusRepository tariffStatusRepository;

    @PostConstruct
    private void createTariff() {
        Tariff tariff = new Tariff();
        tariff.setId(11L);
        tariff.setName("tariff");
        tariff.setBasePrice(10);
        tariffRepository.saveAndFlush(tariff);

        TypeComponent typeComponent = new TypeComponent();
        typeComponent.setName("type component");
        typeComponentRepository.saveAndFlush(typeComponent);

        TariffComponent tariffComponent = new TariffComponent();
        tariffComponent.setTariff(tariff);
        tariffComponent.setQuantity(6);
        tariffComponent.setPrice(1.5);
        tariffComponent.setTypeComponent(typeComponent);
        tariffComponentRepository.saveAndFlush(tariffComponent);
//        tariff.setTariffComponents();
    }
    public List<MonthRequest> monthCheck(BrtRequest request){

        Date date = Date.from(Instant.ofEpochSecond(request.start()));
        if (month == null || month!=date.getMonth()){
            month = date.getMonth();
            var tariffs = tariffRepository.findAllWithNonNullBasePrice();
            List<MonthRequest> result = new ArrayList<>();
            for (Tariff t: tariffs){
                result.add(new MonthRequest(t.getId(), t.getBasePrice()));
            }
        }
        return null;
    }
    public HrsRequest tariffCalculation(BrtRequest request) {
        log.info("tariff calculation");
        var tariff = tariffRepository.findById(request.tariffId());
        tariff.ifPresent(value -> log.info(value.getName()));
        Optional<List<TariffStatus>> tariffStatus;
        tariffStatus = tariffStatusRepository.findAllByPhone(request.number());
        log.error("start tariff comp");
        Optional<List<TariffComponent>> tariffComponents = tariffComponentRepository.findAllByTariffId(request.tariffId());
        log.error("tariff comp");

        if (tariffStatus.get().isEmpty()) {
            log.error("ariff status empty");
            updateTariffStatus(tariffComponents.get(), request);
            tariffStatus = tariffStatusRepository.findAllByPhone(request.number());
            log.error(tariffStatus.get().get(0).toString());
            log.error("ts saved");
        }


        int debit = 0;
        for (TariffStatus ts : tariffStatus.get()) {
            for (TariffComponent tc : tariffComponents.get()) {
                if (Objects.equals(tc.getTypeComponent().getId(), ts.getTypeId().getId())) {
                    var duration = request.end() - request.start();
                    if (ts.getQuantitySpent() > tc.getQuantity()) {
                        int sum = (int) (duration * tc.getPrice());
                        debit += sum;
                        ts.setQuantitySpent((int) (ts.getQuantitySpent() + duration));
                    } else if (ts.getQuantitySpent() + duration > tc.getQuantity()) {
                        debit += (int) ((ts.getQuantitySpent() + duration) * tc.getPrice());
                        ts.setQuantitySpent((int) (ts.getQuantitySpent() + duration));
                    } else if (ts.getQuantitySpent() < tc.getQuantity()) {
                        ts.setQuantitySpent((int) (ts.getQuantitySpent() + duration));
                    }
                }
            }
            log.error("ts new");
            log.error(ts.toString());
            tariffStatusRepository.saveAndFlush(ts);
        }
        HrsRequest hrsRequest = new HrsRequest(request.number(), debit);
        log.error("hrs reques send");
        log.error(hrsRequest.toString());
        return hrsRequest;
    }

    private void updateTariffStatus(List<TariffComponent> tariffComponents, BrtRequest request) {
        log.error("ariff status empty");
        for (TariffComponent tc : tariffComponents) {
            var ts = new TariffStatus();
            ts.setTypeId(tc.getTypeComponent());
            ts.setQuantitySpent(0);
            ts.setPhone(request.number());
            tariffStatusRepository.saveAndFlush(ts);
            log.error("ts made");
        }
    }
}
