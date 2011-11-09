package kwik.product.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import twitter.search.model.api.Tweet;

public class Category{

	private String id;
	private String code;
	private String name;
	
	
	public Category() {
		// TODO Auto-generated constructor stub
	}
	
	public Category(String id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	



}
