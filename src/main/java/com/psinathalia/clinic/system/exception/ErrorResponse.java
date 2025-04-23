package com.psinathalia.clinic.system.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
  private String message;
  private String error;
  private LocalDateTime timestamp;

  public ErrorResponse(String message, String error) {
    this.message = message;
    this.error = error;
    this.timestamp = LocalDateTime.now();
  }

  // Getters e Setters
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}