package com.example.mscatalog.restxontroller;

import com.example.mscatalog.model.*;
import com.example.mscatalog.service.InfoService;
import com.example.mscatalog.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CatalogRestController {

    public final RestTemplate restTemplate;
    public final InfoService infoService;
    public final RatingService ratingService;
    @GetMapping("/wines")
    public Catalog<Wine> getCatalog(){
       RatingWrapper ratingWrapper = ratingService.getWineRatings();
        Rating<Wine> wineRating0 = ratingWrapper.getRatingList().get(0);

        Info<Wine> wineInfo = infoService.getWine(wineRating0.getObject().getId());
        return Catalog.<Wine>builder().object(wineInfo).rating(wineRating0).build();
    }



}
