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
@Table(name = "User_Detail", schema = "sns", catalog = "")
@Data
@NoArgsConstructor
public class UserDetailEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private int seq;
  @Basic
  @Column(name = "profile_picture")
  private String profilePicture;
  @Basic
  @Column(name = "blocked")
  private String blocked;
  @Basic
  @Column(name = "created_at")
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at")
  private Timestamp modifiedAt;
  @Basic
  @Column(name = "User_seq")
  private int userSeq;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq", nullable = false)
  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  @Basic
  @Column(name = "profile_picture", nullable = true, length = 255)
  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  @Basic
  @Column(name = "blocked", nullable = true, length = 1)
  public String getBlocked() {
    return blocked;
  }

  public void setBlocked(String blocked) {
    this.blocked = blocked;
  }

  @Basic
  @Column(name = "created_at", nullable = true)
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  @Basic
  @Column(name = "modified_at", nullable = false)
  public Timestamp getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Timestamp modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  @Basic
  @Column(name = "User_seq", nullable = false)
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
    UserDetailEntity that = (UserDetailEntity) o;
    return seq == that.seq && userSeq == that.userSeq && Objects.equals(profilePicture, that.profilePicture)
        && Objects.equals(blocked, that.blocked) && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(modifiedAt, that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, profilePicture, blocked, createdAt, modifiedAt, userSeq);
  }
}
