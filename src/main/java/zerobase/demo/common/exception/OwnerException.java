package zerobase.demo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class OwnerException extends RuntimeException{

	private ResponseCode responseCode;

	public OwnerException(ResponseCode responseCode) {
		super(responseCode.getDescription());
		this.responseCode = responseCode;
	}
}
