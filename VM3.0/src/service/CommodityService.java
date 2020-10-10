package service;

import dao.CommodityDao;
import po.Item;


public class CommodityService {
	private CommodityDao dao = new CommodityDao();
	
	public Item findById(int id) {
		return dao.findById(id);
	}
}
