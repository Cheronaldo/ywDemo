package com.cherry.util;

import com.cherry.vo.ResultVO;

/**
 * 拼装返回对象VO
 * Created by Administrator on 2017/11/07.
 */
public class ResultVOUtil {

    /**
     * 成功 且有返回数据对象
     * @param object
     * @return
     */
    public static ResultVO success(String msg, Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg(msg);
        return resultVO;
    }

    /**
     * 成功 但无返回数据对象
     * @return
     */
    public static ResultVO success(String msg){return success(msg,null);}




    /**
     * 返回失败结果 无返回结果
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO error(Integer code, String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
