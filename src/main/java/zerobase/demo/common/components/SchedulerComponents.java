package zerobase.demo.common.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.entity.Review;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.type.OrderStatus;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.owner.repository.StoreRepository;
import zerobase.demo.user.repository.UserRepository;

@Slf4j
@Component
@AllArgsConstructor
public class SchedulerComponents {
	private final MailComponents mailComponents;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	@Scheduled(cron = "0 */1 * * * *")
	public void promotingReviewEmailSender() {
		log.info("send email to promote review");

		List<Order> orderList = orderRepository.findAllByOrderTimeBetweenAndReviewedAndStatus(
			LocalDateTime.now().minusHours(2),
			LocalDateTime.now().minusHours(1),
			false,
			OrderStatus.DELIVERY_COMPLETE);


		for (Order i : orderList) {
			mailComponents.sendMail(userRepository.findById(i.getUserId()).get().getUserId(),
									"[조기요] 리뷰를 남겨보세요!",
									"<p>주문에 감사드립니다.</p>"
										+ "<p>주문하신 음식에 대한 리뷰를 남겨보세요!</p>");
		}
	}
}
