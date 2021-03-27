package com.mhp.coding.challenges.mapping.mappers

import com.mhp.coding.challenges.mapping.models.db.Article
import com.mhp.coding.challenges.mapping.models.db.Image
import com.mhp.coding.challenges.mapping.models.db.ImageSize
import com.mhp.coding.challenges.mapping.models.db.blocks.*
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import com.mhp.coding.challenges.mapping.models.dto.ImageDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.GalleryBlockDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.ImageBlockDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlockDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlockDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@SpringBootTest
@ExtendWith(SpringExtension::class)
class ArticleMapperTest(@Autowired private val mapper: ArticleMapper) {

    val fullArticle = Article(
        id = 1001,
        lastModified = Date(),
        lastModifiedBy = "Hans Müller",
        title = "Article Nr.: 1001",
        description = "Article Description 1001",
        author = "Max Mustermann",
        blocks = setOf(
            GalleryBlock(
                images = listOf(
                    Image(
                        id = 2,
                        url = "https://someurl.com/image/2",
                        imageSize = ImageSize.LARGE,
                        lastModified = Date(),
                        lastModifiedBy = "John Doe"
                    )
                ),
                sortIndex = 0
            ),
            TextBlock(
                text = "Some Text for 1",
                sortIndex = 0
            ),
            ImageBlock(
                image = Image(
                    id = 1,
                    url = "https://someurl.com/image/1",
                    imageSize = ImageSize.LARGE,
                    lastModified = Date(),
                    lastModifiedBy = "John Doe"
                ),
                sortIndex = 1
            ),
            VideoBlock(
                type = VideoBlockType.YOUTUBE,
                url = "https://youtu.be/myvideo",
                sortIndex = 4
            )
        ),
    )

    val expectedFullArticleDto = ArticleDto(
        id = 1001,
        title = "Article Nr.: 1001",
        description = "Article Description 1001",
        author = "Max Mustermann",
        blocks = listOf(
            GalleryBlockDto(
                images = listOf(
                    ImageDto(
                        id = 2,
                        url = "https://someurl.com/image/2",
                        imageSize = ImageSize.LARGE
                    )
                ),
                sortIndex = 0
            ),
            TextBlockDto(
                text = "Some Text for 1",
                sortIndex = 0
            ),
            ImageBlockDto(
                image = ImageDto(
                    id = 1,
                    url = "https://someurl.com/image/1",
                    imageSize = ImageSize.LARGE
                ),
                sortIndex = 1
            ),
            VideoBlockDto(
                type = VideoBlockType.YOUTUBE,
                url = "https://youtu.be/myvideo",
                sortIndex = 4
            )
        ),
    )

    @Test
    fun `mapper should map full article`() {
        val result = mapper.mapToDto(fullArticle)
        Assertions.assertThat(result).isEqualTo(expectedFullArticleDto)
    }

    val minimalArticle = Article(
        id = 1001,
        lastModified = Date(),
        title = "Article Nr.: 1001",
        blocks = setOf(
            GalleryBlock(
                images = listOf(
                    null,
                    Image(
                        id = 3,
                        url = "https://someurl.com/image/3",
                        imageSize = ImageSize.LARGE,
                        lastModified = Date(),
                    )
                ),
                sortIndex = 0
            ),
            ImageBlock(
                sortIndex = 1,
                image = null,
            ),
            VideoBlock(
                type = VideoBlockType.YOUTUBE,
                url = "https://youtu.be/myvideo",
                sortIndex = 4
            )
        ),
    )

    val expectedMinimalArticleDto = ArticleDto(
        id = 1001,
        title = "Article Nr.: 1001",
        description = "",
        author = "",
        blocks = listOf(
            GalleryBlockDto(
                images = listOf(
                    ImageDto(
                        id = 3,
                        url = "https://someurl.com/image/3",
                        imageSize = ImageSize.LARGE
                    )
                ),
                sortIndex = 0
            ),
            ImageBlockDto(
                image = ImageDto(
                    id = -1,
                    url = "",
                    imageSize = ImageSize.SMALL
                ),
                sortIndex = 1
            ),
            VideoBlockDto(
                type = VideoBlockType.YOUTUBE,
                url = "https://youtu.be/myvideo",
                sortIndex = 4
            )
        ),
    )

    @Test
    fun `mapper should map minimal article`() {
        val result = mapper.mapToDto(minimalArticle)
        Assertions.assertThat(result).isEqualTo(expectedMinimalArticleDto)
    }
}
