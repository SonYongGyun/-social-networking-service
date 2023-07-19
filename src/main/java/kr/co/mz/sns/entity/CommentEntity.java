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
@Table(name = "Comment", schema = "sns", catalog = "")
public class CommentEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "seq")
  private int seq;
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
  private int userSeq;
  @Basic
  @Column(name = "Post_seq")
  private int postSeq;

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
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

  public int getPostSeq() {
    return postSeq;
  }

  public void setPostSeq(int postSeq) {
    this.postSeq = postSeq;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentEntity that = (CommentEntity) o;
    return seq == that.seq && userSeq == that.userSeq && postSeq == that.postSeq && Objects.equals(content,
        that.content) && Objects.equals(createdAt, that.createdAt) && Objects.equals(modifiedAt,
        that.modifiedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq, content, createdAt, modifiedAt, userSeq, postSeq);
  }
}
