package org.ainative.operations.twitter.service;

import org.ainative.operations.twitter.entity.TwitterConfig;
import org.ainative.operations.twitter.dao.TwitterConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TwitterConfigService {
    
    @Autowired
    private TwitterConfigDao twitterConfigDao;
    
    public List<TwitterConfig> findAll() {
        return twitterConfigDao.findAll();
    }
    
    public Optional<TwitterConfig> findById(UUID id) {
        return twitterConfigDao.findById(id);
    }
    
    public TwitterConfig save(TwitterConfig twitterConfig) {
        return twitterConfigDao.save(twitterConfig);
    }
    
    public void deleteById(UUID id) {
        twitterConfigDao.deleteById(id);
    }
    
    public TwitterConfig update(TwitterConfig twitterConfig) {
        return twitterConfigDao.save(twitterConfig);
    }

    public boolean exist(UUID id) {
        return twitterConfigDao.existsById(id);
    }
}