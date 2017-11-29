package com.cherry.repository;

import com.cherry.dataobject.VisibleStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户可见策略表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface VisibleStrategyRepository extends JpaRepository<VisibleStrategy,String>{
}
