package com.cherry.handler;

import com.cherry.exception.DataException;
import com.cherry.util.ResultVOUtil;
import com.cherry.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据处理 异常捕获
 * Created by Administrator on 2017/12/03.
 */
@ControllerAdvice
public class DataExceptionHandler {

    @ExceptionHandler(DataException.class)
    @ResponseBody
    public ResultVO handlerDataException(DataException e){return ResultVOUtil.error(1, e.getMessage());}

}
