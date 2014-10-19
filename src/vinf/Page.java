package vinf;

import java.util.HashMap;

public class Page {
	private String titleTag;
	private String textTag;
	private Integer namespace;
	private HashMap<String, Integer> termFrquency;
	
	public Page(){
		this.titleTag = "";
		this.textTag = "";
		this.namespace = 0;
		this.termFrquency = new HashMap<>();
	}
	
	public void setTitleTag(String title){
		this.titleTag = title;
	}
	
	public void setTextTag(String text){
		this.textTag = text;
	}
	
	public String getTitleTag(){
		return titleTag;
	}
	
	public String getTextTag(){
		return textTag;
	}
	
	public void setNamespace(Integer ns){
		this.namespace = ns;
	}
	
	public Integer getNamespace(){
		return namespace;
	}
	
	public void setTermFrequncy(HashMap<String,Integer> map){
		this.termFrquency = map;
	}

	
	public HashMap<String,Integer> getTermFrequency(){
		return termFrquency;
	}
	
}
