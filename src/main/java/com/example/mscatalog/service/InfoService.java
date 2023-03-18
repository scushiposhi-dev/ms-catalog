package com.example.mscatalog.service;

import com.example.mscatalog.model.Info;
import com.example.mscatalog.model.Wine;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfoService {

    @Value(value = "${ms-info.url: something}")
    private String urlInfoService;
    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getWineFallback",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="30000"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5")}
    )
    public Info<Wine> getWine(long wineId){
        return restTemplate.getForObject(urlInfoService + "/wines/" + wineId, Info.class);

    }

    public Info<Wine> getWineFallback(long wineId){
        return Info.<Wine>builder().description("from info fallback method").build();
    }

}
