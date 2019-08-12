package com.hanergy.out.controller;


import com.hanergy.out.entity.SysMenu;
import com.hanergy.out.entity.SysRole;
import com.hanergy.out.entity.SysUser;
import com.hanergy.out.service.ISysMenuService;
import com.hanergy.out.service.ISysRoleService;
import com.hanergy.out.service.ISysUserService;
import com.hanergy.out.service.impl.SysUserServiceImpl;
import com.hanergy.out.utils.Base64Encode;
import com.hanergy.out.utils.R;
import com.hanergy.out.vo.MenuPowerVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
@RestController
@RequestMapping("/v1/sysUser")
public class SysUserController {

    Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation(value="获取用户信息",notes="根据用户id用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",required=true,paramType="query")
    })
    @GetMapping("/userInfo")
    public R getUserInfo(@RequestParam("userId")Long userId){
        SysUser sysUser = sysUserService.getUserById(userId);
        return R.ok(200,sysUser);
    }

    @ApiOperation(value="获取用户信息",notes="根据员工编号用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="jobNumber",value="员工编号",required=true,paramType="query")
    })
    @GetMapping("/userByJobNumber")
    public R getUserByJobNumber(@RequestParam("jobNumber")String jobNumber){
        try {
            byte[] number = Base64Encode.decode(jobNumber);
            //String isoString = new String(number,"ISO-8859-1");
            String personNum=new String(number,"UTF-8");

            SysUser sysUser = sysUserService.getUserByJobnumberOrEamil(personNum,null);
            if (sysUser == null){
                return R.error();
            }
            return R.ok(200,sysUser);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return R.error();
    }

    @ApiOperation(value="菜单权限判断",notes="根据员工编号查看用户是否有菜单权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name="jobNumber",value="员工编号",required=true,paramType="query"),
            @ApiImplicitParam(name="menuName",value="菜单名称",required=true,paramType="query"),
            @ApiImplicitParam(name="projectTag",value="项目编号",required=true,paramType="query")
    })
    @PostMapping("/menuPower")
    public R menuPower(@RequestBody MenuPowerVo menu){

        log.info("菜单权限判断"+menu.getJobNumber()+menu.getProjectTag()+menu.getMenuName());
        try {
            byte[] number = Base64Encode.decode(menu.getJobNumber());
            //String isoString = new String(number,"ISO-8859-1");
            String personNum=new String(number,"UTF-8");

            SysUser sysUser = sysUserService.getUserByJobnumberOrEamil(personNum,null);
            if (sysUser == null){
                return R.ok(200,null);
            }
            // 获取角色信息
            List<SysRole> sysRoles = sysRoleService.getRoleByJobnumberAndProject(
                    personNum,menu.getProjectTag());
            if (sysRoles != null && sysRoles.size() > 0) {
                for (int i = 0; i < sysRoles.size(); i++) {
                    // 获取用户菜单信息
                    SysMenu sysMenu = sysMenuService.getMenuByRoleIdAndMenuName(sysRoles.get(i).getRoleId(),menu.getMenuName());
                    if (sysMenu != null) {
                        return R.ok(200,sysUser);
                    }
                }
            }
            return R.ok(200,null);
        } catch (Exception e){
            e.printStackTrace();
        }

        return R.error();

    }

}
