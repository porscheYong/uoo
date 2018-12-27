package cn.ffcs.uoo.common.cache.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  ┏┓　　　┏┓
 *┏┛┻━━━┛┻┓
 *┃　　　　　　　┃ 　
 *┃　　　━　　　┃
 *┃　┳┛　┗┳　┃
 *┃　　　　　　　┃
 *┃　　　┻　　　┃
 *┃　　　　　　　┃
 *┗━┓　　　┏━┛
 *　　┃　　　┃神兽保佑
 *　　┃　　　┃代码无BUG！
 *　　┃　　　┗━━━┓
 *　　┃　　　　　　　┣┓
 *　　┃　　　　　　　┏┛
 *　　┗┓┓┏━┳┓┏┛
 *　　　┃┫┫　┃┫┫
 *　　　┗┻┛　┗┻┛
 * @ClassName RedisController
 * @Description  提供对LIST、SET灯缓存的写入服务项，请求均为POST，涉及的时间均以秒为单位
 * @author WCNGS@QQ.COM
 * @date 2018/12/13 15:36
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/cache/write")
@Api("Redis写的操作")
public class RedisWriteController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**指定缓存失效时间
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:37
     * @param key   键
     * @param time 时间 （秒）
     * @return boolean  true 成功 false失败
     * @throws
     * @since
     */
    @ApiOperation(value = "根据KEY指定缓存失效时间", notes = "指定缓存失效时间，单位是秒,成功True,失败False")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "time", value = "时间 秒", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/expire")
    public boolean expire(String key, long time) {
        if(time>0){
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**删除缓存
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:40
     * @param key   可以传一个值 或多个
     * @return void
     * @throws
     * @since
     */
    @ApiOperation(value = "根据KEY删除缓存", notes = "KEY参数为数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/del")
    public void del(String... key){
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**普通缓存放入
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:43
     * @param key
     * @param value
     * @return boolean
     * @throws
     * @since
     */
    @ApiOperation(value = "普通缓存放入", notes = "存储键值对，成功True,失败False")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "内容", required = true, dataType = "java.lang.Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/set")
    public boolean set(String key, Object value){
        return set(key,value,0);
    }

    /**普通缓存放入并设置时间
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:43
     * @param key
     * @param value
     * @param time 时间 （秒）
     * @return boolean  true 成功 false失败
     * @throws
     * @since
     */
    @ApiOperation(value = "普通缓存放入，并设置时间", notes = "设置的时间单位秒，可是大于或者小于0，成功True,失败False，返回：true 成功 false失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "内容", required = true, dataType = "Object", paramType = "path") ,
            @ApiImplicitParam(name = "time", value = "时间 秒", required = true, dataType = "long", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/setAndTime")
    public boolean set(String key, Object value, long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value,time);
            } else{
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**递增
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:50
     * @param key
     * @param delta 要增加几(大于0)
     * @return long
     * @throws
     * @since
     */
    @ApiOperation(value = "递增KEY", notes = "delta要增加几(大于0)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "delta", value = "要增加几(大于0)", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/incr")
    public long incr(String key, long delta){
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**递减
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:50
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return long
     * @throws
     * @since
     */
    @ApiOperation(value = "递减KEY", notes = "delta要减少几(小于0)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "delta", value = "要减少几(小于0)", required = true, dataType = "long", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/decr")
    public long decr(String key, long delta){
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }


    /**向一张hash表中放入数据,如果不存在将创建
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:53
     * @param key
     * @param item  项
     * @param value
     * @return boolean  true 成功 false失败
     * @throws
     * @since
     */
    @ApiOperation(value = "递减KEY", notes = "delta要减少几(小于0)，返回：true 成功 false失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "item", value = "项", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "内容", required = true, dataType = "Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/hset")
    public boolean hset(String key, String item, Object value){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**删除hash表中的值
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:54
     * @param key   不能为null
     * @param item 可以使多个 不能为null
     * @return void
     * @throws
     * @since
     */
    @ApiOperation(value = "删除hash表中的值", notes = "删除hash表中的值，其中item数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "item", value = "项", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/hdel")
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key, item);
    }


    /**hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:04
     * @param key
     * @param item  项
     * @param by   要增加几(大于0)
     * @return double
     * @throws
     * @since
     */
    @ApiOperation(value = "hash递增", notes = "如果不存在,就会创建一个 并把新增后的值返回")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "item", value = "项", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "by", value = "要增加几 大于0", required = true, dataType = "double", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/hincr")
    public double hincr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**hash递减
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:04
     * @param key
     * @param item  项
     * @param by   要减少记(小于0)
     * @return double
     * @throws
     * @since
     */
    @ApiOperation(value = "hash递减", notes = "如果不存在,就会创建一个 并把新增后的值返回")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "item", value = "项", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "by", value = "要减少几 小于0", required = true, dataType = "double", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/hdecr")
    public double hdecr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }


    /**  将数据放入set缓存
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:05
     * @param key   可以是多个
     * @param values
     * @return long 成功个数
     * @throws
     * @since
     */
    @ApiOperation(value = "数据set缓存", notes = "数据放入set缓存，内容格式为数组，返回的是成功的数量（Long）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "values", value = "内容", required = true, dataType = "Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/sSet")
    public long sSet(String key, Object... values){
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 将set数据放入缓存
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:08
     * @param key
     * @param time  时间(秒)
     * @param values    值 可以是多个
     * @return long 成功个数
     * @throws
     * @since
     */
    @ApiOperation(value = "数据set缓存", notes = "数据放入set缓存，values 内容格式为数组，时间单位为秒")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "time", value = "时间 秒", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "values", value = "内容", required = true, dataType = "Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/sSetAndTime")
    public long sSetAndTime(String key, long time, Object... values){
        Long count = null;
        try {
            count = sSet(key,values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 移除值为value的
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:12
     * @param key   键
     * @param values    值 可以是多个
     * @return long 移除的个数
     * @throws
     * @since
     */
    @ApiOperation(value = "根据KEY VALUE移除", notes = "根据KEY VALUE移除，返回：移除的个数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "values", value = "内容", required = true, dataType = "Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/setRemove")
    public long setRemove(String key, Object... values){
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**将list放入缓存
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:14
     * @param key   键
     * @param value  值
     * @return boolean 成功True，失败False
     * @throws
     * @since
     */
    @ApiOperation(value = "将list放入缓存", notes = "lSet，返回：成功True，失败False")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "内容", required = true, dataType = "Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/lSet")
    public boolean lSet(String key, Object value){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**将list放入缓存
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:14
     * @param key   键
     * @param value  值
     * @param time  时间(秒)
     * @return boolean 成功True，失败False
     * @throws
     * @since
     */
    @ApiOperation(value = "将list放入缓存", notes = "将list放入缓存;时间 秒;返回：成功True，失败False")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "内容", required = true, dataType = "Object", paramType = "path"),
            @ApiImplicitParam(name = "time", value = "时间 秒", required = true, dataType = "long", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/lSetAndTime")
    public boolean lSet(String key, Object value, long time){
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if(time>0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**将list放入缓存
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:14
     * @param key   键
     * @param value  值
     * @return boolean 成功True，失败False
     * @throws
     * @since
     */
    @ApiOperation(value = "将list放入缓存", notes = "将list放入缓存;时间 秒;返回：成功True，失败False")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "内容", required = true, dataType = "List", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/lSetByList")
    public boolean lSet(String key, List<Object> value){
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**将list放入缓存
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:14
     * @param key   键
     * @param value  值
     * @param time  时间(秒)
     * @return boolean 成功True，失败False
     * @throws
     * @since
     */
    @ApiOperation(value = "将list放入缓存", notes = "将list放入缓存;时间 秒;返回：成功True，失败False")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "内容", required = true, dataType = "List", paramType = "path"),
            @ApiImplicitParam(name = "time", value = "时间 秒", required = true, dataType = "long", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/lSetByList4Time")
    public boolean lSet(String key, List<Object> value, long time){
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if(time>0){
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 根据索引修改list中的某条数据
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:22
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return boolean
     * @throws
     * @since
     */
    @ApiOperation(value = "根据索引修改list中的某条数据", notes = "根据索引修改list中的某条数据；返回：成功True，失败False")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "index", value = "索引", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/lUpdateIndex")
    public boolean lUpdateIndex(String key, long index, Object value) {

        try {
            redisTemplate.opsForList().set(key,index,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 移除N个值为value
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/27 10:24
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return long 移除的个数
     * @throws
     * @since
     */
    @ApiOperation(value = "移除N个值为value", notes = "移除N个值为value；返回：移除的个数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "count", value = "移除多少个", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "值", required = true, dataType = "Object", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST,value = "/lRemove")
    public long lRemove(String key, long count, Object value){
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
