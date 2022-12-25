package zerobase.demo.common.config;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zerobase.demo.common.components.MailComponents;
import zerobase.demo.common.entity.Order;
import zerobase.demo.common.entity.User;
import zerobase.demo.common.type.OrderStatus;
import zerobase.demo.common.type.UserStatus;
import zerobase.demo.order.repository.OrderRepository;
import zerobase.demo.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final OrderRepository orderRepository;
	private final MailComponents mailComponents;
	private final UserRepository userRepository;

	@Bean
	public Job promotionMailSendJob() {
		return jobBuilderFactory.get("job")
			.start(Step1())
			.on("FAILED")
			.end()
			.from(Step1())
			.on("*")
			.to(Step2())
			.on("*")
			.end()
			.end()
			.build();
	}

	@Bean
	public Job sendNoticeJob() {
		return jobBuilderFactory.get("job2")
			.start(sendNoticeStep())
			.on("*")
			.end()
			.end()
			.build();
	}

	@Bean
	public Step Step1() {
		return stepBuilderFactory.get("step1")
			.tasklet((contribution, chunkContext) -> {
				List<Order> orderList = orderRepository.findAllByOrderTimeBetweenAndReviewedAndStatus(
					LocalDateTime.now().minusHours(2),
					LocalDateTime.now().minusHours(1),
					false,
					OrderStatus.DELIVERY_COMPLETE);
				if (orderList.isEmpty()) {
					System.out.println(("====메일 전송 대상 없음"));
					contribution.setExitStatus(ExitStatus.FAILED);
				}
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step Step2() {
		return stepBuilderFactory.get("step2")
			.tasklet((contribution, chunkContext) -> {
				List<Order> orderList = orderRepository.findAllByOrderTimeBetweenAndReviewedAndStatus(
					LocalDateTime.now().minusHours(2),
					LocalDateTime.now().minusHours(1),
					false,
					OrderStatus.DELIVERY_COMPLETE);
				for (Order i : orderList) {
					try {
						mailComponents.sendMail(
							userRepository.findById(i.getUserId()).get().getUserId(),
							"[조기요] 리뷰를 남겨보세요!",
							"<p>주문에 감사드립니다.</p>"
								+ "<p>주문하신 음식에 대한 리뷰를 남겨보세요!</p>");
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step sendNoticeStep() {
		return stepBuilderFactory.get("sendNoticeStep")
			.tasklet(helloTasklet(null, null))
			.build();
	}

	@Bean
	@StepScope //stepScope로 지정해줘야함
	public Tasklet helloTasklet(@Value("#{jobParameters['status']}") String status
		,@Value("#{jobParameters['notice']}") String notice) {
		return (stepContribution, chunkContext) -> {
			List<User> userList;
			if (!status.equals("ALL")){
				userList = userRepository.findAllByStatus(UserStatus.valueOf(status));
			} else {
				userList = userRepository.findAll();
			}
			if (userList.isEmpty()) {
				stepContribution.setExitStatus(ExitStatus.FAILED);
			}
			for (User i : userList) {
				try {
					mailComponents.sendMail(
						i.getUserId(),
						"[조기요] 중요! 공지사항 입니다.",
						"<p>회원 여러분께 공지 드립니다.</p>"
							+ "<p>"+ notice + "</p>");
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
			return RepeatStatus.FINISHED;
		};
	}
}
