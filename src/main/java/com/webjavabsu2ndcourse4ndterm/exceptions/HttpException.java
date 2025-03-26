package com.webjavabsu2ndcourse4ndterm.exceptions;

import jakarta.servlet.http.HttpServletResponse;

public class HttpException extends RuntimeException {
    private int status = HttpServletResponse.SC_BAD_REQUEST;
    public HttpException(String message, int status) {
        super(message);
        this.status = status;
    }

    public HttpException(String message) {
        super(message);
    }

    public int getStatus() {
        return status;
    }
}