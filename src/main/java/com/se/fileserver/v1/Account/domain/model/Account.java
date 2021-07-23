package com.se.fileserver.v1.Account.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class Account {

  @Id
  @Column(name ="USER_ID")
  private String id;
  private String password;
  private String auth;

  public Account() {
  }

  public Account(String id, String password, String auth) {
    this.id = id;
    this.password = password;
    this.auth = auth;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", password='" + password + '\'' +
        ", auth='" + auth + '\'' +
        '}';
  }
}
