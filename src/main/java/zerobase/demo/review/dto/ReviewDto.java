package zerobase.demo.review.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.entity.Store;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.owner.dto.StoreInfo;

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

    public static ReviewDto fromEntity(Review review) {

        return ReviewDto.builder()
            .restaurantId(review.getRestaurantId())
            .orderId(review.getOrderId())
            .summary(review.getSummary())
            .content(review.getContent())
            .build();
    }

    public static List<ReviewDto> fromList(List<Review> reviews) {

        return reviews.stream()
            .map(ReviewDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response<T> extends BaseResponse {
        private T reviewDtoList;

        public Response(T reviews, ResponseCode responseCode){

            super(responseCode);

            reviewDtoList = reviews;
        }
        public Response(ResponseCode responseCode){
            super((responseCode));
        }
    }
}
