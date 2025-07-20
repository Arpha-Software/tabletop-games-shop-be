// tabletop-games-shop/product-service/src/main/java/org/arpha/mapper/CommentMapper.java

package org.arpha.mapper;

import org.arpha.dto.blog.response.CommentResponse;
import org.arpha.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "userId", source = "userId")
    CommentResponse toCommentResponse(Comment comment);
}