package zerobase.demo.review.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Integer restaurantId;
    private Integer orderId;
    private String summary;
    private String content;

    public static ReviewDto fromRequest(ReviewRequest request) {
        return ReviewDto.builder()
                .restaurantId(request.getRestaurantId())
                .orderId(request.getOrderId())
                .summary(request.getContent().substring(0,Math.min(request.getContent().length(),10)))
                .content(request.getContent())
                .build();
    }

}
