package com.base.controller;


import com.base.model.Req.StartProcessRequest;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/start")
    public ResponseEntity<?> start(
            @RequestBody StartProcessRequest request) {

        try {

            Map<String, Object> variables =
                    new HashMap<>();

            variables.put(
                    "documentId",
                    request.getDocumentId()
            );

            variables.put(
                    "createdBy",
                    request.getCreatedBy()
            );

            variables.put(
                    "approvers",
                    request.getApprovers()
            );

            ProcessInstance processInstance =
                    runtimeService.startProcessInstanceByKey(
                            "document_approval",
                            variables
                    );

            Map<String, Object> response =
                    new HashMap<>();

            response.put("success", true);

            response.put(
                    "message",
                    "PROCESS_STARTED"
            );

            response.put(
                    "processInstanceId",
                    processInstance.getProcessInstanceId()
            );

            response.put(
                    "definitionId",
                    processInstance.getProcessDefinitionId()
            );

            response.put(
                    "businessKey",
                    processInstance.getBusinessKey()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            Map<String, Object> error =
                    new HashMap<>();

            error.put("success", false);

            error.put(
                    "message",
                    e.getMessage()
            );

            return ResponseEntity
                    .badRequest()
                    .body(error);
        }
    }
    @GetMapping("/process/{processInstanceId}")
    public ResponseEntity<?> getProcess(
            @PathVariable String processInstanceId) {

        ProcessInstance process =
                runtimeService
                        .createProcessInstanceQuery()
                        .processInstanceId(processInstanceId)
                        .singleResult();

        if (process == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Process not found");
        }

        Map<String, Object> result =
                new HashMap<>();

        result.put(
                "processInstanceId",
                process.getProcessInstanceId()
        );

        result.put(
                "definitionId",
                process.getProcessDefinitionId()
        );

        result.put(
                "businessKey",
                process.getBusinessKey()
        );

        return ResponseEntity.ok(result);
    }



}