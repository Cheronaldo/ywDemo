package com.cherry.handler;

import com.cherry.enums.DeviceHandleEnum;
import com.cherry.exception.DeviceException;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备异常捕获（捕获的是 controller层向上抛出的异，不能在服务层抛）
 * Created by Administrator on 2017/11/11.
 */
@ControllerAdvice
public class DeviceExceptionHandler {

    /**
     * 捕获设备注册 时异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = DeviceException.class)
    @ResponseBody
    public ResultVO handlerDeviceException(DeviceException e){return ResultVOUtil.error(1,e.getMessage());}

}
