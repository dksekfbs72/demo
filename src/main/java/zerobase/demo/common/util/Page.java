package zerobase.demo.common.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {
	static int PER_PAGE = 20;
	Integer page;
	Integer offset;
	Integer limit;

	public Page(int page) {
		this.page = page;

		this.offset = page == 1 ? 0 : (page-1)*20+1;
		this.limit = page*20;

	}
}
