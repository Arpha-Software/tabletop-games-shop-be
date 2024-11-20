package org.arpha.mapper;

import org.arpha.dto.order.request.CreateConsignmentDocumentRequest;
import org.arpha.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateConsignmentDocumentRequestMapper {


    CreateConsignmentDocumentRequest toCreateConsignmentDocumentRequest(Order order);

}
