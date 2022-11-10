package com.albusxing.dobby.service.converter;

import com.albusxing.dobby.dto.UserCmd;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author Albusxing
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    User toUser(UserCmd userReq);

    UserResp toUserResp(User user);

    void updateToUser(UserCmd userReq, @MappingTarget User user);
}
