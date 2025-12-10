package com.ttl.base.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ttl.base.entities.Brand;
import com.ttl.common.index.BrandIndexDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandIndexerClient {

    private static final Logger log = LoggerFactory.getLogger(BrandIndexerClient.class);

    private final RestTemplate restTemplate;

    @Value("${searchservice.url}")
    private String searchServiceUrl; // e.g., http://localhost:8082

    public void indexBrand(Brand brand) {
        BrandIndexDto dto = BrandIndexDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .generic(brand.isGeneric())
                .slug(brand.getSlug())
                .build();

        String url = searchServiceUrl + "/internal/brand/index";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<BrandIndexDto> entity = new HttpEntity<>(dto, headers);
            restTemplate.postForObject(url, entity, Void.class);
        } catch (Exception ex) {
            // Avoid failing main transaction; log and continue.
            log.error("Failed to call SearchService for brand indexing: id={}, slug={}, error={}",
                    brand.getId(), brand.getSlug(), ex.getMessage(), ex);
        }
    }
}
