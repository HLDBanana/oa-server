package com.hanergy.out.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hanergy.out.entity.MiddleProtection;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 劳动保护  
2019年7月15日杨国栋创建 服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-07-15
 */
public interface IMiddleProtectionService extends IService<MiddleProtection> {

    public IPage<MiddleProtection> getMiddleProtection(Integer pageSize, Integer current);

}
