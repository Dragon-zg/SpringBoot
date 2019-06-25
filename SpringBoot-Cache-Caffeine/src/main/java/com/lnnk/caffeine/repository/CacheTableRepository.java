package com.lnnk.caffeine.repository;

import com.lnnk.caffeine.entity.CacheTable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangqiang
 * @date 2019/6/25 14:09
 **/
public interface CacheTableRepository extends JpaRepository<CacheTable, Long> {
}
