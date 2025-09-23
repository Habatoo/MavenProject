package org.example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationService {
    public void sendWelcomeEmail(UserDto user) {
        log.info("Sending welcome email to: {}", user.getEmail());
        // Имитация отправки email
    }

    public void sendActivationNotification(UserDto user) {
        log.info("Sending activation notification to: {}", user.getEmail());
        // Имитация отправки уведомления
    }
}

