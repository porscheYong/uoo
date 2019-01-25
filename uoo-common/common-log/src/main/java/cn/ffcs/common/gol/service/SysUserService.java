package cn.ffcs.common.gol.service;


import cn.ffcs.common.gol.entity.AccessLog;
import cn.ffcs.common.gol.entity.SysUser;
import cn.ffcs.common.gol.repository.AccessLogRepository;
import cn.ffcs.common.gol.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.common.persistence.jpa.service.BaseService;

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
 * @ClassName SysUserService
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/19 14:43
 * @Version 1.0.0
*/
@Service(value="sysUserService")
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SysUserService extends BaseService<SysUser, Long> {

	private SysUserRepository sysUserRepository;

	@Autowired
	@Qualifier("sysUserRepository")
	@Override
	public void setJpaRepository(JpaRepository<SysUser, Long> jpaRepository) {
		// TODO Auto-generated method stub
		this.jpaRepository=jpaRepository;
		this.sysUserRepository =(SysUserRepository)jpaRepository;
	}

}
