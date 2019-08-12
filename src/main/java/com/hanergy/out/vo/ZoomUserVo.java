package com.hanergy.out.vo;

/**
 * @ClassName ZoomUserVo
 * @Description
 * @Auto HANLIDONG
 * @Date 2019/5/31 13:52)
 */
public class ZoomUserVo {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private int type;

    public ZoomUserVo(){}
    public ZoomUserVo(String id, String firstName, String lastName, String email, int type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
