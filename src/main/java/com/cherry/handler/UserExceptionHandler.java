package com.cherry.handler;

import com.cherry.exception.UserException;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/11/07.
 */
@ControllerAdvice
public class UserExceptionHandler {

    /**
     * 捕获用户注册 时异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ResultVO handlerUserException(UserException e){
        return ResultVOUtil.error(1,e.getMessage());
    }

}
