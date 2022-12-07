package zerobase.demo.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;


@Getter
@Setter
public class ErrorResponse extends BaseResponse{
	ResponseCode code;
	String message;

	public ErrorResponse(ResponseCode responseCode) {
		this.code = responseCode;
		this.message = responseCode.getDescription();
	}
}
