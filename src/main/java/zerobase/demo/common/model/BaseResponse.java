package zerobase.demo.common.model;

import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(example = "결과(FAIL or SUCCESS)")
	protected Result result;
	@ApiModelProperty(example = "상태 코드")
	protected ResponseCode code;
	@ApiModelProperty(example = "결과 메세지")
	protected String message;

	public BaseResponse(ResponseCode responseCode) {
		this.result = responseCode.getResult();
		this.code = responseCode;
		this.message = responseCode.getDescription();
	}
}
