package zerobase.demo.customer.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.demo.common.components.MailComponents;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.exception.UserException;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.customer.service.CustomerService;
import zerobase.demo.order.dto.OrderDto;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.review.dto.ReviewDto;
import zerobase.demo.review.repository.ReviewRepository;
import zerobase.demo.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;

	@Override
	public boolean userAddReview(ReviewDto fromRequest, String userId) {
		reviewRepository.findByOrderId(fromRequest.getOrderId())
				.ifPresent(t -> {throw new UserException(ResponseCode.ALREADY_ADDED_REVIEW);});

		Order order = orderRepository.findById(fromRequest.getOrderId())
				.orElseThrow(() -> new UserException(ResponseCode.ORDER_NOT_FOUND));

		if (!Objects.equals(order.getRestaurantId(), fromRequest.getRestaurantId())) {
			throw new UserException(ResponseCode.DIFF_ORDER_ID);
		}
		if (order.getOrderTime().plusDays(3).isBefore(LocalDateTime.now())) {
			throw new UserException(ResponseCode.TOO_OLD_AN_ORDER);
		}
		if (!order.getUserId().equals(userRepository.findByUserId(userId).get().getId())) {
			throw new UserException(ResponseCode.NOT_MY_ORDER);
		}

		Review review = new Review().builder()
				.writerId(userRepository.findByUserId(userId).get().getId())
				.orderId(fromRequest.getOrderId())
				.restaurantId(fromRequest.getRestaurantId())
				.summary(fromRequest.getSummary())
				.content(fromRequest.getContent())
				.reviewAddTime(LocalDateTime.now())
				.build();

		reviewRepository.save(review);

		return false;
	}

	@Override
	public List<OrderDto> getMyOrderList(String userId) {

		List<Order> orderDtoList = orderRepository
				.findAllByUserId(userRepository.findByUserId(userId).get().getId());
		if (orderDtoList.isEmpty()) throw new UserException(ResponseCode.THERE_IS_NO_ORDER);


		return OrderDto.fromEntity(orderDtoList);
	}

}
