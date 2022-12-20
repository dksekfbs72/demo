package zerobase.demo.menu.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.entity.Menu;
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
        return MenuDto.builder()
                .price(request.getPrice())
                .name(request.getName())
                .pictureUrl(request.getPictureUrl())
                .summary(request.getSummary())
                .soldOut(request.isSoldOut())
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
    public static class Response<T> extends BaseResponse {
        private T resultList;

        public Response(T list, ResponseCode responseCode){

            super(responseCode);

            resultList = list;
        }
    }
}




