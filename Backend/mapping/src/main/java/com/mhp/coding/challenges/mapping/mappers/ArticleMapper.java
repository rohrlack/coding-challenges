package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.blocks.*;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ArticleMapper {

    ArticleDto articleToArticleDto(Article article);

    default ArticleBlockDto articleBlockToArticleBlockDto(ArticleBlock articleBlock) {
        if (articleBlock instanceof GalleryBlock) {
            return galleryBlockToGalleryBlockDto((GalleryBlock) articleBlock);
        } else if (articleBlock instanceof ImageBlock) {
            return imageBlockToImageBlockDto((ImageBlock) articleBlock);
        } else if (articleBlock instanceof TextBlock) {
            return textBlockToTextBlockDto((TextBlock) articleBlock);
        } else if (articleBlock instanceof VideoBlock) {
            return videoBlockToVideoBlockDto((VideoBlock) articleBlock);
        } else {
            throw new UnsupportedOperationException("Mapping is not implemented for ArticleBlocks of type " + articleBlock.getClass());
        }
    }

    GalleryBlockDto galleryBlockToGalleryBlockDto(GalleryBlock galleryBlock);

    ImageBlockDto imageBlockToImageBlockDto(ImageBlock imageBlock);

    TextBlockDto textBlockToTextBlockDto(TextBlock textBlock);

    VideoBlockDto videoBlockToVideoBlockDto(VideoBlock videoBlock);

    @AfterMapping
    default void sortBlocksAfterArticleMapping(@MappingTarget ArticleDto articleDto) {
        articleDto.setBlocks(articleDto.getBlocks().stream()
                .sorted()
                .collect(Collectors.toList()));
    }

    default Article map(ArticleDto articleDto) {
        // Nicht Teil dieser Challenge.
        return new Article();
    }
}
