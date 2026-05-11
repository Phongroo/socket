package com.base.service.Listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.HashMap;
import java.util.Map;
@Component("taskAssignListener")
public class TaskAssignListener  implements TaskListener {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void notify(
            DelegateTask delegateTask) {

        String assignee =
                delegateTask.getAssignee();

        Map<String, Object> body =
                new HashMap<>();

        body.put("username", assignee);

        body.put(
                "processInstanceId",
                delegateTask.getProcessInstanceId()
        );

        body.put(
                "taskId",
                delegateTask.getId()
        );

        body.put(
                "taskName",
                "phong"
        );
        System.out.println(
                "TASK CREATED"
        );

        System.out.println(
                delegateTask.getAssignee()
        );

        restTemplate.postForEntity(
                "http://localhost:8090/notify/assign",
                body,
                Void.class
        );
    }
}
