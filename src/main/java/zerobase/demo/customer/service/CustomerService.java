package zerobase.demo.customer.service;

import java.util.List;

import zerobase.demo.common.entity.Review;
import zerobase.demo.customer.dto.CustomerStoreDetail;
import zerobase.demo.customer.dto.CustomerStoreInfo;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.review.dto.ReviewDto;

public interface CustomerService {

	CustomerStoreInfo.Response getStoreList(CustomerStoreInfo.ListParam listParam);

	CustomerStoreDetail.Response getStoreDetail(CustomerStoreDetail.Request request);

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

	/**
	 * 주문 취소하기
	 */
	OrderDto cancelOrder(String username, Integer orderId);

	/**
	 * 쿠폰 사용
	 */
	OrderDto useCoupon(String name, Integer couponId);
}
