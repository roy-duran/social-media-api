//package com.cooksys.assessment1.group3;
//
//import com.cooksys.assessment1.group3.repositories.HashtagRepository;
//import com.cooksys.assessment1.group3.repositories.TweetRepository;
//import com.cooksys.assessment1.group3.repositories.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@AllArgsConstructor
//public class Seeder implements CommandLineRunner {
//
//    private final TweetRepository tweetRepository;
//    private final UserRepository userRepository;
//    private final HashtagRepository hashtagRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//
////        Credentials credentials1 = new Credentials();
////        credentials1.setUsername("username1");
////        credentials1.setPassword("password1");
////
////        Profile profile1 = new Profile();
////        profile1.setFirstName("firstName1");
////        profile1.setLastName("lastName1");
////        profile1.setPhone("11111111");
////        profile1.setEmail("1@fake.com");
////
////        User user1 = new User();
////        user1.setCredentials(credentials1);
////        user1.setProfile(profile1);
////
////        userRepository.save(user1);
////
////        Tweet tweet1User1 = new Tweet();
////        tweet1User1.setAuthor(user1);
////        tweet1User1.setContent("This is tweet #1...");
////        Tweet tweet2User1 = new Tweet();
////        tweet2User1.setAuthor(user1);
////        tweet2User1.setContent("This is tweet #2...");
////        Tweet tweet3User1 = new Tweet();
////        tweet3User1.setAuthor(user1);
////        tweet3User1.setContent("This is tweet #3...");
////        tweet3User1.setInReplyTo(tweet2User1);
////
////        tweetRepository.saveAll(Arrays.asList(tweet1User1, tweet2User1, tweet3User1));
////
////        Hashtag hashtag1 = new Hashtag();
////        hashtag1.setLabel("TestHashTag1");
////        Hashtag hashtag2 = new Hashtag();
////        hashtag2.setLabel("TestHashTag2");
////        Hashtag hashtag3 = new Hashtag();
////        hashtag3.setLabel("TestHashTag3");
////
////        hashtagRepository.saveAll(Arrays.asList(hashtag1, hashtag2, hashtag3));
////
////        Credentials credentials2 = new Credentials();
////        credentials2.setUsername("username2");
////        credentials2.setPassword("password2");
////
////        Profile profile2 = new Profile();
////        profile2.setFirstName("firstName2");
////        profile2.setLastName("lastName2");
////        profile2.setPhone("2222222222");
////        profile2.setEmail("2@fake.com");
////
////        User user2 = new User();
////        user2.setCredentials(credentials2);
////        user2.setProfile(profile2);
////
////        userRepository.save(user2);
////
////        Tweet tweet1User2 = new Tweet();
////        tweet1User2.setAuthor(user2);
////        tweet1User2.setContent("This is tweet #1...");
////        Tweet tweet2User2 = new Tweet();
////        tweet2User2.setAuthor(user2);
////        tweet2User2.setContent("This is tweet #2...");
////        Tweet tweet3User2 = new Tweet();
////        tweet3User2.setAuthor(user2);
////        tweet3User2.setContent("This is tweet #3...");
////        tweet3User2.setInReplyTo(tweet1User1);
////
////        tweetRepository.saveAll(Arrays.asList(tweet1User2, tweet2User2, tweet3User2));
////
////        Credentials credentials3 = new Credentials();
////        credentials3.setUsername("username3");
////        credentials3.setPassword("password3");
////
////        Profile profile3 = new Profile();
////        profile3.setFirstName("firstName3");
////        profile3.setLastName("lastName3");
////        profile3.setPhone("333333333");
////        profile3.setEmail("3@fake.com");
////
////        User user3 = new User();
////        user3.setCredentials(credentials3);
////        user3.setProfile(profile3);
////
////        userRepository.save(user3);
////
////        Tweet tweet1User3 = new Tweet();
////        tweet1User3.setAuthor(user3);
////        tweet1User3.setContent("This is tweet #1...");
////        Tweet tweet2User3 = new Tweet();
////        tweet2User3.setAuthor(user3);
////        tweet2User3.setContent("This is tweet #2...");
////        Tweet tweet3User3 = new Tweet();
////        tweet3User3.setAuthor(user3);
////        tweet3User3.setContent("This is tweet #3...");
////        tweet3User3.setInReplyTo(tweet1User1);
////
////        tweetRepository.saveAll(Arrays.asList(tweet1User3, tweet2User3, tweet3User3));
//    }
//}
