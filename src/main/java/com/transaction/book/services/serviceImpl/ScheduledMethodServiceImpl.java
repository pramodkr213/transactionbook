package com.transaction.book.services.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.transaction.book.services.serviceInterface.ScheduledMethodService;

@Service
public class ScheduledMethodServiceImpl implements ScheduledMethodService{

    private final FCMService fcmService;
    private final UserServiceImpl userServiceImpl;

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins; 

    public ScheduledMethodServiceImpl(FCMService fcmService, UserServiceImpl userServiceImpl) {
        this.fcmService = fcmService;
        this.userServiceImpl = userServiceImpl;
    }

    @Scheduled(cron = "0 0 11 * * ?")
    @Override
    public void sendMorningNotification() {
        List<String> userTokens = this.userServiceImpl.getAllFcmTokens();
        userTokens.addAll(this.userServiceImpl.getAllWebFcmTokens());
        if(userTokens.isEmpty() || userTokens==null){
            return;
        }
        for(String userToken:userTokens){
            fcmService.sendNotification(userToken, "Due Date Reminder", "Reminder regarding money !",allowedOrigins[0]);
        }
    }

    @Scheduled(cron = "0 0 19 * * ?")
    @Override
    public void sendEveningNotification() {
        List<String> userTokens = this.userServiceImpl.getAllFcmTokens();
        userTokens.addAll(this.userServiceImpl.getAllWebFcmTokens());
        if(userTokens.isEmpty() || userTokens==null){
            return;
        }
        for(String userToken:userTokens){
            fcmService.sendNotification(userToken, "Due Date Reminder", "Reminder regarding money !",allowedOrigins[0]);
        }
    }
    
}
