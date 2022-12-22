package zerobase.demo.common.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page {
	static int PER_PAGE = 20;
	Integer startPage;
	Integer endPage;
	Integer offset;
	Integer limit;

	public Page(int startPage, int endPage) {
		this.startPage = startPage;
		this.endPage = endPage;

		this.offset = startPage == 1 ? 0 : (startPage-1)*20+1;
		this.limit = (endPage)*20;
	}
}
