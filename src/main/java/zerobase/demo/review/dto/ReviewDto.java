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

    public static ReviewDto fromRequest(ReviewDto.Request request) {
        String summary = "";
        if (request.content.length() < 10) summary = request.content;
        else summary = request.content.substring(0, 10);
        return ReviewDto.builder()
                .restaurantId(request.restaurantId)
                .orderId(request.orderId)
                .summary(summary)
                .content(request.content)
                .build();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Integer restaurantId;
        private Integer orderId;
        private String content;
    }

}
