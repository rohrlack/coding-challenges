package com.mhp.coding.challenges.mapping.models.dto.blocks;

import com.mhp.coding.challenges.mapping.models.dto.ImageDto;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageBlockDto extends ArticleBlockDto {

    private ImageDto image;

    public ImageDto getImage() {
        return image;
    }

    public void setImage(ImageDto image) {
        this.image = image;
    }
}
