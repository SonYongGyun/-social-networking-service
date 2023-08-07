package kr.co.mz.sns.repository.user;

import java.util.List;
import java.util.Optional;
import kr.co.mz.sns.dto.user.friend.FriendDetailDto;
import kr.co.mz.sns.entity.user.FriendRelationshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRelationshipRepository extends JpaRepository<FriendRelationshipEntity, Long> {

  Optional<FriendRelationshipEntity> findByFriendEntity_Seq(Long friendSeq);

  Optional<FriendRelationshipEntity> findByUserEntity_Seq(Long userSeq);

  Optional<FriendRelationshipEntity> findBySeq(Long seq);

  @Query("""
      select new kr.co.mz.sns.dto.user.friend.FriendDetailDto (
          f.seq,
          f.userEntity.seq,
          f.status,
          f.createdAt,
          f.friendEntity.seq,
          f.friendEntity.name,
          f.friendEntity.userDetail.greeting,
          f.friendEntity.userDetail.lastLoginAt
      )
      FROM FriendRelationshipEntity f
      WHERE f.userEntity.seq = :userSeq
      """)
  Page<FriendDetailDto> findAllByUserSeqAsPage(@Param("userSeq") Long userSeq, Pageable pageable);

  Optional<FriendRelationshipEntity> findByUserEntity_SeqAndFriendEntity_Seq(Long userSeq, Long friendSeq);

  @Query("select f from FriendRelationshipEntity f where f.userEntity.seq in :userSeqs")
  List<FriendRelationshipEntity> findAllByUserEntity_SeqIn(@Param("userSeqs") List<Long> userSeqs, Pageable pageable);

  @Query("""
      select new kr.co.mz.sns.dto.user.friend.FriendDetailDto (
          f.seq,
          f.userEntity.seq,
          f.status,
          f.createdAt,
          f.friendEntity.seq,
          f.friendEntity.name,
          f.friendEntity.userDetail.greeting,
          f.friendEntity.userDetail.lastLoginAt
      )
      from FriendRelationshipEntity f
      where f.userEntity.seq = :userSeq and f.friendEntity.name = :friendName and f.status = :status
      """)
  List<FriendDetailDto> findByUserEntity_SeqAndFriendEntity_NameAndStatus(
      @Param("userSeq") Long userSeq,
      @Param("friendName") String friendName,
      @Param("status") String status
  );
}

