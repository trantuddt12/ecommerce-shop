package com.search.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ttl.common.index.BrandIndexDto;

@Service
public class BrandIndexService {

	private static final Logger log = LoggerFactory.getLogger(BrandIndexService.class);
	
	@Value("${elasticsearch.url}")
	private String esUrl;
	
	private final RestTemplate restTemplate ;

	public BrandIndexService(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	public void indexBrand(BrandIndexDto dto) {
		// TODO 
//		String url = esUrl + "/brand/_doc/" + dto.getId();
//		Ý nghĩa:
//
//		Phần	Giải thích
//		brand	tên index trong Elasticsearch
//		_doc	type (ES7+ dùng mặc định _doc)
//		{id}	document id = id của Brand
		String url = esUrl + "/brand/_doc/" + dto.getId();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<BrandIndexDto> entity = new HttpEntity<>(dto, headers);
			restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		}catch(Exception ex) {
			log.error("Failed to index brand to ES id={}, slug={}, err={}", dto.getId(), dto.getSlug(), ex.getMessage(), ex);
            throw ex;
		}
	}

	public String searchBrands(String keyword, int from, int size) {
        String url = esUrl + "/brand/_search";

        String query = """
                {
                  "from": %d,
                  "size": %d,
                  "query": {
                    "multi_match": {
                      "query": "%s",
                      "type": "phrase_prefix",
                      "fields": ["name^3","description","slug"]
                    }
                  }
                }
                """.formatted(from, size, escapeJson(keyword));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(query, headers);
        String result = restTemplate.postForObject(url, request, String.class);
        return result;
    }

    // Minimal JSON escaping for quotes/backslashes in query
    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
