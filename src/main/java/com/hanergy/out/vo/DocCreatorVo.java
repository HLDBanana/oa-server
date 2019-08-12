package com.hanergy.out.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName DocCreatorVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/6/17 11:39)
 */
public class DocCreatorVo {
    // 员工编号
    @JSONField(name = "PersonNo")
    private String PersonNo;

    public  DocCreatorVo(){}

    public DocCreatorVo(String PersonNo){
        this.PersonNo = PersonNo;
    }

    public String getPersonNo() {
        return PersonNo;
    }

    public void setPersonNo(String personNo) {
        PersonNo = personNo;
    }
}
