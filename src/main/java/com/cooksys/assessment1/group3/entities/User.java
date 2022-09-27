package com.cooksys.assessment1.group3.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.cooksys.assessment1.group3.entities.embeddable.Credentials;
import com.cooksys.assessment1.group3.entities.embeddable.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user_table")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Credentials credentials;

    @CreationTimestamp
    private Timestamp joined = Timestamp.valueOf(LocalDateTime.now());

    private boolean deleted = false;

    @Embedded
    private Profile profile;

    @ManyToMany(mappedBy = "likedBy", cascade = CascadeType.ALL)
    private List<Tweet> likedTweets = new ArrayList<>();

    @ManyToMany(mappedBy = "tweetMentions")
    private List<Tweet> userMentions = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets = new ArrayList<>();

    @ManyToMany
    @JoinTable
    private List<User> followers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<User> following = new ArrayList<>();

}
