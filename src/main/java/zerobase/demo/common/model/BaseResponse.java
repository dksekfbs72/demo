package zerobase.demo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.Result;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseResponse {
	Result result;
	ResponseCode code;
	String message;

	public BaseResponse(ResponseCode responseCode) {
		this.result = responseCode.getResult();
		this.code = responseCode;
		this.message = responseCode.getDescription();
	}
}
