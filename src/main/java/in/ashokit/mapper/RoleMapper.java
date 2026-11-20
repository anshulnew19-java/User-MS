package in.ashokit.mapper;

import in.ashokit.dto.RoleDto;
import in.ashokit.dto.UserDto;
import in.ashokit.entity.RoleEntity;
import in.ashokit.entity.UserEntity;
import org.modelmapper.ModelMapper;

public class RoleMapper {
    public static final ModelMapper mapper= new ModelMapper();

    public static RoleDto entityToDto(RoleEntity entity) {
        return mapper.map(entity, RoleDto.class);
    }

    public static RoleEntity dtoToEntity(RoleDto dto) {
        return mapper.map(dto, RoleEntity.class);
    }
}
