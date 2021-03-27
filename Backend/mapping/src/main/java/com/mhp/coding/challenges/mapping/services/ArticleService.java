package com.mhp.coding.challenges.mapping.services;

import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository repository;

    private final ArticleMapper mapper;

    public List<ArticleDto> list() {
        final List<Article> articles = repository.all();
        return articles.stream()
                .map(mapper::articleToArticleDto)
                .collect(Collectors.toList());
    }

    public Optional<ArticleDto> articleForId(Long id) {
        final Optional<Article> article = repository.findBy(id);
        return article.map(mapper::articleToArticleDto);
    }

    public ArticleDto create(ArticleDto articleDto) {
        final Article create = mapper.map(articleDto);
        repository.create(create);
        return mapper.articleToArticleDto(create);
    }
}
