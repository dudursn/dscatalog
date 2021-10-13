package io.github.dudursn.dscatalog.controllers.exceptions;

import java.time.Instant;

public class StandardError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String msg;
    private String path;

    public StandardError() {
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
