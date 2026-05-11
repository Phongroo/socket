package com.base.controller;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/complete")
    public ResponseEntity<?> completeTask(
            @RequestBody Map<String, Object> body) {

        try {

            if (body == null ||
                body.get("processInstanceId") == null) {

                return ResponseEntity.badRequest()
                        .body("processInstanceId is required");
            }

            String processInstanceId =
                    body.get("processInstanceId")
                            .toString();

            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .singleResult();

            if (task == null) {

                return ResponseEntity.badRequest()
                        .body(
                                "Task not found or already completed"
                        );
            }

            // remove technical field
            body.remove("processInstanceId");

            Map<String, Object> variables =
                    new HashMap<>(body);

            taskService.complete(
                    task.getId(),
                    variables
            );

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", true);
            response.put("message", "TASK_COMPLETED");
            response.put("taskId", task.getId());
            response.put("processInstanceId",
                    processInstanceId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, Object> error =
                    new HashMap<>();

            error.put("success", false);
            error.put("message", e.getMessage());

            return ResponseEntity.badRequest()
                    .body(error);
        }
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