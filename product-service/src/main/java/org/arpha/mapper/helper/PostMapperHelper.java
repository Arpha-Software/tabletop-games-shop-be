// tabletop-games-shop/product-service/src/main/java/org/arpha/mapper/helper/PostMapperHelper.java

package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.media.enums.TargetType;
import org.arpha.entity.Post;
import org.arpha.service.MediaService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapperHelper {

    private final MediaService mediaService;

    @Named("getPostMainImageUrl")
    public String getPostMainImageUrl(Post post) {
        return mediaService.getFileLink(post.getId(), TargetType.POST_MAIN_IMAGE);
    }

    @Named("getOtherPostImageUrls")
    public List<String> getOtherPostImageUrls(Post post) {
        return mediaService.getFilesLinks(post.getId(), TargetType.POST_IMAGE);
    }
}