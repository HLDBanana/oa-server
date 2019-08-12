package com.hanergy.out.service;

import com.hanergy.out.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-11
 */
public interface ISysDeptService extends IService<SysDept> {

    /*
     * @Author hld
     * @Description //根据部门id批量查询部门信息
     * @Date 9:24 2019/6/11
     * @Param [ids] 部门id
     * @return java.util.List<com.hanergy.out.entity.SysDept>
     **/
    public List<SysDept> getDeptByIds(List<String> ids);
}
