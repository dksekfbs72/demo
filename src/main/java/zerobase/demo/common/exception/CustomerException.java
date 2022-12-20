package zerobase.demo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerException extends RuntimeException {
	private ResponseCode responseCode;
	private String errorMessage;

	public CustomerException(ResponseCode responseCode) {
		this.responseCode = responseCode;
		this.errorMessage = responseCode.getDescription();
	}
}
