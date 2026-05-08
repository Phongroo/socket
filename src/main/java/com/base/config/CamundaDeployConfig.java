package com.base.config;

import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CamundaDeployConfig {
    @Autowired
    private RepositoryService repositoryService;

    @PostConstruct
    public void deployProcess() {

        repositoryService.createDeployment()
                .addClasspathResource("processes/document_approval.bpmn")
                .deploy();
    }

}
