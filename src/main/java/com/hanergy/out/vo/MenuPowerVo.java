package com.hanergy.out.vo;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName MenuPowerVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019-6-26 17:44)
 */
public class MenuPowerVo {
    /*
     * 用户编号
     */
    private String jobNumber;
    /*
     * 菜单名称
     */
    private String menuName;
    /*
     * 项目编号
     */
    private String projectTag;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getProjectTag() {
        return projectTag;
    }

    public void setProjectTag(String projectTag) {
        this.projectTag = projectTag;
    }
}
