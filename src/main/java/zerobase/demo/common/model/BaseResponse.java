package zerobase.demo.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.demo.common.type.ResponseCode;
import zerobase.demo.common.type.Result;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseResponse { //모든 Response가 상속한다.
	protected Result result;
	protected ResponseCode code;
	protected String message;

	public BaseResponse(ResponseCode responseCode) {
		this.result = responseCode.getResult();
		this.code = responseCode;
		this.message = responseCode.getDescription();
	}
}
