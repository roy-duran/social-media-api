package com.cooksys.assessment1.group3.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.group3.dtos.HashtagDto;
import com.cooksys.assessment1.group3.dtos.TweetResponseDto;
import com.cooksys.assessment1.group3.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDto> getAllHashtags() {
        return hashtagService.getAllHashtags();
    }

    @GetMapping("/{label}")
    public List<TweetResponseDto> getHashtags(@PathVariable String label) {
        return hashtagService.getHashtags(label);
    }

}
