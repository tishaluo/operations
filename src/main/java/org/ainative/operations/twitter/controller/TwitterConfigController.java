package org.ainative.operations.twitter.controller;

import org.ainative.operations.twitter.entity.TwitterConfig;
import org.ainative.operations.twitter.service.TwitterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/twitter/config")
public class TwitterConfigController {
    
    @Autowired
    private TwitterConfigService twitterConfigService;
    
    @GetMapping
    public ResponseEntity<List<TwitterConfig>> getAllConfigs() {
        List<TwitterConfig> configs = twitterConfigService.findAll();
        return new ResponseEntity<>(configs, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TwitterConfig> getConfigById(@PathVariable UUID id) {
        Optional<TwitterConfig> config = twitterConfigService.findById(id);
        if (config.isPresent()) {
            return new ResponseEntity<>(config.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    public ResponseEntity<TwitterConfig> createConfig(@RequestBody TwitterConfig twitterConfig) {
        TwitterConfig savedConfig = twitterConfigService.save(twitterConfig);
        return new ResponseEntity<>(savedConfig, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TwitterConfig> updateConfig(@PathVariable UUID id, 
                                                     @RequestBody TwitterConfig twitterConfig) {
        Optional<TwitterConfig> existingConfig = twitterConfigService.findById(id);
        if (existingConfig.isPresent()) {
            twitterConfig.setId(id);
            TwitterConfig updatedConfig = twitterConfigService.update(twitterConfig);
            return new ResponseEntity<>(updatedConfig, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable UUID id) {
        Optional<TwitterConfig> config = twitterConfigService.findById(id);
        if (config.isPresent()) {
            twitterConfigService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean> exist(@PathVariable UUID id) {
        return ResponseEntity.ok(twitterConfigService.exist(id));
    }
}