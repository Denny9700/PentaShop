package penta.objects;

public class SearchStructure {

	private String keyword;
	
	public SearchStructure() {}
	public SearchStructure(String keyword)
	{
		this.keyword = keyword;
	}
	
	public String getKeyword(				) { return keyword; 		}
	public void setKeyword	(String keyword	) { this.keyword = keyword; }
	
}
