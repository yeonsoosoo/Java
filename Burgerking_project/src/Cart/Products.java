package Cart;

public class Products {
	
	//상품명, 가격, 수량
	String name;
	int count;
	String price;
	
	public int getCount() {
		return count;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
