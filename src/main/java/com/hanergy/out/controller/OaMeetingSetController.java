package com.hanergy.out.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hanergy.out.entity.*;
import com.hanergy.out.service.*;
import com.hanergy.out.utils.DateUtils;
import com.hanergy.out.utils.IdentityService;
import com.hanergy.out.utils.ListUtils;
import com.hanergy.out.utils.R;
import com.hanergy.out.vo.OaMeetingSetVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会议室设置
19年5月9日新增 前端控制器
 * </p>
 *
 * @author Du Ronghong
 * @since 2019-05-13
 */
@RestController
@RequestMapping("/v1/oaMeetingSet")
public class OaMeetingSetController {

    Logger log = LoggerFactory.getLogger(OaMeetingController.class);

    @Autowired
    private IOaMeetingSetService oaMeetingSetService;       //会议室基本信息表

    @Autowired
    private IdentityService identityService;

    @Autowired
    private IOaMeetingSetPowerService oaMeetingSetPowerService;     // 会议权限表

    @Autowired
    private IDocumentService documentService;           //文件上传表

    @Autowired
    private ISysUserService sysUserService;             //用户表

    @Autowired
    private ISysDeptService sysDeptService;             //部门表



    @ApiOperation(value="保存/修改会议室",notes="id为null表示新增会议室信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="oaMeetingSet",value="会议室数据",paramType="query")
    })
    @PostMapping("/saveOaMeeting")
    public R saveOaMeeting(@RequestBody OaMeetingSetVo param){
        int result = 0;
        String id = null;
        OaMeetingSet oaMeetingSet = new OaMeetingSet(param);
        if (param.getId() == null){
            // 从redis获取分布式主键
            id = identityService.buildOneId().toString();
            log.info("redis分布式主键id = "+id);
            oaMeetingSet.setId(id);
            oaMeetingSet.setType(1);
            // 丰富创建时间
            oaMeetingSet.setCreDate(DateUtils.dateToString(new Date(),DateUtils.DATE_PATTERN));
            result = oaMeetingSetService.saveOaMeeting(oaMeetingSet);
        }else {
            id = param.getId();
            oaMeetingSet.setId(id);
            oaMeetingSet.setType(param.getType());
            // oaMeetingSet.setCreDate(param.getCreDate());
            result = oaMeetingSetService.updateOaMeeting(oaMeetingSet);
            // 删除原有人员信息
            oaMeetingSetPowerService.delOaPowerBySetId(param.getId());

        }
        //保存附件信息
        if (param.getDocument() != null && param.getType() != null && param.getType() != 2) {
            // 删除原始会议图片
            if (param.getId() != null){
                documentService.delDocumentByServiceId(Long.parseLong(param.getId()));
            }
            Document document = param.getDocument();
            document.setServiceId(Long.valueOf(id));
            document.setId(identityService.buildOneId());
            document.setCreatetime(new Date());
            document.setUploadUserName(param.getUserName());
            document.setUploadUserId(param.getUserId());
            documentService.saveDocument(document);
        }
        // 保存权限人员、部门信息
        savePowerData(param,id);
        if (result > 0){
            return R.ok();
        }else {
            return R.error("操作失败");
        }
    }

    // 保存人员、部门和会议室关联信息
    public void savePowerData(OaMeetingSetVo param,String id){
        if (param.getUsers() != null){
            for (int i = 0; i < param.getUsers().size(); i++) {
                OaMeetingSetPower power = new OaMeetingSetPower();
                power.setId(identityService.buildOneId());
                power.setOaMeetingSetId(id);
                power.setUserId(param.getUsers().get(i));
                oaMeetingSetPowerService.saveOaPower(power);
            }
        }
        if (param.getDepts() != null){
            for (int i = 0; i < param.getDepts().size(); i++) {
                OaMeetingSetPower power = new OaMeetingSetPower();
                power.setId(identityService.buildOneId());
                power.setOaMeetingSetId(id);
                power.setDeptId(param.getDepts().get(i).toString());
                oaMeetingSetPowerService.saveOaPower(power);
            }
        }
    }

    @GetMapping("/delOaMeeting")
    public R delOaMeeting(@RequestParam("id")String id){
        int result = oaMeetingSetService.delOaMeeting(id);
        oaMeetingSetPowerService.delOaPowerBySetId(id);
        if (result > 0){
            return R.ok();
        }else {
            return R.error("会议删除失败");
        }
    }

    @PostMapping("/updateOaMeeting")
    public R updateOaMeeting(@RequestBody OaMeetingSetVo param){
        OaMeetingSet set = new OaMeetingSet(param);
        set.setId(param.getId());
        int result = oaMeetingSetService.updateOaMeeting(set);
        if (param.getDocument() != null){
            // 删除原始会议图片
            documentService.delDocumentByServiceId(Long.parseLong(param.getId()));
            //保存新传入的图片
            Document document = param.getDocument();
            document.setServiceId(Long.valueOf(param.getId()));
            document.setId(identityService.buildOneId());
            document.setCreatetime(new Date());
            documentService.saveDocument(document);
        }
        // 删除原有人员信息
        oaMeetingSetPowerService.delOaPowerBySetId(param.getId());
        // 保存权限人员、部门信息
        savePowerData(param,param.getId());
        if (result > 0){
            return R.ok();
        }else {
            return R.error("会议更新失败");
        }
    }

    @ApiOperation(value="会议室列表",notes="获取当前用户有权限的会议列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId",value="用户id",required=true,paramType="query"),
            @ApiImplicitParam(name="type",value="1/3:实体会议室 2：虚拟会议室",required=true,paramType="query")
    })
    @GetMapping("/getOaMeeting")
    public R getOaMeeting(
            @RequestParam("type") Integer type,
            @RequestParam("userId")Long userId,
            @RequestParam("time")String time){
        if (type == 3){
            type = 1;
        }
        // 获取用户信息
        SysUser user = sysUserService.getUserById(userId);
        String deptTree = user.getDepartmentTree();
        // 获取用户权限会议列表
        List <OaMeetingSetVo> oaMeetingSets =
                oaMeetingSetService.getPowerPartMeeting(userId,type);

        List <OaMeetingSetVo> others = oaMeetingSetService.deptOrUserIsNotNull(type);
        //判断部门树是否包含部门字符串，包含代表有权限，添加到权限集合
        for (int i = 0; i < others.size(); i++) {
            String userPower = others.get(i).getUserPower();
            if (userPower != null && !userPower.contains(userId.toString())){
                String deptStr = others.get(i).getDeptPower();
                if (deptStr != null && !"".equals(deptStr)){
                    String[] depts = deptStr.split(",");
                    for (int j = 0; j < depts.length; j++) {
                        if (deptTree != null && deptTree.contains(depts[j])){
                            oaMeetingSets.add(others.get(i));
                            break;
                        }
                    }
                }
            }
        }
//        // 获取有权限的会议列表
//        List<OaMeetingSetVo> oaMeetingSets =
//                oaMeetingSetService.getPowerOaMeeting(userId,user.getDepartmentId(),type);
        return R.ok(200,oaMeetingSets);
    }


    @GetMapping("/getOneOaMeeting")
    public R getOneOaMeeting(@RequestParam("id") String id){
        OaMeetingSetVo set = oaMeetingSetService.getOneOaMeeting(id);
        // 用户信息
        if (set != null) {
            if (set.getUserPower() != null && set.getUserPower().length() > 0) {
                String[] userStr = set.getUserPower().split(",");
                List<Long> userList = ListUtils.stringToLongList(userStr);
                List<SysUser> users = sysUserService.getsysUserByIds(userList);
                set.setUserList(users);
                set.setUsers(userList);
            }
            if (set.getDeptPower() != null && set.getDeptPower().length() > 0) {
                String[] deptStr = set.getDeptPower().split(",");
                List<String> deptList = ListUtils.stringToList(deptStr);
                List<SysDept> depts = sysDeptService.getDeptByIds(deptList);
                set.setDeptList(depts);
                set.setDepts(ListUtils.stringToStringList(deptStr));
            }
        }
        return R.ok(200,set);
    }

    @ApiOperation(value="会议室列表",notes="会议室管理界面所有会议列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNumber",value="页码",required=true,paramType="query"),
            @ApiImplicitParam(name="pageSize",value="条数",required=true,paramType="query")
    })
    // 添加部门列表信息，员工列表信息
    @GetMapping("/allOaMeeting")
    public R allOaMeeting(
            @RequestParam("pageNumber")Integer pageNumber,
            @RequestParam("pageSize")Integer pageSize){
        IPage<OaMeetingSetVo> page = oaMeetingSetService.getAllOaMeeting(pageNumber,pageSize);
        if (page != null && page.getRecords() != null){
            List<OaMeetingSetVo> oaMeetingSets = page.getRecords();
            for (int i = 0; i < oaMeetingSets.size(); i++) {
                // 用户信息
                if (oaMeetingSets.get(i).getUserPower() != null) {
                    String[] userStr = oaMeetingSets.get(i).getUserPower().split(",");
                    List<Long> userList = ListUtils.stringToLongList(userStr);
                    if (userList != null && userList.size() > 0){
                        List<SysUser> users = sysUserService.getsysUserByIds(userList);
                        oaMeetingSets.get(i).setUserList(users);
                        oaMeetingSets.get(i).setUsers(userList);
                    }
                }
                // 部门信息
                if (oaMeetingSets.get(i).getDeptPower() != null){
                    String[] deptStr = oaMeetingSets.get(i).getDeptPower().split(",");
                    List<String> deptList = ListUtils.stringToList(deptStr);
                    List<SysDept> depts = sysDeptService.getDeptByIds(deptList);
                    oaMeetingSets.get(i).setDeptList(depts);
                    oaMeetingSets.get(i).setDepts(ListUtils.stringToStringList(deptStr));
                }
                //丰富附件信息
                if (oaMeetingSets.get(i).getType() == 1){
                    List<Document> documents = documentService.getDocumentByServiceId(Long.parseLong(oaMeetingSets.get(i).getId()));
                    if (documents != null && documents.size() > 0){
                        oaMeetingSets.get(i).setDocument(documents.get(0));
                        oaMeetingSets.get(i).setImage(documents.get(0).getUrl());
                    }
                }
                // 设置审批人信息
                if (oaMeetingSets.get(i).getApproval() != null) {
                    String[] approvalStr = oaMeetingSets.get(i).getApproval().split(",");
                    List<Long> approvalList = ListUtils.stringToLongList(approvalStr);
                    if (approvalList != null && approvalList.size() > 0){
                        List<SysUser> users = sysUserService.getsysUserByIds(approvalList);
                        oaMeetingSets.get(i).setApprovalList(users);
                        oaMeetingSets.get(i).setApprovals(approvalList);
                    }
                }
            }
        }
        return R.ok(200,page);
    }
}
