package cn.ffcs.common.gol.service;


import cn.ffcs.common.gol.entity.ControllerAccessLog;
import cn.ffcs.common.gol.repository.AccessLogRepository;
import cn.ffcs.common.gol.util.MethodUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wongs.common.persistence.jpa.service.BaseService;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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
 * @ClassName TbUserService
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/19 14:43
 * @Version 1.0.0
*/
@Service(value="accessLogService")
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class AccessLogService extends BaseService<ControllerAccessLog, Long> {

	private AccessLogRepository accessLogRepository;


	public Page<ControllerAccessLog> findCriteria(ControllerAccessLog controllerAccessLog){
		return findCriteria(15,controllerAccessLog);
	}

	public Page<ControllerAccessLog> findCriteria(int size, ControllerAccessLog controllerAccessLog){
		return findCriteria(0,size,controllerAccessLog);
	}

	public Page<ControllerAccessLog> findCriteria(int page, int size, ControllerAccessLog controllerAccessLog) {
		size=size==0?10:size;
		// TODO Auto-generated method stub
		Pageable pageable = new PageRequest(page, size);
		try {
			return accessLogRepository.findAll((Specification<ControllerAccessLog>) (root, query, cb) -> {
				List<Predicate> lp = new ArrayList<>();
				MethodUtil.getFieldValue(controllerAccessLog,root,cb,lp);
				Predicate[] pe = new Predicate[lp.size()];
				query.where(cb.and(lp.toArray(pe)));
				return query.getRestriction();
			}, pageable);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Autowired
	@Qualifier("accessLogRepository")
	@Override
	public void setJpaRepository(JpaRepository<ControllerAccessLog, Long> jpaRepository) {
		// TODO Auto-generated method stub
		this.jpaRepository=jpaRepository;
		this.accessLogRepository =(AccessLogRepository)jpaRepository;
	}

}
