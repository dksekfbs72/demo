package zerobase.demo.common.model;

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
public abstract class BaseResponse { //모든 Response가 상속한다.
	ResponseCode code;
	String message;

	public BaseResponse(ResponseCode responseCode) {
		this.code = responseCode;
		this.message = responseCode.getDescription();
	}
}
