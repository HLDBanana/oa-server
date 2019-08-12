package com.hanergy.out.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hanergy.out.entity.MiddleProtection;
import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.IMiddleProtectionService;
import com.hanergy.out.service.ISysUserService;
import com.hanergy.out.utils.IdentityService;
import com.hanergy.out.utils.R;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 劳动保护  
2019年7月15日杨国栋创建 前端控制器
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-07-15
 */
@RestController
@RequestMapping("/v1/middleProtection")
public class MiddleProtectionController {

    @Autowired
    private IMiddleProtectionService middleProtectionService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ISysUserService sysUserService;


    @ApiOperation(value="保存/修改接口",notes="id为null表示新增，否则为修改")
    @ApiImplicitParam(name = "pro",value = "劳保数据",required = true,dataType = "MiddleProtection")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="pro",value="劳保数据",paramType="query")
//    })
    @PostMapping("/saveMiddleProtection")
    public R saveMiddleProtection(@RequestBody MiddleProtection pro){
        boolean success = true;
        if (pro.getId() != null) {
            SysUser sysUser = sysUserService.getUserById(pro.getUserId());
            pro.setUserName(sysUser.getName());
            success = middleProtectionService.updateById(pro);
        } else {
            Long id = identityService.buildOneId();
            pro.setId(id);
            SysUser sysUser = sysUserService.getUserById(pro.getUserId());
            pro.setUserName(sysUser.getName());
            success = middleProtectionService.save(pro);
        }
        if (success){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value="分页查询",notes="分页查询查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageSize",value="条数",paramType="query"),
            @ApiImplicitParam(name="current",value="页码",paramType="query")
    })
    @GetMapping("/getMiddleProtection")
    public R getMiddleProtection(
            @RequestParam("pageSize")Integer pageSize,
            @RequestParam("current")Integer current){
        //分页获取劳保数据
        IPage<MiddleProtection> proPage = middleProtectionService.getMiddleProtection(pageSize,current);

        return R.ok(200,proPage);
    }

    @ApiOperation(value="删除",notes="删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="数据id",paramType="query")
    })
    @GetMapping("/delMiddleProtection")
    public R delMiddleProtection(@RequestParam("id")Long id){
        middleProtectionService.removeById(id);
        return R.ok();
    }


}
