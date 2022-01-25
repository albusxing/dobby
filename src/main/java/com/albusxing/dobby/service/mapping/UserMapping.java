package com.albusxing.dobby.service.mapping;

import com.albusxing.dobby.dto.UserReq;
import com.albusxing.dobby.dto.UserResp;
import com.albusxing.dobby.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author Albusxing
 */
@Mapper(componentModel = "spring")
public interface UserMapping {


    User toUser(UserReq userReq);

    UserResp toUserResp(User user);

    void updateToUser(UserReq userReq, @MappingTarget User user);
}
