package com.mhp.coding.challenges.mapping.mappers

import com.mhp.coding.challenges.mapping.models.db.Article
import com.mhp.coding.challenges.mapping.models.db.Image
import com.mhp.coding.challenges.mapping.models.db.ImageSize
import com.mhp.coding.challenges.mapping.models.db.blocks.*
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import com.mhp.coding.challenges.mapping.models.dto.ImageDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class ArticleMapper {

    fun mapToDto(article: Article): ArticleDto {
        return ArticleDto(
            id = article.id,
            title = article.title,
            description = article.description.orEmpty(),
            author = article.author.orEmpty(),
            blocks = article.blocks
                .map(this::mapToDto)
                .sortedBy(ArticleBlockDto::sortIndex)
        )
    }

    fun mapToDto(articleBlock: ArticleBlock): ArticleBlockDto {
        return when (articleBlock) {
            is GalleryBlock -> mapToDto(articleBlock)
            is ImageBlock -> mapToDto(articleBlock)
            is TextBlock -> mapToDto(articleBlock)
            is VideoBlock -> mapToDto(articleBlock)
            else -> {
                throw UnsupportedOperationException("Mapping of class %s is not supported.".format(articleBlock.javaClass.simpleName))
            }
        }
    }

    fun mapToDto(galleryBlock: GalleryBlock): GalleryBlockDto {
        return GalleryBlockDto(
            images = galleryBlock.images
                .filterNotNull()
                .map(this::mapToDto),
            sortIndex = galleryBlock.sortIndex
        )
    }

    fun mapToDto(image: Image?): ImageDto {
        return ImageDto(
            id = image?.id ?: -1,
            url = image?.url.orEmpty(),
            imageSize = image?.imageSize ?: ImageSize.SMALL,
        )
    }

    fun mapToDto(imageBlock: ImageBlock): ImageBlockDto {
        return ImageBlockDto(
            image = mapToDto(imageBlock.image),
            sortIndex = imageBlock.sortIndex
        )
    }

    fun mapToDto(textBlock: TextBlock): TextBlockDto {
        return TextBlockDto(
            text = textBlock.text,
            sortIndex = textBlock.sortIndex
        )
    }

    fun mapToDto(videoBlock: VideoBlock): VideoBlockDto {
        return VideoBlockDto(
            url = videoBlock.url,
            type = videoBlock.type,
            sortIndex = videoBlock.sortIndex
        )
    }

    // Not part of the challenge / Nicht Teil dieser Challenge.
    fun map(articleDto: ArticleDto?): Article = Article(
        title = "An Article",
        blocks = emptySet(),
        id = 1,
        lastModified = Date()
    )
}
