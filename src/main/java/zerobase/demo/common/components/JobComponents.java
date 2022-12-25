package zerobase.demo.common.components;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.demo.common.config.JobConfig;
import zerobase.demo.common.type.UserStatus;

@Slf4j
@Component
@AllArgsConstructor
public class JobComponents {

	private JobLauncher jobLauncher;
	private JobConfig jobConfig;
	@Scheduled(cron = "0 0 0/1 * * *")
	public void promotingReviewEmailSender() {
		log.info("send email to promote review");
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);
		try {
			jobLauncher.run(jobConfig.promotionMailSendJob(), jobParameters);
		} catch (JobInstanceAlreadyCompleteException | JobParametersInvalidException |
				 JobExecutionAlreadyRunningException | JobRestartException e) {
			throw new RuntimeException(e);
		}
	}

	public void noticeEmailSender(String status, String notice) {
		log.info(("send notice email"));
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		confMap.put("status", new JobParameter(status));
		confMap.put("notice", new JobParameter(notice));
		JobParameters jobParameters = new JobParameters(confMap);
		try {
			jobLauncher.run(jobConfig.sendNoticeJob(), jobParameters);
		} catch (JobInstanceAlreadyCompleteException | JobParametersInvalidException |
				 JobExecutionAlreadyRunningException | JobRestartException e) {
			throw new RuntimeException(e);
		}
	}
}
