package com.base.model.Req;

public class NotificationMessage {
    private String type;

    private String message;

    private String processInstanceId;

    private String assignee;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public NotificationMessage(String type, String message, String processInstanceId, String assignee) {
        this.type = type;
        this.message = message;
        this.processInstanceId = processInstanceId;
        this.assignee = assignee;
    }
}
