package com.hanergy.out.vo;

/**
 * @ClassName ZoomUserInfo
 * @Description zoom用户信息
 * @Auto HANLIDONG
 * @Date 2019/5/7 14:59)
 */
public class ZoomUserInfo {

    private String id;

    private String firstName;

    private String lastName;

    private Integer type;
    private Integer pmi;
    private String timezone;
    private Integer verified;
    private String dept;
    private String createAt;
    private String lastLoginTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPmi() {
        return pmi;
    }

    public void setPmi(Integer pmi) {
        this.pmi = pmi;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
