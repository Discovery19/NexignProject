package org.project.brtservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
//import org.project.brtservice.model.Cdr;
//import org.project.brtservice.model.User;
//import org.project.brtservice.repository.TransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.project.brtservice.requests.BrtRequest;
import org.project.brtservice.requests.CdrRequest;
import org.project.brtservice.requests.HrsRequest;
import org.project.brtservice.requests.MonthRequest;
import org.project.brtservice.model.User;
import org.project.brtservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrtService {
    private final UserRepository userRepository;

    @PostConstruct
    private void addUser() {
        User user = new User();
        user.setId(79787478615L);
        user.setTariffId(11L);
        user.setBalance(50);
        userRepository.saveAndFlush(user);
        user = new User();
        user.setId(79259876543L);
        user.setTariffId(12L);
        user.setBalance(150);
        userRepository.saveAndFlush(user);
        user = new User();
        user.setId(79001234567L);
        user.setTariffId(12L);
        user.setBalance(15);
        userRepository.saveAndFlush(user);
        user = new User();
        user.setId(79009876543L);
        user.setTariffId(11L);
        user.setBalance(500);
        userRepository.saveAndFlush(user);
        user = new User();
        user.setId(79161234500L);
        user.setTariffId(11L);
        user.setBalance(550);
        userRepository.saveAndFlush(user);
    }

    public Optional<BrtRequest> authorize(CdrRequest request) {
        Optional<User> user = userRepository.findById(request.number());
        Optional<BrtRequest> brtRequest = Optional.empty();
        if (user.isPresent()) {
            brtRequest = Optional.of(new BrtRequest(request.id(), request.type(), request.number(), request.start(), request.end(), user.get().getTariffId()));
        }
        return brtRequest;
    }

    public void balanceOperation(HrsRequest notification) {
        User user = userRepository.findById(notification.phone()).get();
        user.setBalance(user.getBalance() - notification.debit());
        userRepository.saveAndFlush(user);
    }

    public void monthBalanceOperation(MonthRequest notification) {
        var users = userRepository.findAllByTariffId(notification.tariff());
        for (User u : users) {
            u.setBalance(u.getBalance() - notification.debit());
            userRepository.saveAndFlush(u);
        }
    }
}
