package org.ainative.operations.twitter.service.impl;

import org.ainative.operations.twitter.entity.TwitterInteractions;
import org.ainative.operations.twitter.dao.TwitterInteractionsDao;
import org.ainative.operations.twitter.service.TwitterInteractionsService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * (TwitterInteractions)表服务实现类
 *
 * @author makejava
 * @since 2025-08-21 15:55:24
 */
@Service("twitterInteractionsService")
public class TwitterInteractionsServiceImpl implements TwitterInteractionsService {

    private final TwitterInteractionsDao twitterInteractionsDao;

    public TwitterInteractionsServiceImpl(TwitterInteractionsDao twitterInteractionsDao) {
        this.twitterInteractionsDao = twitterInteractionsDao;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public List<TwitterInteractions> queryById(String id) {
        return this.twitterInteractionsDao.findAllByExecuteId(id);
    }


}
