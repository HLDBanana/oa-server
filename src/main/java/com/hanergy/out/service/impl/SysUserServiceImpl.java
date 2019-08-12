package com.hanergy.out.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanergy.out.controller.OaMeetingController;
import com.hanergy.out.dao.SysUserMapper;
import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Override
    public List<String> getUserByIds(List<Long> ids) {
        if (ids != null && ids.size() > 0){
            List<SysUser> userList = this.baseMapper.selectBatchIds(ids);
            List<String> emails = new ArrayList<>();
            if (userList != null){
                for (int i = 0; i < userList.size() ; i++) {
                    String email = userList.get(i).getEmail();
                    emails.add(email);
                }
            }
            return emails;
        }
       return  null;
    }

    @Override
    public List<SysUser> getsysUserByIds(List<Long> ids) {

        return this.baseMapper.selectBatchIds(ids);
    }

    @Override
    public List<SysUser> getMeetingAttendUser(Long meetingId,Integer type) {

        return this.baseMapper.getMeetingAttendUser(meetingId,type);
    }

    @Override
    public SysUser getUserById(Long id) {

        return this.baseMapper.selectById(id);
    }

    @Override
    public SysUser getUserByJobnumberOrEamil(String jobNumber, String email) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        log.info("通过员工号查询员工信息"+ jobNumber +" email = "+ email);
        if (jobNumber != null && !"".equals(jobNumber)){
            log.info(jobNumber);
            queryWrapper.eq("job_number",jobNumber);
        }
        if (email != null && !"".equals(email)){
            log.info(email);
            queryWrapper.eq("email",email);
        }
        return this.baseMapper.selectOne(queryWrapper);
    }
}
