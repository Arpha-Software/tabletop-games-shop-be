// tabletop-games-shop/product-service/src/main/java/org/arpha/mapper/PostMapper.java

package org.arpha.mapper;

import org.arpha.dto.blog.request.CreatePostRequest;
import org.arpha.dto.blog.response.PostResponse;
import org.arpha.entity.Post;
import org.arpha.mapper.helper.PostMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {CommentMapper.class, PostMapperHelper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "createdBy")
    @Mapping(target = "mainImageUrl", source = "post", qualifiedByName = "getPostMainImageUrl")
    @Mapping(target = "otherImageUrls", source = "post", qualifiedByName = "getOtherPostImageUrls")
    PostResponse toPostResponse(Post post);

    Post toPost(CreatePostRequest request);
}