package service;

import dao.OrderStoreDao;
import po.OrderStore;


public class OrderStoreService {
	private OrderStoreDao dao = new OrderStoreDao();
	
	public void insertOrder(OrderStore order) {
		dao.orderInsert(order);
	}

}
