package cn.ffcs.uoo.core.permission.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 描述将权限授权给不同的对象，包括系统用户、系统岗位、角色；
一个系统用户也可以拥有多个私有的权限，一个权限可以分配给多个系统用户。
一个系统岗位除了拥有角色所带的权限，也可以拥有私有的多个权限，一个权限可以分配给多个系统岗位。
一个角色可以包含多个权限，一个权限也可以分配给多个角色。 前端控制器
 * </p>
 *
 * @author zengxsh
 * @since 2018-11-08
 */
@Controller
@RequestMapping("/permission/privGrant")
public class PrivGrantController {

}

