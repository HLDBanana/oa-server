package com.hanergy.out.vo;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName OaForwardVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/6/24 11:21)
 */
public class OaForwardVo {
    private String jobNumber;
    private String email;
    private Integer type;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
