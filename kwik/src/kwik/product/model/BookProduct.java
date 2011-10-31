package kwik.product.model;

import java.sql.Date;

public class BookProduct extends Product {
	String authors;
	String publisher;
	Date published_date;
	String isbn_10;
	String isbn_13;
	String language;

	public BookProduct(int product_id, int category_id, int subcategory_id,
			String name, int sales_rank, Double price, String image_url,
			String type, String authors, String publisher, Date published_date,
			String isbn_10, String isbn_13, String language) {
		super(product_id, category_id, subcategory_id,
				name, sales_rank, price, image_url,	type);
		this.authors = authors;
		this.publisher = publisher;
		this.published_date = published_date;
		this.isbn_10 = isbn_10;
		this.isbn_13 = isbn_13;
		this.language = language;
	}

	

}
