package org.ainative.operations.twitter.dao;

import org.ainative.operations.twitter.entity.TwitterConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TwitterConfigDao extends JpaRepository<TwitterConfig, UUID> {
}