package com.cooksys.assessment1.group3.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.assessment1.group3.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long tweetId);

    List<Tweet> findAllByDeletedFalse();

    List<Tweet> findAllByDeletedFalse(Sort by);

}
