package com.cooksys.assessment1.group3.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    private Timestamp posted = Timestamp.valueOf(LocalDateTime.now());

    private boolean deleted = false;

    private String content;

    @ManyToOne
    @JoinColumn(name = "tweet_reply_id")
    private Tweet inReplyTo;

    @OneToMany(mappedBy = "inReplyTo")
    private List<Tweet> replies = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tweet_repost_id")
    private Tweet repostOf;

    @OneToMany(mappedBy = "repostOf")
    private List<Tweet> reposts = new ArrayList<>();

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinTable
    private List<User> likedBy = new ArrayList<>();

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinTable
    private List<User> tweetMentions = new ArrayList<>();

    @ManyToMany(targetEntity = Hashtag.class, cascade = CascadeType.MERGE)
    @JoinTable
    private List<Hashtag> hashtags = new ArrayList<>();

}
