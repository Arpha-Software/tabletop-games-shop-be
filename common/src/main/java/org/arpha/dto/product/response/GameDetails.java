package org.arpha.dto.product.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDetails {
    private String players;
    private String age;
    private String playTime;
    private Double complexity;
    private Double bggRating;
    private String components;
}
