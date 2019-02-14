package cn.ffcs.uoo.web.aspect;

import cn.ffcs.uoo.web.accesslog.ControllerAccessLog;
import cn.ffcs.uoo.web.maindata.common.system.dto.SysOperationLog;

public class LogCollections {
    private ControllerAccessLog controllerAccessLog;
    private SysOperationLog sysOperationLog;
    public ControllerAccessLog getControllerAccessLog() {
        return controllerAccessLog;
    }
    public void setControllerAccessLog(ControllerAccessLog controllerAccessLog) {
        this.controllerAccessLog = controllerAccessLog;
    }
    public SysOperationLog getSysOperationLog() {
        return sysOperationLog;
    }
    public void setSysOperationLog(SysOperationLog sysOperationLog) {
        this.sysOperationLog = sysOperationLog;
    }
    
}
