package cn.ffcs.common.gol.service;


import cn.ffcs.common.gol.entity.AccessLog;
import cn.ffcs.common.gol.repository.AccessLogRepository;
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
 * @ClassName TbUserService
 * @Description 
 * @author WCNGS@QQ.COM
 * @date 2019/1/19 14:43
 * @Version 1.0.0
*/
@Service(value="accessLogService")
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class AccessLogService extends BaseService<AccessLog, Long> {

	private AccessLogRepository accessLogRepository;


//	public Page<AccessLog> findCriteria(AccessLog controllerAccessLog){
//		return findCriteria(15,controllerAccessLog);
//	}
//
//	public Page<AccessLog> findCriteria(int size, AccessLog controllerAccessLog){
//		return findCriteria(0,size,controllerAccessLog);
//	}

//	public Page<AccessLog> findCriteria(int page, int size, AccessLog controllerAccessLog) {
//		size=size==0?10:size;
//		// TODO Auto-generated method stub
//		Pageable pageable = new PageRequest(page, size);
//		try {
//			return accessLogRepository.findAll((Specification<AccessLog>) (root, query, cb) -> {
//				List<Predicate> lp = new ArrayList<>();
//				MethodUtil.getFieldValue(controllerAccessLog,root,cb,lp);
//				Predicate[] pe = new Predicate[lp.size()];
//				query.where(cb.and(lp.toArray(pe)));
//				return query.getRestriction();
//			}, pageable);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	@Autowired
	@Qualifier("accessLogRepository")
	@Override
	public void setJpaRepository(JpaRepository<AccessLog, Long> jpaRepository) {
		// TODO Auto-generated method stub
		this.jpaRepository=jpaRepository;
		this.accessLogRepository =(AccessLogRepository)jpaRepository;
	}

}
