package com.example.charliegutzmanncustomersupport;

import java.io.Serializable;

public class Ticket implements Serializable {
    private String name;
    private String subject;
    private String body;

    private Attachment attachment;

    public Ticket() {
        super();
    }

    public Ticket(String name, String subject, String body, Attachment attachment) {
        this.name = name;
        this.subject = subject;
        this.body = body;
        this.attachment = attachment;
    }

    public String getName() {
        return name;
    }

    public void setName(String customerName) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Attachment getAttachment() {return attachment;}

    public void setAttachment(Attachment attachment){this.attachment = attachment;}

    public boolean hasAttachment() {
        return attachment.getName().length() > 0 && attachment.getContents().length > 0;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "name='" + name + '\'' +
                ", subject=" + subject +
                ", body='" + body + '\'' +
                ", attachment=" + attachment +
                '}';
    }
}
