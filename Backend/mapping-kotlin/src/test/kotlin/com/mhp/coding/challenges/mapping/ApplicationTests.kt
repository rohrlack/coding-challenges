package com.mhp.coding.challenges.mapping

import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus.OK

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun contextLoads() {
    }

    @Test
    fun `controller should return article list`() {
        val result = testRestTemplate.getForEntity<List<ArticleDto>>("/article")

        assertThat(result.statusCode).isEqualTo(OK)

        assertThat(result.body).hasSize(5)
    }

    @Test
    fun `controller should return article by id`() {
        val result = testRestTemplate.getForEntity<String>("/article/1")

        assertThat(result.statusCode).isEqualTo(OK)

        assertThat(result.body).contains("Article Description 1")
    }
}
