package com.example.zuul.filters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Token {
  public Token() {
  }

  private String _id;
  private String email;
  private String createdAt;
  private String updatedAt;
  private float __v;

  // Getter Methods

  public String get_id() {
    return _id;
  }

  public String getEmail() {
    return email;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public float get__v() {
    return __v;
  }

  // Setter Methods

  public void set_id(String _id) {
    this._id = _id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void set__v(float __v) {
    this.__v = __v;
  }
}