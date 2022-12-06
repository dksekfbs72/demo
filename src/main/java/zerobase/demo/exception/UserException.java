package zerobase.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.type.ResponseCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserException extends RuntimeException{
	private ResponseCode responseCode;
	private String errorMassage;

	public UserException(ResponseCode responseCode) {
		this.responseCode = responseCode;
		this.errorMassage = responseCode.getDescription();
	}
}
