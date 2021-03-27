package com.mhp.coding.challenges.mapping.controllers

import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import com.mhp.coding.challenges.mapping.services.ArticleService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController(
    private val articleService: ArticleService
) {
    @GetMapping
    fun list(): List<ArticleDto> = articleService.list()

    @GetMapping("/{id}")
    fun details(@PathVariable id: Long): ResponseEntity<ArticleDto> {
        val articleForId = articleService.articleForId(id)
        articleForId?.let {
            return ok(it)
        } ?: run {
            return notFound().build()
        }
    }

    @PostMapping
    fun create(@RequestBody articleDto: ArticleDto): ArticleDto = articleService.create(articleDto)
}
