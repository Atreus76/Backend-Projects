package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class UrlShortenerService {
    private final UrlRepository urlRepository;

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateShortUrl(){
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] randomBytes = new byte[6];
            random.nextBytes(randomBytes);
            String secureUrl = Base64.getEncoder().encodeToString(randomBytes);
            while (urlRepository.findByShortUrl(secureUrl) != null){
                random.nextBytes(randomBytes);
                secureUrl = Base64.getEncoder().encodeToString(randomBytes);
            }
            return secureUrl;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean urlValidate(String url){
        if(url.startsWith("http://") || url.startsWith("https://")){
            return true;
        }
        else{
            return false;
        }
    }
    public Url shortenUrl(UrlRequest request){
        String shortUrl = "";
        if (urlValidate(request.getLongUrl())){
            String secureUrl = generateShortUrl();
            shortUrl = "http://localhost:8080/" + secureUrl;
        }
        Url url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setShortUrl(shortUrl);
        url.setCreateAt(LocalDate.now());
        return urlRepository.save(url);
    }
}
