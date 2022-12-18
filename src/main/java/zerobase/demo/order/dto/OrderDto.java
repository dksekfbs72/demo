package zerobase.demo.order.dto;

import lombok.*;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.model.BaseResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Integer price;

    private List<Integer> menus;

    private Integer userId;
    private String status;
    private Integer deliveryTime;
    private Integer restaurantId;
    private LocalDateTime orderTime;
    private Integer useCouponId;

    public static OrderDto request(Order request) {
        return OrderDto.builder()
                .price(request.getPrice())
                .menus(request.getMenus())
                .userId(request.getUserId())
                .status(request.getStatus())
                .deliveryTime(request.getDeliveryTime())
                .restaurantId(request.getRestaurantId())
                .orderTime(request.getOrderTime())
                .useCouponId(request.getUseCouponId())
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
    public static class ListResponse extends BaseResponse {
        private List<OrderDto> orderDtoList;

        public ListResponse(List<OrderDto> list, ResponseCode responseCode){

            super(responseCode);

            orderDtoList = list;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response extends BaseResponse {

        private OrderDto order;
        public Response(OrderDto orderDto, ResponseCode responseCode) {
            super(responseCode);
            order = orderDto;
        }
    }


}
