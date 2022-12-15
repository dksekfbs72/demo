package zerobase.demo.customer.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import zerobase.demo.common.type.ResponseCode;
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
}
