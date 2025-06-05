package com.minguccicommerce.product_service.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.CompletionSuggestOption;
import co.elastic.clients.elasticsearch.core.search.Suggestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSuggestionService {

    private final ElasticsearchClient elasticsearchClient;

    public List<String> getSuggestions(String prefix) throws IOException {
        // 검색 요청 빌더 생성
        SearchRequest request = new SearchRequest.Builder()
                .index("products") // 검색할 인덱스 이름
                .suggest(s -> s
                        .suggesters("product-suggest", sg -> sg
                                                    .text(prefix) // 사용자가 입력한 prefix
                                                    .completion(c -> c
                                                            .field("suggest") // suggest 필드를 기준으로
                                                            .skipDuplicates(true) // 중복 제거
                                                            .size(10) // 최대 추천 개수
                                                    )
                        )
                )
                .build();

        // Elasticsearch에 요청 전송
        SearchResponse<Void> response = elasticsearchClient.search(request, Void.class);

        // 결과 파싱 및 반환
        if (response.suggest() == null || response.suggest().get("product-suggest") == null) {
            return Collections.emptyList();
        }

       return response.suggest()
               .get("product-suggest")
               .stream()
               .flatMap(s -> s.completion().options().stream())
               .map(option -> option.text().toString())
               .collect(Collectors.toList());

    }

}
