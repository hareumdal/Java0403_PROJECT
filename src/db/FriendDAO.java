package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FriendDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private static FriendDAO FriendDAO = null;

	private FriendDAO() {

	}

	public static FriendDAO getInstance(Connection c) {
		con = c;
		if (FriendDAO == null) {
			FriendDAO = new FriendDAO();
		}
		return FriendDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		try {
			FriendDTO f = (FriendDTO) DTO;
			String sql = "insert into friend values(?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, f.getMyId());
			psmt.setString(2, f.getYourId());

			int a = psmt.executeUpdate();

			if (a > 0) {
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
		try {
			FriendDTO f = null;
			String sql = "select * from friend";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				f = new FriendDTO();
				f.setMyId(rs.getString("myid"));
				f.setYourId(rs.getString("yourid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public ArrayList<FriendDTO> getDBList(String tName) {
		// TODO Auto-generated method stub
		ArrayList<FriendDTO> frList = new ArrayList<>();
		try {
			String sql = "select * from friend";
			stmt = con.prepareStatement(sql);
			if (stmt != null) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("DB not connect");
		}
		return frList;
	}

	@Override
	public Object select(String s) {
		FriendDTO f = null;
		ArrayList<FriendDTO> frList = new ArrayList<>();
		String sql = "select yourid from friend where myid=?";
		PreparedStatement psmt;
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				f = new FriendDTO();
				f.setMyId(rs.getString("myid"));
				f.setYourId(rs.getString("yourid"));
				frList.add(f);
			}
			return frList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
