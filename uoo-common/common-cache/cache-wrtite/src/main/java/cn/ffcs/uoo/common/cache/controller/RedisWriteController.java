package cn.ffcs.uoo.common.cache.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2018/12/13 15:36
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/cache/write")
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
    @ApiOperation(value = "指定缓存失效时间", notes = "指定缓存失效时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "time", value = "time", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST)
    public boolean expire(String key, long time) {
        if(time>0){
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**根据key 获取过期时间
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:37
     * @param key   键
     * @return long
     * @throws
     * @since
     */
    @ApiOperation(value = "指定缓存失效时间", notes = "指定缓存失效时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(method = RequestMethod.POST)
    public long getExpire(String key) {
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
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
     * @param key
     * @param delta 要减少几(小于0)
     * @return long
     * @throws
     * @since
     */
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
     * @param item 可以使多个 不能为null
     * @return void
     * @throws
     * @since
     */
    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key, item);
    }


    /**判断hash表中是否有该项的值
     * @author WCNGS@QQ.COM
     * @See
     * @date 2018/12/13 15:55
     * @param key
     * @param item 项 不能为null
     * @return boolean  true 存在 false不存在
     * @throws
     * @since
     */
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }
}
