package penta.objects;

public class TabStructure {

	private int tabIndex;
	
	public TabStructure() { super(); }
	public TabStructure(int tabIndex)
	{
		super();
		this.tabIndex = tabIndex;
	}
	
	public int getTabIndex	(				) { return tabIndex; 			}
	public void setTabIndex	(int tabIndex	) { this.tabIndex = tabIndex; 	}
}
