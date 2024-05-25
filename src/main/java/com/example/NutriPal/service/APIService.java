package com.example.NutriPal.service;

import com.example.NutriPal.dto.NutritionDetailsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class APIService {
    public Map getNutritionDetails(NutritionDetailsRequest nutritionDetailsRQ) throws NoSuchAlgorithmException, InvalidKeyException{

    String api_key = "d28718060b8adfd39783ead254df7f92";

    String app_id = "47379841";

    String urlTemplate = UriComponentsBuilder.fromHttpUrl("https://api.edamam.com/api/nutrition-details")
            .queryParam("app_id", app_id)
            .queryParam("app_key", api_key)
            .encode()
            .toUriString();

    HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    HttpEntity entity = new HttpEntity(nutritionDetailsRQ,headers);

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<Map> response = restTemplate.exchange(
            urlTemplate,
            HttpMethod.POST,
            entity,
            Map.class
    );


        return response.getBody();
}

}
