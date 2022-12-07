package zerobase.demo.model;

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
public class ResponseResult {
	ResponseCode code;
	String message;

	public ResponseResult(ResponseCode responseCode) {
		this.code = responseCode;
		this.message = responseCode.getDescription();
	}
}
