package org.project.crm.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.project.crm.repository.UserRepository;
import org.project.crm.dto.InfoAbonent;
import org.project.crm.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @PostConstruct
    private void addto(){
        User user =new User();
        user.setId(79787478614L);
        user.setTariffId(11L);
        user.setBalance(100);
        //user.setRole("USER");
        createUser(user);
        user =new User();
        user.setId(1L);
        user.setTariffId(0L);
        user.setBalance(0);
        //user.setRole("ADMIN");
        createUser(user);
    }
    //test
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
    public void pay(Long msisdn, Integer money){
        var u = userRepository.findById(msisdn).get();
        var balance = u.getBalance();
        u.setBalance(balance+money);
        userRepository.saveAndFlush(u);
    }

    public void changeTariff(Long msisdn, Long tariffId) {
        var u = userRepository.findById(msisdn).get();
        u.setTariffId(tariffId);
        userRepository.saveAndFlush(u);
    }

    public User getUser(Long msisdn) {
        return userRepository.findById(msisdn).get();
    }
    public List<User> getAllUsersForScheduler(){
        return userRepository.findAll();
    }
}

