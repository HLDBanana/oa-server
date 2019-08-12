package com.hanergy.out.controller;

import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.ISysDeptService;
import com.hanergy.out.service.ISysUserService;
import com.hanergy.out.utils.R;
import com.hanergy.out.vo.OaForwardVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName OaUserController
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/6/20 18:09)
 */
@RestController
@RequestMapping("/v1/oaUser")
public class OaUserController {

    Logger log = LoggerFactory.getLogger(OaUserController.class);

    @Autowired
    private ISysUserService sysUserService;             //用户表

    @Autowired
    private ISysDeptService sysDeptService;             //部门表

    @ApiOperation(value="OA页面跳转",notes="获取用户信息，返回跳转链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name="personNo",value="员工编号",required=true,paramType="query"),
            @ApiImplicitParam(name="email",value="邮箱",required=true,paramType="query"),
                @ApiImplicitParam(name="type",value="1:会议预约  2：会议室管理",required=true,paramType="query"),
    })
    @PostMapping("/oaForward")
    public R getOaUser(@RequestBody OaForwardVo forwardVo){
        if ((forwardVo.getJobNumber() == null || "".equals(forwardVo.getJobNumber())) &&
                (forwardVo.getEmail() == null || "".equals(forwardVo.getEmail()))){
            return R.error();
        }
        SysUser user = sysUserService.getUserByJobnumberOrEamil(forwardVo.getJobNumber()
                ,forwardVo.getEmail());
        if (user == null) {
            return R.error();
        } else {
            String url = null;
            if (forwardVo.getType() == 1){ //会议预约
                url = "192.168.18.204:8084/oa-order?userId="+user.getUserId();
            }else{
                url = "192.168.18.204:8084/oa-keep?userId="+user.getUserId();
            }
            return R.ok(200,"http://192.168.18.202/#/oa-order?userId=123");
        }



    }
}
