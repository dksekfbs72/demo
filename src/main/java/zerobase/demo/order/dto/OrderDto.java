package zerobase.demo.order.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.*;
import zerobase.demo.common.entity.Coupon;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.model.BaseResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import zerobase.demo.common.type.OrderStatus;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Integer price;

    private List<Integer> menus;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Integer deliveryTime;
    private Integer restaurantId;
    private LocalDateTime orderTime;
    private List<Coupon> useCoupon;

    public static OrderDto request(Order request) {
        return OrderDto.builder()
                .price(request.getPrice())
                .menus(request.getMenus())
                .status(request.getStatus())
                .deliveryTime(request.getDeliveryTime())
                .restaurantId(request.getRestaurantId())
                .orderTime(request.getOrderTime())
                .useCoupon(request.getUseCoupon())
                .build();
    }

    public static List<OrderDto> fromEntity(List<Order> orders) {

        return orders.stream()
                .map(OrderDto::request)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response<T> extends BaseResponse {
        private T resultList;
        public Response(T orderDto, ResponseCode responseCode) {
            super(responseCode);
            resultList = orderDto;
        }
    }


}
