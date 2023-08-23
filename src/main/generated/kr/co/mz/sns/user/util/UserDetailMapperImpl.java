package kr.co.mz.sns.user.util;

import javax.annotation.processing.Generated;
import kr.co.mz.sns.user.dto.detail.UserDetailAndProfileDto;
import kr.co.mz.sns.user.dto.detail.UserDetailDto;
import kr.co.mz.sns.user.entity.UserDetailEntity;
import kr.co.mz.sns.user.entity.UserDetailEntity.UserDetailEntityBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-23T09:45:42+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.7 (JetBrains s.r.o.)"
)
public class UserDetailMapperImpl implements UserDetailMapper {

    @Override
    public UserDetailEntity dtoToEntity(UserDetailDto userDetailDto) {

        UserDetailEntityBuilder userDetailEntity = UserDetailEntity.builder();

        if ( userDetailDto != null ) {
            userDetailEntity.detailSeq( userDetailDto.getDetailSeq() );
            userDetailEntity.blocked( userDetailDto.getBlocked() );
            userDetailEntity.greeting( userDetailDto.getGreeting() );
            userDetailEntity.createdAt( userDetailDto.getCreatedAt() );
            userDetailEntity.modifiedAt( userDetailDto.getModifiedAt() );
        }

        return userDetailEntity.build();
    }

    @Override
    public UserDetailDto entityToDto(UserDetailEntity userDetailEntity) {

        UserDetailDto userDetailDto = new UserDetailDto();

        if ( userDetailEntity != null ) {
            userDetailDto.setDetailSeq( userDetailEntity.getDetailSeq() );
            userDetailDto.setBlocked( userDetailEntity.getBlocked() );
            userDetailDto.setGreeting( userDetailEntity.getGreeting() );
            userDetailDto.setCreatedAt( userDetailEntity.getCreatedAt() );
            userDetailDto.setModifiedAt( userDetailEntity.getModifiedAt() );
        }

        return userDetailDto;
    }

    @Override
    public UserDetailAndProfileDto entityToDetailAndFileDto(UserDetailEntity userDetailEntity) {

        UserDetailAndProfileDto userDetailAndProfileDto = new UserDetailAndProfileDto();

        if ( userDetailEntity != null ) {
            userDetailAndProfileDto.setDetailSeq( userDetailEntity.getDetailSeq() );
            userDetailAndProfileDto.setBlocked( userDetailEntity.getBlocked() );
            userDetailAndProfileDto.setGreeting( userDetailEntity.getGreeting() );
            userDetailAndProfileDto.setCreatedAt( userDetailEntity.getCreatedAt() );
            userDetailAndProfileDto.setModifiedAt( userDetailEntity.getModifiedAt() );
        }

        return userDetailAndProfileDto;
    }
}
