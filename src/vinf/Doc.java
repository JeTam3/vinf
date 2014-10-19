package vinf;

import java.util.HashMap;

public class Doc {
	private String titleTag;
	private String abstractTextTag;
	private HashMap<String, Integer> termFrquency;
	
	public Doc(){
		this.titleTag = "";
		this.abstractTextTag = "";
	}
	
	public void setTitleTag(String title){
		this.titleTag = title;
	}
	
	public void setAbstractTextTag(String text){
		this.abstractTextTag = text;
	}
	
	public String getTitleTag(){
		return titleTag;
	}
	
	public String getAbstractTextTag(){
		return abstractTextTag;
	}
	
	public void setTermFrequncy(HashMap<String,Integer> map){
		this.termFrquency = map;
	}

	
	public HashMap<String,Integer> getTermFrequency(){
		return termFrquency;
	}
}
