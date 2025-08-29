package org.ainative.operations.twitter.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * (TwitterInteractions)实体类
 *
 * @author makejava
 * @since 2025-08-21 15:55:22
 */

@Data
@Entity
@Table(name = "twitter_interactions")
public class TwitterInteractions implements Serializable {

    @Serial
    private static final long serialVersionUID = -97693862152397018L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String executeId;

    private Integer replies;

    private String username;

    private String displayName;

    private LocalDateTime date;

    private String timeParsed;

    private String permanentUrl;

    private Integer likes;

    private String comment;

    private LocalDateTime createTime;

    private Long tweetId;

    private String text;

    private Integer retweets;

    private Boolean alreadyComment;

}

