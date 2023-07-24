package kr.co.mz.sns.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_detail", schema = "sns")
@Data
@NoArgsConstructor
public class UserDetailEntity {

  @Id
  @Column(name = "user_seq", nullable = false)
  private Long userSeq;
  @Basic
  @Column(name = "blocked", nullable = true, length = 255)
  private String blocked;
  @Basic
  @Column(name = "greeting", nullable = true, length = 45)
  private String greeting;
  @Basic
  @Column(name = "profile_picture", nullable = true, length = 255)
  private String profilePicture;
  @Basic
  @Column(name = "created_at", nullable = true)
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at", nullable = true)
  private Timestamp modifiedAt;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailEntity that = (UserDetailEntity) o;
    return Objects.equals(userSeq, that.userSeq) && Objects.equals(blocked, that.blocked)
        && Objects.equals(greeting, that.greeting) && Objects.equals(profilePicture,
        that.profilePicture) && Objects.equals(createdAt, that.createdAt) && Objects.equals(
        modifiedAt, that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userSeq, blocked, greeting, profilePicture, createdAt, modifiedAt);
  }
}
