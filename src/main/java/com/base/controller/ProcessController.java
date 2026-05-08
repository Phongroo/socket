package com.base.controller;


import com.base.model.Req.StartProcessRequest;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/start")
    public String start(@RequestBody StartProcessRequest request) {

        Map<String, Object> variables = new HashMap<>();

        variables.put("documentId", request.getDocumentId());
        variables.put("createdBy", request.getCreatedBy());
        variables.put("approvers", request.getApprovers());

        runtimeService.startProcessInstanceByKey(
                "document_approval",
                variables
        );

        return "PROCESS_STARTED";
    }
}