package zerobase.demo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

	private LocalDateTime regDt;
	private LocalDateTime udtDt;
}
