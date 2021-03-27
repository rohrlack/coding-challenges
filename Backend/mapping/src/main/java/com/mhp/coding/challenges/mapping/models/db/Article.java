package com.mhp.coding.challenges.mapping.models.db;

import com.mhp.coding.challenges.mapping.models.db.blocks.ArticleBlock;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@NoArgsConstructor
public class Article extends DBEntity {

    private String title;

    private String description;

    private String author;

    private Set<ArticleBlock> blocks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<ArticleBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<ArticleBlock> blocks) {
        this.blocks = blocks;
    }
}
