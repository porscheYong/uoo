package cn.ffcs.uoo.core.organization.Api.service;/**
 * @description:
 * @author: ffcs-gzb
 * @date: 2018-11-30
 */

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>
 *
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/11/30
 */
@FeignClient(value = "business-public")
public interface ExpandovalueService {

}
