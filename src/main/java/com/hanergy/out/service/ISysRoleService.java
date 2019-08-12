package com.hanergy.out.service;

import com.hanergy.out.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-26
 */
public interface ISysRoleService extends IService<SysRole> {
    /*
     * @Author hld
     * @Description //通过员工编号和所属项目获取用户角色信息
     * @Date 17:18 2019-6-26
     * @Param [jobNumber, projectTag]
     * @return com.hanergy.out.entity.SysRole
     **/
    public List<SysRole> getRoleByJobnumberAndProject(String jobNumber, String projectTag);

}
