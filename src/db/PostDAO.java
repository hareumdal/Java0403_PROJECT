package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PostDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private static PostDAO PostDAO = null;

	private PostDAO() {

	}

	public static PostDAO getInstance(Connection c) {
		con = c;
		if (PostDAO == null) {
			PostDAO = new PostDAO();
		}
		return PostDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		try {
			PostDTO p = (PostDTO) DTO;
			String sql = "insert into post values(no.nextval, to_date(sysdate, 'yyyy-mm-dd hh24:mi:ss'), ?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);

			psmt.setString(1, p.getId());
			psmt.setString(2, p.getText());

			int a = psmt.executeUpdate();

			if (a > 0) {
				return true;
			}
		} catch (SQLException e) {
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
	public boolean update(Object DTO) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String s) {
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
		return null;
	}

	@Override
	public Object getDBList(String tName, String s) { // 친구가 두 명이면 실행 안 됨
		ArrayList<PostDTO> pList = new ArrayList<>();
		// TODO Auto-generated method stub
		try { // 시간
			String sql = "select * from post where id in (select yourid from friend where myid=?) or id=?";
			PreparedStatement psmt = con.prepareStatement(sql);

			psmt.setString(1, s);
			psmt.setString(2, s);
			rs = psmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {

					PostDTO p = new PostDTO();
					// rs.getFloat("no");
					p.setNo(rs.getInt("no"));
					p.setDay(String.valueOf(rs.getDate("day")));
					p.setId(rs.getString("id"));
					p.setText(rs.getString("text"));
					System.out.println();

					pList.add(p);
				}

				return pList;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB not connect");
		}
		return null;
	}

	@Override
	public Object getDBListES(String tName, Object object) {
		ArrayList<PostDTO> pList = new ArrayList<>();

		ArrayList<String> obList = (ArrayList<String>) object;
		try { // 시간
			String sql = "select * from (select * from post where id in (select yourid from friend where myid=?) or id=?) where no between (select no from (select * from post where id in (select yourid from friend where myid=?) or id=?) where no=?) and (select no from (select * from post where id in (select yourid from friend where myid=?) or id=? order by no desc) where rownum=1)";
			PreparedStatement psmt = con.prepareStatement(sql);

			psmt.setString(1, obList.get(0));
			psmt.setString(2, obList.get(0));
			psmt.setString(3, obList.get(0));
			psmt.setString(4, obList.get(0));
			psmt.setString(5, obList.get(1));
			psmt.setString(6, obList.get(0));
			psmt.setString(7, obList.get(0));
			rs = psmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					PostDTO p = new PostDTO();
					p.setNo(rs.getInt("no"));
					p.setDay(String.valueOf(rs.getDate("day")));
					p.setId(rs.getString("id"));
					p.setText(rs.getString("text"));

					pList.add(p);
				}
				return pList;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB not connect");
		}
		return null;
	}

}
