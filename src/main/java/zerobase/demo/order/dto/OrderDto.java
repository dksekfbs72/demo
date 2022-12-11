package zerobase.demo.order.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Integer price;

    private String menus;

    private Integer userId;
    private String status;
    private Integer deliveryTime;
    private Integer restaurantId;
    private LocalDateTime orderTime;
    private Integer useCouponId;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private Integer price;

        private String menus;

        private Integer userId;
        private String status;
        private Integer deliveryTime;
        private Integer restaurantId;
        private LocalDateTime orderTime;
        private Integer useCouponId;
    }

}
