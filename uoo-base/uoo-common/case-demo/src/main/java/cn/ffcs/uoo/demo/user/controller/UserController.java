package cn.ffcs.uoo.demo.user.controller;


import cn.ffcs.uoo.base.common.annotion.UooLog;
import cn.ffcs.uoo.demo.user.entity.User;
import cn.ffcs.uoo.demo.user.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WCNGS@QQ.COM
 * @since 2018-08-29
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 分页 PAGE
     */
    @UooLog(value = "测试分页", key = "test")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Page<User> test() {
        return userService.selectPage(new Page<User>(0, 12));
    }


    /**
     * 分页 PAGE
     */
    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable(value = "id") String id) {
        return userService.selectById(id);
    }


    /**
     * AR 部分测试
     */
    @GetMapping("/test1")
    public Page<User> test1() {
        User user = new User();
        System.err.println("删除所有：" + user.delete(null));

        user.setAge(340);
        user.setName("Wongs");
        user.insert();
        System.err.println("查询插入结果：" + user.selectById().toString());
        System.err.println("更新：" + user.updateById());
        return user.selectPage(new Page<User>(0, 12), null);
    }


    /**
     * 增删改查 CRUD
     */
    @GetMapping("/test2")
    public User test2() {
        User user = new User();
        user.setAge(8);
        user.setName("Li Ke Qiang");
        System.err.println("删除一条数据：" + userService.deleteById(1L));
        System.err.println("插入一条数据：" + userService.insert(user));
        User user2 = new User();
        user.setAge(12);
        user.setName("Ping");
        boolean result = userService.insert(user);
        // 自动回写的ID
        Long id = user.getId();
        System.err.println("插入一条数据：" + result + ", 插入信息：" + user.toString());
        System.err.println("查询：" + userService.selectById(id).toString());
        Page<User> userListPage = userService.selectPage(new Page<User>(1, 5), new EntityWrapper<>(new User()));
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());
        return userService.selectById(1L);
    }


//    @GetMapping("testSelect")
//    public Object testSelect() {
//        Integer start = 0;
//        Integer length =10;
//        User param = new User();
//        //param.setNickname("guangqing2");
//        Integer pageNo=getPageNo(start,length);
//        Page<User> page =new Page<User>(pageNo,length);
//        EntityWrapper<User> ew = new EntityWrapper<User>();
//        ew.setEntity(param);
//        ew.where("password={0}","123456")
//                .like("nickname","guangqing")
//                .ge("create_time","2017-09-21 15:50:00");
//        userService.selectPage(page, ew);
//        DatatablesJSON<User> resJson= new DatatablesJSON<>();
//        //resJson.setDraw(draw++);
//        resJson.setRecordsTotal(page.getTotal());
//        resJson.setRecordsFiltered(page.getTotal());
//        resJson.setData(page.getRecords());
//        return resJson;
//    }
}

