package kwik.product.model;

import java.sql.Date;

public class MovieProduct extends Product {
	String actors;
	String format;
	String language;
	String subtitles;
	String region;
	String aspect_ratio;
	String number_discs;
	Date release_date;
	String run_time;
	String ASIN;
	
	public MovieProduct(int product_id, int category_id, int subcategory_id,
			String name, int sales_rank, Double price, String image_url,
			String type,String actors, String format, String language,
			String subtitles, String region, String aspect_ratio,
			String number_discs, Date release_date, String run_time, String aSIN) {
		super(product_id, category_id, subcategory_id, name, sales_rank, price, image_url,type);
		this.actors = actors;
		this.format = format;
		this.language = language;
		this.subtitles = subtitles;
		this.region = region;
		this.aspect_ratio = aspect_ratio;
		this.number_discs = number_discs;
		this.release_date = release_date;
		this.run_time = run_time;
		ASIN = aSIN;
	}
}
