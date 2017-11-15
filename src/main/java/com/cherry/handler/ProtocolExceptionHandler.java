package com.cherry.handler;

import com.cherry.exception.ProtocolException;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**协议异常捕获
 * Created by Administrator on 2017/11/15.
 */
@ControllerAdvice
public class ProtocolExceptionHandler {

    /**
     * 捕获协议类异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ProtocolException.class)
    @ResponseBody
    public ResultVO handlerProtocolException(ProtocolException e){
        return ResultVOUtil.error(1,e.getMessage());
    }

}
