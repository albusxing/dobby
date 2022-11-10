package com.albusxing.dobby.service;


import com.albusxing.dobby.dto.TokenInfo;
import com.albusxing.dobby.domain.entity.AuthClient;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author liguoqing
 */
public interface AuthClientService extends IService<AuthClient> {

    /**
     * 生成token信息
     * @param client
     * @param secret
     * @return
     */
    TokenInfo genToken(String client, String secret);

    /**
     * 根据client查询
     * @param client
     * @return
     */
    AuthClient findByClient(String client);
}
