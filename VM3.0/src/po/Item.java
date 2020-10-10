package po;

//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class Item {
	private int id;
	private String name;
	private int num;
	private double price;
	private String imgPath;

	public Item() {
		super();
	}

	public Item(int id, String name, int num, double price, String imgPath) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.price = price;
		this.imgPath = imgPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", name=" + name + ", num=" + num + ", price=" + price + "]";
	}

}
