package zerobase.demo.menu.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.model.BaseResponse;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {
    private Integer price;
    private String name;
    private String pictureUrl;
    private String summary;
    private boolean soldOut;

    public static MenuDto request(Menu request) {
        boolean soldOut = request.getSoldOut()==null ? false : request.getSoldOut();
        //null 이면 품절이 아님
        // 추후 메뉴 등록 시에 자동으로 false 로 등록되는 방식으로 수정 예정
        return MenuDto.builder()
                .price(request.getPrice())
                .name(request.getName())
                .pictureUrl(request.getPictureUrl())
                .summary(request.getSummary())
                .soldOut(soldOut)
                .build();
    }

    public static List<MenuDto> fromEntity(List<Menu> orders) {

        return orders.stream()
                .map(MenuDto::request)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response extends BaseResponse {
        private List<MenuDto> orderDtoList;

        public Response(List<MenuDto> list, ResponseCode responseCode){

            super(responseCode);

            orderDtoList = list;
        }
    }
}
