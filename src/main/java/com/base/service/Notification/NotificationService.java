package com.base.service.Notification;

import com.base.model.Req.NotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private SimpMessagingTemplate template;
    public void sendAssign(
            String username,
            Object data) {

        template.convertAndSend(
                "/topic/tasks/" + username,
                data
        );
    }
}