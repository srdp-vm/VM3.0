package po;

import java.util.ArrayList;
import java.util.Date;    //放入数据库要用java.sql.Date，后面再转化成java.sql.Date
import java.util.HashMap;
import java.util.Map;

public class OrderStore {
	private String order_id; 
	private String subject = "";
	private double total_amount;
	private String create_time;
	private ArrayList<Item> items;
	
	public OrderStore() {
		super();
	}
	
	public OrderStore(String order_id, String subject, double total_amount, String create_time, ArrayList<Item> items) {
		super();
		this.order_id = order_id;
		this.subject = subject;
		this.total_amount = total_amount;
;		this.create_time = create_time;
		this.items = items;
	}


	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
}
