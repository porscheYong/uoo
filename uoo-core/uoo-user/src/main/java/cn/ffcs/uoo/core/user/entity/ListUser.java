package cn.ffcs.uoo.core.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ListUser {

    private String userId;

    private String psnName;

    private Date createDate;
}
