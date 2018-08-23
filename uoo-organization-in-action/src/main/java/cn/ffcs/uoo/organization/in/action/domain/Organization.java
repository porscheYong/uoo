package cn.ffcs.uoo.organization.in.action.domain;

import lombok.Data;

import java.util.Date;

/**
 * Organization DO
 *
 * @author Lin
 * @date 2018/08/23
 */
@Data
public class Organization {

    private int id;
    private Date gmtCreate;
    private Date gmtModified;
    private String name;

}
