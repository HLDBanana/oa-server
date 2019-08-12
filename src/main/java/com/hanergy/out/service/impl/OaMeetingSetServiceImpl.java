package com.hanergy.out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hanergy.out.dao.OaMeetingSetMapper;
import com.hanergy.out.entity.OaMeetingSet;
import com.hanergy.out.service.IOaMeetingSetService;
import com.hanergy.out.utils.DateUtils;
import com.hanergy.out.utils.PageUtils;
import com.hanergy.out.vo.OaMeetingSetVo;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会议室设置
19年5月9日新增 服务实现类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
@Service
public class OaMeetingSetServiceImpl extends ServiceImpl<OaMeetingSetMapper, OaMeetingSet> implements IOaMeetingSetService {

    @Override
    public int saveOaMeeting(OaMeetingSet oaMeetingSet) {

        return this.baseMapper.insert(oaMeetingSet);
    }

    @Override
    public int delOaMeeting(String id) {

        return this.baseMapper.deleteById(id);
    }

    @Override
    public int updateOaMeeting(OaMeetingSet oaMeetingSet) {

        return this.baseMapper.updateById(oaMeetingSet);
    }

    @Override
    public List<OaMeetingSetVo> getOaMeeting() {
        return this.baseMapper.getOaMeeting();
    }

    @Override
    public IPage<OaMeetingSetVo> getAllOaMeeting(Integer pageNumber,Integer pageSize) {
        QueryWrapper<OaMeetingSet> queryWrapper = new QueryWrapper<>();
        //         select d.name as sysDeptName,s.* from oa_meeting_set s, sys_dept d
        //         where d.id = s.sys_dept_id  order by type,meeting_name limit #{start},#{pageSize}
        Integer total = this.baseMapper.selectCount(queryWrapper);
        IPage<OaMeetingSetVo> page = new Page<>();
        page.setTotal(total);
        page.setSize(pageSize);
        page.setCurrent(pageNumber);
        Integer start = (pageNumber - 1)*pageSize;
        page.setRecords(this.baseMapper.allOaMeeting(start,pageSize));
        return page;
    }

    @Override
    public List<OaMeetingSetVo> getPowerOaMeeting(Long userId, String deptId,Integer type) {

        return this.baseMapper.powerOaMeeting(userId,deptId,type);
    }

    @Override
    public List<OaMeetingSetVo> getVirtualMeeting() {
        QueryWrapper<OaMeetingSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2)
                    .eq("state",1);
        return this.baseMapper.getVirtualMeeting();
    }

    @Override
    public List<OaMeetingSet> getMeetingByType(Integer type) {
        QueryWrapper<OaMeetingSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<OaMeetingSet> getAllMeeting() {
        Date yesterday = DateUtils.addDateDays(new Date(),-1);
        Date date = DateUtils.stringToDate(DateUtils.dateToString(yesterday,DateUtils.DATE_PATTERN),DateUtils.DATE_PATTERN);
        QueryWrapper<OaMeetingSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public OaMeetingSetVo getOneOaMeeting(String id) {

        return this.baseMapper.getOneOaMeeting(id);
    }

    @Override
    public List<OaMeetingSetVo> getPowerPartMeeting(Long userId,Integer type) {

        return this.baseMapper.getPowerPartMeeting(userId,type);
    }

    @Override
    public List<OaMeetingSetVo> deptOrUserIsNotNull(Integer type) {

        return this.baseMapper.deptOrUserIsNotNull(type);
    }

    @Override
    public OaMeetingSet getMeetingSetByMeetingId(Long id) {

        return this.baseMapper.getMeetingSetByMeetingId(id);
    }

    @Override
    public int saveVirtualMeeting(OaMeetingSet oaMeetingSet) {
        return this.baseMapper.insert(oaMeetingSet);
    }

}
