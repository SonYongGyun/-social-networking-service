package kr.co.mz.sns.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comment", schema = "sns", catalog = "")
@Data
@NoArgsConstructor
public class CommentEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private Long seq;
  @Basic
  @Column(name = "content")
  private String content;
  @Basic
  @Column(name = "created_at")
  private Timestamp createdAt;
  @Basic
  @Column(name = "modified_at")
  private Timestamp modifiedAt;
  @Basic
  @Column(name = "User_seq")
  private Long userSeq;
  @Basic
  @Column(name = "Post_seq")
  private Long postSeq;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "User_seq")
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Post_seq")
  private PostEntity postEntity;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    var that = (CommentEntity) o;
    return Objects.equals(seq, that.seq) && Objects.equals(userSeq, that.userSeq) && Objects.equals(postSeq, that.postSeq) && Objects.equals(content,
        that.content) && Objects.equals(createdAt, that.createdAt) && Objects.equals(modifiedAt,
        that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, content, createdAt, modifiedAt, userSeq, postSeq);
  }
}
