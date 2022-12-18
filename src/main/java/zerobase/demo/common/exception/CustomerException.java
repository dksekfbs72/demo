package zerobase.demo.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CustomerException extends RuntimeException{

	private ResponseCode responseCode;

	public CustomerException(ResponseCode responseCode) {
		super(responseCode.getDescription());
		this.responseCode = responseCode;
	}
}
