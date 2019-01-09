package cn.ffcs.uoo.web.maindata.busipublic.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import cn.ffcs.uoo.web.maindata.busipublic.resource.client.ModifyHistoryClient;
import cn.ffcs.uoo.web.maindata.busipublic.resource.dto.ModifyHistory;
import cn.ffcs.uoo.web.maindata.busipublic.vo.ResponseResult;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/public/modifyHistory")
public class ModifyHistoryController {
    @Autowired
    ModifyHistoryClient mdfHisClient;
    @ApiOperation(value = "分页", notes = "分页")
    @RequestMapping(value = "/listByRecord", method = RequestMethod.GET)
    public ResponseResult<Page<ModifyHistory>> listByRecord(@RequestParam(value = "pageNo") Integer pageNo, @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value="tableName")String tableName,@RequestParam(value="recordId")String recordId) {
        return mdfHisClient.listByRecord(pageNo, pageSize, tableName, recordId);
    }
}
