package com.babyback.api.admin.api.feign.fallback;

import com.babyback.api.admin.api.entity.SysUser;
import com.babyback.api.admin.api.feign.RemoteUserService;
import com.babyback.api.common.utils.QueryPage;
import com.babyback.api.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.cn;

/**
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService {

    @Override
    public Result<SysUser> info(String username) {
        log.error("查询用户信息失败，username >> {}", username);
        return null;
    }

    @Override
    public Result<SysUser> infoTel(String username) {
        log.error("查询电话或email失败，name >> {}", username);
        return null;
    }


}
