package com.base.controller;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    // =========================================
    // STEP 2 - GET ALL TASK
    // =========================================
    @GetMapping("/getTasks")
    public List<Map<String, Object>> getTasks() {

        List<Task> tasks = taskService.createTaskQuery().list();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Task task : tasks) {

            Map<String, Object> map = new HashMap<>();

            map.put("id", task.getId());
            map.put("name", task.getName());
            map.put("assignee", task.getAssignee());

            result.add(map);
        }

        return result;
    }

    // =========================================
    // STEP 3 - CLAIM TASK
    // =========================================
    @PostMapping("/{taskId}/claim/{userId}")
    public String claimTask(@PathVariable String taskId,
                            @PathVariable String userId) {

        taskService.claim(taskId, userId);

        return "TASK CLAIMED";
    }

    // =========================================
    // STEP 4 - COMPLETE TASK
    // =========================================
    @PostMapping("/{taskId}/complete")
    public String completeTask(@PathVariable String taskId) {

        taskService.complete(taskId);

        return "TASK COMPLETED";
    }

    // =========================================
    // STEP 5 - APPROVE TASK
    // =========================================
    @PostMapping("/{taskId}/approve")
    public String approveTask(@PathVariable String taskId) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", true);

        taskService.complete(taskId, variables);

        return "TASK APPROVED";
    }

    // =========================================
    // STEP 6 - REJECT TASK
    // =========================================
    @PostMapping("/{taskId}/reject")
    public String rejectTask(@PathVariable String taskId) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", false);

        taskService.complete(taskId, variables);

        return "TASK REJECTED";
    }
}