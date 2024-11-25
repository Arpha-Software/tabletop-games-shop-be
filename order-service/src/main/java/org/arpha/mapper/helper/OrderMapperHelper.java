package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.entity.User;
import org.arpha.security.UserDetailsAdapter;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class OrderMapperHelper {

    @Named("getUser")
    public User getUser(Object plug) {
        return  ((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user();
    }

    @Named("toDeliveryPrice")
    public BigDecimal toDeliveryPrice(String deliveryPrice) {
        return new BigDecimal(deliveryPrice);
    }

    @Named("toDate")
    public LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }

}
