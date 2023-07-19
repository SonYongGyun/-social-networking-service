package kr.co.mz.sns.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User", schema = "sns", catalog = "")
@Data
@NoArgsConstructor
public class UserEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private int seq;
  @Basic
  @Column(name = "email")
  private String email;
  @Basic
  @Column(name = "name")
  private String name;
  @Basic
  @Column(name = "password")
  private String password;
  @Basic
  @Column(name = "last_login_at")
  private Timestamp lastLoginAt;
  @Basic
  @Column(name = "created_at")
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at")
  private Timestamp modifiedAt;

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Timestamp getLastLoginAt() {
    return lastLoginAt;
  }

  public void setLastLoginAt(Timestamp lastLoginAt) {
    this.lastLoginAt = lastLoginAt;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Timestamp modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserEntity that = (UserEntity) o;
    return seq == that.seq && Objects.equals(email, that.email) && Objects.equals(name, that.name)
        && Objects.equals(password, that.password) && Objects.equals(lastLoginAt, that.lastLoginAt)
        && Objects.equals(createdAt, that.createdAt) && Objects.equals(modifiedAt, that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, email, name, password, lastLoginAt, createdAt, modifiedAt);
  }
}
