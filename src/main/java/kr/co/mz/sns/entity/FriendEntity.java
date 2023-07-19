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

@Entity
@Table(name = "Friend", schema = "sns", catalog = "")
public class FriendEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private int seq;
  @Basic
  @Column(name = "status")
  private String status;
  @Basic
  @Column(name = "created_at")
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at")
  private Timestamp modifiedAt;
  @Basic
  @Column(name = "User_seq")
  private int userSeq;

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

  public int getUserSeq() {
    return userSeq;
  }

  public void setUserSeq(int userSeq) {
    this.userSeq = userSeq;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FriendEntity that = (FriendEntity) o;
    return seq == that.seq && userSeq == that.userSeq && Objects.equals(status, that.status)
        && Objects.equals(createdAt, that.createdAt) && Objects.equals(modifiedAt, that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, status, createdAt, modifiedAt, userSeq);
  }
}
