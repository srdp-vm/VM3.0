package dao;

import po.Item;
import po.OrderStore;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import util.DBUtil;

public class OrderStoreDao {
	public void orderInsert(OrderStore order) {
		Connection conn = DBUtil.getConn();
		// order: id, order_id, total_amount, create_time
		String sql = "INSERT INTO orderTable(order_id,total_amount,create_time) values(?,?,?)";
		//orderdetail:id ,name, count
		String sql_ = "INSERT INTO orderdetail(order_id,commodity_id, count) values(?,?,?)";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt_ = null;
		try {
			System.out.println("order_id:"+order.getOrder_id());  
			System.out.println("Total_amount:"+order.getTotal_amount());  
			System.out.println("Create_time:"+order.getCreate_time()); 
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, order.getOrder_id());
			pstmt.setDouble(2, order.getTotal_amount());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = (Date) sdf.parse(order.getCreate_time());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pstmt.setDate(3, new java.sql.Date(date.getTime()));
			int resultSet = pstmt.executeUpdate();
			System.out.println(resultSet);
			
			pstmt_ = conn.prepareStatement(sql_);
			/**
			pstmt_.setString(1, order.getOrder_id());
			pstmt_.setInt(2, 1);
			pstmt_.setInt(3, 1);
			System.out.println("other:"+pstmt_.executeUpdate());
			**/
			//遍历items数组
			for(Item item : order.getItems())
			{
				pstmt_.setString(1, order.getOrder_id());
				pstmt_.setInt(2, item.getId());
				pstmt_.setInt(3, item.getNum());
				System.out.println(pstmt_.executeUpdate());
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closePstmt(pstmt);
			DBUtil.closePstmt(pstmt_);
			DBUtil.closeConn(conn);
		}
	}
}
