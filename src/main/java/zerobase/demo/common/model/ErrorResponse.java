package zerobase.demo.common.model;

import lombok.Getter;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;

@Getter
@Setter
public class ErrorResponse extends BaseResponse{

	public ErrorResponse(ResponseCode responseCode) {
		super(responseCode);
	}
}
