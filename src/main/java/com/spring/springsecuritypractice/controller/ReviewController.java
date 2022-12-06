package com.spring.springsecuritypractice.controller;

import com.spring.springsecuritypractice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> writeReview(Authentication authentication){
        String review = reviewService.write(authentication.getName());
        return ResponseEntity.ok().body(review);
    }
}
