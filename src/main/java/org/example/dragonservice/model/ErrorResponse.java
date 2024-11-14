package org.example.dragonservice.model;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "error")
public class ErrorResponse {
    private Integer code;
    private String message;
    private List<ErrorDetail> details;

    @XmlElement
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    @XmlElement
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @XmlElementWrapper(name = "details")
    @XmlElement(name = "detail")
    public List<ErrorDetail> getDetails() { return details; }
    public void setDetails(List<ErrorDetail> details) { this.details = details; }

    @XmlRootElement(name = "detail")
    public static class ErrorDetail {
        private String field;
        private String issue;

        @XmlElement
        public String getField() { return field; }
        public void setField(String field) { this.field = field; }

        @XmlElement
        public String getIssue() { return issue; }
        public void setIssue(String issue) { this.issue = issue; }
    }
}