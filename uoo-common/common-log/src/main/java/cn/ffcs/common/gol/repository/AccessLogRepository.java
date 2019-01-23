package cn.ffcs.common.gol.repository;


import cn.ffcs.common.gol.entity.ControllerAccessLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.wongs.common.persistence.jpa.repository.BaseRepository;

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
 * @ClassName AccessLogRepository
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/19 15:20
 * @Version 1.0.0
*/
public interface AccessLogRepository extends BaseRepository<ControllerAccessLog, Long>,JpaSpecificationExecutor<ControllerAccessLog> {


}
