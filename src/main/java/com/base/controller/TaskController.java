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
    public String safeClaim(@PathVariable String taskId,
                            @PathVariable String userId) {

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        if (task == null) return "NOT FOUND";

        if (task.getAssignee() != null) {
            return "ALREADY CLAIMED BY " + task.getAssignee();
        }

        taskService.claim(taskId, userId);
        return "CLAIMED";
    }

    @PostMapping("/{taskId}/complete")
    public String completeTask(@PathVariable String taskId,
                               @RequestBody(required = false) Map<String, Object> body) {

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        if (task == null) {
            throw new RuntimeException("Task not found or already completed: " + taskId);
        }

        Map<String, Object> variables = (body != null)
                ? new HashMap<>(body)
                : new HashMap<>();

        taskService.complete(taskId, variables);

        return "TASK COMPLETED";
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

    @PostMapping("/{taskId}/completeafter")
    public String completeTask(@PathVariable String taskId) {

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        if (task == null) {
            return "TASK NOT FOUND";
        }

        System.out.println("TASK NAME = " + task.getName());

        taskService.complete(taskId);

        return "TASK COMPLETED";
    }
}