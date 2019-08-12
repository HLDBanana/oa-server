package com.hanergy.out.service;

import com.hanergy.out.entity.OaMeetingSetPower;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-06-11
 */
public interface IOaMeetingSetPowerService extends IService<OaMeetingSetPower> {

    public int saveOaPower(OaMeetingSetPower power);

    /*
     * @Author hld
     * @Description //通过会议室id删除权限数据
     * @Date 8:58 2019/6/12
     * @Param [oaMeetingSetId]  会议室id
     * @return int
     **/
    public int delOaPowerBySetId(String oaMeetingSetId);

}
