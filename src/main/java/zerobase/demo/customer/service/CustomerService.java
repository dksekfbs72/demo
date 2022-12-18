package zerobase.demo.customer.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import zerobase.demo.common.entity.Menu;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.menu.dto.MenuDto;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.user.dto.UserDto;
import zerobase.demo.user.dto.UserUpdateDto;

import java.util.List;

public interface CustomerService {
	/**
	 * 리뷰 남기기
	 */
    boolean userAddReview(ReviewDto fromRequest, String userId);

	/**
	 * 주문 내역 확인
	 */
	List<OrderDto> getMyOrderList(String userId);

	/**
	 * 식당 리뷰 불러오기
	 */
	List<Review> getStoreReview(Integer storeId);

	/**
	 * 식당 메뉴 리스트 보기
	 */
	List<MenuDto> getStoreMenu(Integer storeId);

	/**
	 * 장바구니에 메뉴 담기
	 */
	OrderDto putShoppingBasket(Integer storeId, String username, Integer menuId, Integer count);

	/**
	 * 장바구니에 메뉴 빼기
	 */
	OrderDto pullShoppingBasket(String username, Integer menuId);

	/**
	 * 내 주문 결제하기
	 */
	OrderDto orderPayment(String username);
}
