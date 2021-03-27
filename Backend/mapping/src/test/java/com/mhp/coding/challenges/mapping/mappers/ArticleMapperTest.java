package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.db.ImageSize;
import com.mhp.coding.challenges.mapping.models.db.blocks.*;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.ImageDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ArticleMapperTest.SpringTestConfig.class)
public class ArticleMapperTest {
    @Configuration
    @ComponentScan(basePackageClasses = ArticleMapperTest.class)
    public static class SpringTestConfig {
    }

    @Autowired
    private ArticleMapper articleMapper;

    // acceptance criterion 1 - mapping
    // acceptance criterion 2 - sorted
    @Test
    public void shouldMapFullArticle() {
        // given
        final Date someDate = Date.from(Instant.EPOCH);
        Article article = Article.builder()
                .author("author")
                .id(12345678L)
                .description("description")
                .lastModified(someDate)
                .lastModifiedBy("lastModifiedBy")
                .title("title")
                .blocks(new HashSet<>(asList(
                        TextBlock.builder()
                                .sortIndex(3)
                                .text("text")
                                .build(),
                        GalleryBlock.builder()
                                .sortIndex(2)
                                .images(asList(
                                        Image.builder()
                                                .id(123L)
                                                .imageSize(ImageSize.LARGE)
                                                .url("url")
                                                .lastModified(someDate)
                                                .lastModifiedBy("lastModifiedBy")
                                                .build()
                                )).build(),
                        ImageBlock.builder()
                                .sortIndex(1)
                                .image(Image.builder()
                                        .id(123L)
                                        .imageSize(ImageSize.MEDIUM)
                                        .url("other_url")
                                        .lastModified(someDate)
                                        .lastModifiedBy("lastModifiedBy")
                                        .build())
                                .build(),
                        VideoBlock.builder()
                                .sortIndex(4)
                                .url("VideoBlockurl")
                                .type(VideoBlockType.YOUTUBE)
                                .build()
                ))).build();

        // when
        ArticleDto dto = articleMapper.articleToArticleDto(article);

        // then
        assertThat(dto).isEqualToIgnoringGivenFields(ArticleDto.builder()
                .author("author")
                .id(12345678L)
                .description("description")
                .title("title")
                .build(), "blocks");

        assertThat(dto.getBlocks())
                .extracting(ArticleBlockDto::getSortIndex)
                .isSorted();

        assertThat(dto.getBlocks()).containsExactlyInAnyOrder(
                ImageBlockDto.builder()
                        .sortIndex(1)
                        .image(ImageDto.builder()
                                .id(123L)
                                .imageSize(ImageSize.MEDIUM)
                                .url("other_url")
                                .build())
                        .build(),
                GalleryBlockDto.builder()
                        .sortIndex(2)
                        .images(asList(
                                ImageDto.builder()
                                        .id(123L)
                                        .imageSize(ImageSize.LARGE)
                                        .url("url")
                                        .build()
                        )).build(),
                TextBlockDto.builder()
                        .sortIndex(3)
                        .text("text")
                        .build(),
                VideoBlockDto.builder()
                        .sortIndex(4)
                        .url("VideoBlockurl")
                        .type(VideoBlockType.YOUTUBE)
                        .build()
        );
    }
}
