package com.example.mscatalog.service;

import com.example.mscatalog.model.Rating;
import com.example.mscatalog.model.RatingWrapper;
import com.example.mscatalog.model.Wine;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RatingService {
    @Value(value = "${ms-rating.url}")
    private String url;
    private final RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getWineRatingsFallback",
            commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="30000"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5")})
    public RatingWrapper getWineRatings(){
        return restTemplate.getForObject(url +"/wines", RatingWrapper.class);
    }
    public RatingWrapper getWineRatingsFallback(){
        return RatingWrapper.builder().ratingList(Arrays.asList(Rating.<Wine>builder().object(Wine.builder().id(10L).build()).description("from rating fallback method").build())).build();
    }
}
