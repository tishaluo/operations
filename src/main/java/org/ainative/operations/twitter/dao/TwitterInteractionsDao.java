package org.ainative.operations.twitter.dao;

import org.ainative.operations.twitter.entity.TwitterInteractions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (TwitterInteractions)表数据库访问层
 *
 * @author makejava
 * @since 2025-08-21 15:55:22
 */
@Repository
public interface TwitterInteractionsDao extends JpaRepository<TwitterInteractions, Long> {


    List<TwitterInteractions> findAllByExecuteId(String executeId);


}

