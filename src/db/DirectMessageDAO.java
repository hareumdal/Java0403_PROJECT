package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DirectMessageDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private static DirectMessageDAO directMessageDAO = null;

	private DirectMessageDAO() {

	}

	public static DirectMessageDAO getInstance(Connection c) {
		con = c;
		if (directMessageDAO == null) {
			directMessageDAO = new DirectMessageDAO();
		}
		return directMessageDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		try {
			DirectMessageDTO dm = (DirectMessageDTO) DTO;
			String sql = "insert into directmessage values(?, sysdate, ?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);

			psmt.setString(1, dm.getRoomname());

			psmt.setString(2, dm.getId());
			psmt.setString(3, dm.getMessage());

			int a = psmt.executeUpdate();

			if (a == 1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return false;
	}

	@Override
	public boolean update(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String s) {
		// TODO Auto-generated method stub
		try {
			String sql = "delete from directmessage where roomname=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);

			int cnt = psmt.executeUpdate();

			if (cnt == 1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean select(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object select(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDBList(String tName) {
		// TODO Auto-generated method stub
		ArrayList<Object> pList = new ArrayList<>();
		try {
			String sql = "select * from directmessage";
			stmt = con.prepareStatement(sql);

			if (stmt != null) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					DirectMessageDTO p = new DirectMessageDTO();

					p.setRoomname(rs.getString("roomname"));
					p.setDay(rs.getString("day"));
					p.setId("sendid");
					p.setMessage(rs.getString("message"));

					pList.add(p);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return pList;
	}

	@Override
	public Object getDBList(String tName, String s) {
		// TODO Auto-generated method stub
		ArrayList<Object> pList = new ArrayList<>();
		try {
			if (s.contains("/")) {
				String ss = s.substring(0, s.indexOf("/"));
				System.out.println("DirectMessgeDAO:::getDBList:: ss" + ss);
				//String sql = "select * from directmessage where roomname=? and rownum=1 order by day desc";
				String sql = " select * from (select * from directmessage where roomname=? order by day desc) where rownum=1";
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, ss);

				rs = psmt.executeQuery();

				while (rs.next()) {
					DirectMessageDTO p = new DirectMessageDTO();
					p.setRoomname(rs.getString("roomname"));
					p.setDay(rs.getString("day"));
					p.setId("id");
					p.setMessage(rs.getString("message"));
					pList.add(p);
				}
			} else {
				String sql = "select * from directmessage where roomname=? order by day asc";

				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, s);

				rs = psmt.executeQuery();

				while (rs.next()) {
					DirectMessageDTO p = new DirectMessageDTO();

					p.setRoomname(rs.getString("roomname"));
					p.setDay(rs.getString("day"));
					p.setId(rs.getString("id"));
					p.setMessage(rs.getString("message"));
					pList.add(p);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return pList;
	}

}
