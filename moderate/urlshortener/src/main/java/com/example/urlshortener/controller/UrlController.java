package com.example.urlshortener.controller;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.UrlRequest;
import com.example.urlshortener.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class UrlController {
    private static final Logger log = LoggerFactory.getLogger(UrlController.class);

    @Autowired
    private UrlShortenerService urlService;

    @PostMapping("/shorten")
    public Url shorten(@RequestBody UrlRequest urlRequest){
        log.info("Create short Url");
        return urlService.shortenUrl(urlRequest);
    }

}
