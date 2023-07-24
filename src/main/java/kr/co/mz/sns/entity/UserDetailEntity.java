package kr.co.mz.sns.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User_Detail", schema = "sns")
@Data
@NoArgsConstructor
public class UserDetailEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private Long seq;
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
  private Long userSeq;

  @OneToOne(mappedBy = "userDetail", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private UserEntity user;


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var that = (UserDetailEntity) o;
    return seq == that.seq && userSeq == that.userSeq && Objects.equals(profilePicture, that.profilePicture)
        && Objects.equals(blocked, that.blocked) && Objects.equals(createdAt, that.createdAt)
        && Objects.equals(modifiedAt, that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, profilePicture, blocked, createdAt, modifiedAt, userSeq);
  }
}
