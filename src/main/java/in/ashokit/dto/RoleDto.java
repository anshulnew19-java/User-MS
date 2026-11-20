package in.ashokit.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class RoleDto {
    private Integer roleId;

    private String name;
}
