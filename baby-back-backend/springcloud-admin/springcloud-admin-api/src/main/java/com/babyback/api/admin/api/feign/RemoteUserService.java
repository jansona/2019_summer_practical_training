package com.babyback.api.admin.api.feign;

import com.babyback.api.admin.api.entity.SysUser;
import com.babyback.api.admin.api.feign.fallback.RemoteUserServiceFallbackImpl;
import com.babyback.api.common.utils.QueryPage;
import com.babyback.api.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * 用户远程调用接口
 *
 */
@FeignClient(value = "springcloud-admin-biz", fallback = RemoteUserServiceFallbackImpl.class)
public interface RemoteUserService {

    /**
     * 根据用户名查找用户信息
     *
     * @return
     */
    @GetMapping("/user/info/{username}")
    Result<SysUser> info(@PathVariable("username") String username);

    @GetMapping("/user/infoTel/{username}")
    Result<SysUser> infoTel(@PathVariable("username") String username);
}
