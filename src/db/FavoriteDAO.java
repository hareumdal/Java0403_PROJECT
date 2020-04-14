package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FavoriteDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private static FavoriteDAO FavoriteDAO = null;

	private FavoriteDAO() {

	}

	public static FavoriteDAO getInstance(Connection c) {
		con = c;
		if (FavoriteDAO == null) {
			FavoriteDAO = new FavoriteDAO();
		}
		return FavoriteDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		try {
			FavoriteDTO f = (FavoriteDTO) DTO;
			String sql = "insert into favorite values(?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, f.getNo());
			psmt.setString(2, f.getId());

			int a = psmt.executeUpdate();

			if (a > 0) {
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
		try {
			FavoriteDTO fv = (FavoriteDTO) DTO;
			String sql = "delete favorite where no=? and Id=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, fv.getNo());
			psmt.setString(2, fv.getId());
			int cnt = psmt.executeUpdate();

			if (cnt == 1) {
				System.out.println("FaDAO:: Good");
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
	public boolean delete(String s) {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public ArrayList<Object> getDBList(String tName, String s) {
		ArrayList<Object> fvList = new ArrayList<>();
		try {
			String sql = "select * from favorite where Id=?";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, s);
			rs = psmt.executeQuery();

			while (rs.next()) {
				FavoriteDTO fv = new FavoriteDTO();

				fv.setNo(rs.getString("no"));
				fv.setId(rs.getString("Id"));
				
				fvList.add(fv);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB error");
		}
		return fvList;

	}

}
