package kr.co.mz.sns.common.mapstruct;

import javax.annotation.processing.Generated;
import kr.co.mz.sns.user.dto.detail.UserDetailDto;
import kr.co.mz.sns.user.entity.UserDetailEntity;
import kr.co.mz.sns.user.entity.UserDetailEntity.UserDetailEntityBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-22T19:42:05+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.7 (JetBrains s.r.o.)"
)
public class UserDetailMapperImpl implements UserDetailMapper {

    @Override
    public UserDetailEntity dtoToEntity(UserDetailDto userDetailDto) {
        if ( userDetailDto == null ) {
            return null;
        }

        UserDetailEntityBuilder userDetailEntity = UserDetailEntity.builder();

        userDetailEntity.detailSeq( userDetailDto.getDetailSeq() );
        userDetailEntity.blocked( userDetailDto.getBlocked() );
        userDetailEntity.greeting( userDetailDto.getGreeting() );
        userDetailEntity.createdAt( userDetailDto.getCreatedAt() );
        userDetailEntity.modifiedAt( userDetailDto.getModifiedAt() );

        return userDetailEntity.build();
    }

    @Override
    public UserDetailDto entityToDto(UserDetailEntity userDetailEntity) {
        if ( userDetailEntity == null ) {
            return null;
        }

        UserDetailDto userDetailDto = new UserDetailDto();

        userDetailDto.setDetailSeq( userDetailEntity.getDetailSeq() );
        userDetailDto.setBlocked( userDetailEntity.getBlocked() );
        userDetailDto.setGreeting( userDetailEntity.getGreeting() );
        userDetailDto.setCreatedAt( userDetailEntity.getCreatedAt() );
        userDetailDto.setModifiedAt( userDetailEntity.getModifiedAt() );

        return userDetailDto;
    }
}
