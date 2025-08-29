package org.ainative.operations.twitter.service;

import org.ainative.operations.twitter.entity.TwitterInteractions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (TwitterInteractions)表服务接口
 *
 * @author makejava
 * @since 2025-08-21 15:55:23
 */
@Service
public interface TwitterInteractionsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<TwitterInteractions> queryById(String id);



}
