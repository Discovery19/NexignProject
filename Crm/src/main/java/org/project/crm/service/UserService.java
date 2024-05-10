package org.project.crm.service;

import lombok.RequiredArgsConstructor;
import org.project.crm.dto.InfoAbonent;
import org.project.crm.model.User;
import org.project.crm.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис UserService предоставляет функциональность для работы с пользователями в системе CRM.
 * Он обеспечивает операции по созданию, оплате, изменению тарифа и получению информации о пользователях.
 * Данный сервис использует UserRepository для доступа к данным о пользователях в базе данных.
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private User createUser(User testUser) {
        return userRepository.saveAndFlush(testUser);
    }

    public User createUser(InfoAbonent info) {
        User user = new User();
        user.setId(Long.valueOf(info.msisdn()));
        user.setBalance(info.money());
        user.setTariffId(info.tariffId());
        return userRepository.saveAndFlush(user);
    }

    public String pay(Long msisdn, Integer money) {
        var u = userRepository.findById(msisdn).get();
        var balance = u.getBalance();
        u.setBalance(balance + money);
        userRepository.saveAndFlush(u);
        return "New balance: " + balance + money;
    }

    public String changeTariff(Long msisdn, Long tariffId) {
        var u = userRepository.findById(msisdn).get();
        u.setTariffId(tariffId);
        userRepository.saveAndFlush(u);
        return "New tariff: " + tariffId;
    }

    public User getUser(Long msisdn) {
        return userRepository.findById(msisdn).get();
    }

    public List<User> getAllUsersForScheduler() {
        return userRepository.findAll();
    }
}

