package com.albusxing.dobby.service.impl;


import com.albusxing.dobby.common.constant.DataStatusEnum;
import com.albusxing.dobby.dto.TokenInfo;
import com.albusxing.dobby.entity.AuthClient;
import com.albusxing.dobby.mapper.AuthClientMapper;
import com.albusxing.dobby.service.AuthClientService;
import com.albusxing.dobby.util.JwtTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author liguoqing
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthClientServiceImpl extends ServiceImpl<AuthClientMapper, AuthClient> implements AuthClientService {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public TokenInfo genToken(String client, String secret) {
        QueryWrapper<AuthClient> queryWrapper = new QueryWrapper<AuthClient>()
                .eq("status", DataStatusEnum.NORMAL.getCode())
                .eq("client", client)
                .eq("secret", secret);
        AuthClient authClient = this.getOne(queryWrapper, false);
        if (Objects.isNull(authClient)) {
            throw new RuntimeException("未知的客户端");
        }
        String token = jwtTokenUtil.generateToken(client, authClient.getExpiration());
        return TokenInfo.builder().token(token).expiration(authClient.getExpiration()).build();
    }


    @Override
    public AuthClient findByClient(String client) {
        QueryWrapper<AuthClient> queryWrapper = new QueryWrapper<AuthClient>()
                .eq("status", DataStatusEnum.NORMAL.getCode())
                .eq("client", client);
        return this.getOne(queryWrapper, false);
    }
}
