package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import po.Item;
import util.DBUtil;

public class CommodityDao {
	
	public Item findById(int id) {
		Connection conn = DBUtil.getConn();
		String sql = "select * from commodity where id = ?";
		PreparedStatement pstmt = null;
		Item item = null;
		ResultSet rSet = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rSet = pstmt.executeQuery();
			while (rSet.next()) {
				//id， name， num， price， imagepath
				item = new Item(rSet.getInt(1), rSet.getString(2), 1, rSet.getDouble(3), rSet.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeRst(rSet);
			DBUtil.closePstmt(pstmt);
			DBUtil.closeConn(conn);	
		}
		return item;
	}
}
