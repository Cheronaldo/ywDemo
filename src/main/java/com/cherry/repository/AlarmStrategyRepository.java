package com.cherry.repository;

import com.cherry.dataobject.AlarmStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户报警策略表DAO层
 * Created by Administrator on 2017/11/29.
 */
public interface AlarmStrategyRepository extends JpaRepository<AlarmStrategy,String>{
}
