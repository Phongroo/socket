package com.base.controller;

import com.base.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/notify")
public class NotificationController {
    @Autowired
    private NotificationService
            notificationService;

    @PostMapping("/assign")
    public ResponseEntity<?> notifyAssign(
            @RequestBody Map<String, Object> req) {

        String username =
                req.get("userName").toString();

        notificationService.sendAssign(
                username,
                req
        );
        System.out.println(
                "TASK CREATED"
        );

        return ResponseEntity.ok().build();
    }
}
