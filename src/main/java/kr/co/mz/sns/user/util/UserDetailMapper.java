package kr.co.mz.sns.user.dto.mapstruct;

import kr.co.mz.sns.user.dto.detail.UserDetailAndProfileDto;
import kr.co.mz.sns.user.dto.detail.UserDetailDto;
import kr.co.mz.sns.user.entity.UserDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserDetailMapper {
    UserDetailEntity dtoToEntity(UserDetailDto userDetailDto);

    UserDetailDto entityToDto(UserDetailEntity userDetailEntity);

    UserDetailAndProfileDto entityToDetailAndFileDto(UserDetailEntity userDetailEntity);
}
