package zerobase.demo.customer.redis.customStructure;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectResult {
	int minOffset;
	int maxLimit;

	List<Integer> storeList;
}
