package com.cherry.converter;

import com.cherry.dataobject.UserInfo;
import com.cherry.vo.SiteUserInfoVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**UserInfo --> SiteUserInfoVO 转换器
 * Created by Administrator on 2018/01/04.
 */
public class UserInfo2SiteUserInfoVOConverter {

    public static SiteUserInfoVO convert(UserInfo userInfo){

        SiteUserInfoVO userInfoVO = new SiteUserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        return userInfoVO;

    }

    public static List<SiteUserInfoVO> convert(List<UserInfo> userInfoList){

        return userInfoList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

    }

}
