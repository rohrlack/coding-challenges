package com.mhp.coding.challenges.mapping.controllers

import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import com.mhp.coding.challenges.mapping.services.ArticleService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ArticleController::class)
@ExtendWith(MockitoExtension::class)
internal class ArticleControllerTest(
    @Autowired val mvc: MockMvc,
) {

    @MockBean
    lateinit var articleService: ArticleService

    private val article = ArticleDto(1, "title", "description", "author", listOf())
    private val articleJson =
        "{\"id\":1,\"title\":\"title\",\"description\":\"description\",\"author\":\"author\",\"blocks\":[]}"

    @Test
    fun `list endpoint should return article list`() {
        given(articleService.list())
            .willReturn(listOf(article))

        mvc.perform(get("/article"))
            .andExpect(status().isOk)
            .andExpect(content().string("[$articleJson]"))
    }

    @Test
    fun `details endpoint should return article`() {
        given(articleService.articleForId(1))
            .willReturn(article)

        mvc.perform(get("/article/1"))
            .andExpect(status().isOk)
            .andExpect(content().string(articleJson))
    }

    @Test
    fun `details endpoint should return notFound for unknown id`() {
        given(articleService.articleForId(1)).willReturn(null)

        mvc.perform(get("/article/1"))
            .andExpect(status().isNotFound)
    }
}
