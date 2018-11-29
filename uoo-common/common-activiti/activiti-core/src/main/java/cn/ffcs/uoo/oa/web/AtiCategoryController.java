package cn.ffcs.uoo.oa.web;


import cn.ffcs.uoo.oa.utils.HttpClientUtils;
import cn.ffcs.uoo.oa.utils.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.ffcs.uoo.base.controller.BaseController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuxiaodong
 * @since 2018-09-27
 */
@RestController
@RequestMapping("/atiCategory")
public class AtiCategoryController extends BaseController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseResult test(String accout, String passwd) throws Exception {
        String url = "http://localhost:9100/system/sysUserLogin";

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("accout", accout));
        params.add(new BasicNameValuePair("passwd", passwd));
        JSONObject jsonObj = HttpClientUtils.postHttp(url, params);
        if (null != jsonObj) {
            return JSON.parseObject(jsonObj.toJSONString(),
                    ResponseResult.class);
        }
        return null;
    }
}

