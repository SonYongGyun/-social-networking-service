package kr.co.mz.sns.user.util;

import javax.annotation.processing.Generated;
import kr.co.mz.sns.user.dto.UserDto;
import kr.co.mz.sns.user.dto.UserDto.UserDtoBuilder;
import kr.co.mz.sns.user.entity.UserEntity;
import kr.co.mz.sns.user.entity.UserEntity.UserEntityBuilder;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-23T11:31:44+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.7 (JetBrains s.r.o.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto entityToDto(UserEntity userEntity) {

        UserDtoBuilder userDto = UserDto.builder();

        if ( userEntity != null ) {
            userDto.seq( userEntity.getSeq() );
            userDto.email( userEntity.getEmail() );
            userDto.password( userEntity.getPassword() );
            userDto.role( userEntity.getRole() );
            userDto.name( userEntity.getName() );
            userDto.createdAt( userEntity.getCreatedAt() );
            userDto.modifiedAt( userEntity.getModifiedAt() );
        }

        return userDto.build();
    }

    @Override
    public UserEntity dtoToEntity(UserDto userDto) {

        UserEntityBuilder userEntity = UserEntity.builder();

        if ( userDto != null ) {
            userEntity.seq( userDto.getSeq() );
            userEntity.email( userDto.getEmail() );
            userEntity.name( userDto.getName() );
            userEntity.password( userDto.getPassword() );
            userEntity.role( userDto.getRole() );
            userEntity.createdAt( userDto.getCreatedAt() );
            userEntity.modifiedAt( userDto.getModifiedAt() );
        }

        return userEntity.build();
    }
}
