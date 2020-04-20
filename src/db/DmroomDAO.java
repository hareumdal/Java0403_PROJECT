package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DmroomDAO implements DAOInterface {
	private static Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private static DmroomDAO dmDAO = null;

	private DmroomDAO() {

	}

	public static DmroomDAO getInstance(Connection c) {
		con = c;
		if (dmDAO == null) {
			dmDAO = new DmroomDAO();
		}
		return dmDAO;
	}

	@Override
	public boolean insert(Object DTO) {
		try {
			DmroomDTO dmr = (DmroomDTO) DTO;
			String sql = "insert into dmroom values(?, ?)";
			PreparedStatement psmt = con.prepareStatement(sql);
			psmt.setString(1, dmr.getRoomname());
			psmt.setString(2, dmr.getId());

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
	public Object getDBList(String tName, String s) {
		// TODO Auto-generated method stub
		ArrayList<Object> pList = new ArrayList<>();
		try {
			if (s.contains("/t")) {
			
				String myId = s.substring(0, s.indexOf("/"));
				String yourid = s.substring(s.indexOf("/") + 1, s.lastIndexOf("/"));

				String sql = "select roomname from dmroom where (select roomname from dmroom where Id=?) = (select roomname from dmroom where Id=?) group by roomname";

				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, myId);
				psmt.setString(2, yourid);
				rs = psmt.executeQuery();

				while (rs.next()) {
					DmroomDTO dmr = new DmroomDTO();
					dmr.setRoomname(rs.getString("roomname"));
					pList.add(dmr);
				}
			} 	else if (s.contains("/n")) {
				String myId = s.substring(0, s.indexOf("/"));
				String roomname = s.substring(s.indexOf("/") + 1, s.lastIndexOf("/"));
System.out.println("DMRoomDAO:::::"+myId+"/"+roomname);
					
				String sql = "select id from dmroom and roomname=?";

				PreparedStatement psmt = con.prepareStatement(sql);
			//	psmt.setString(1, myId);
				psmt.setString(1, roomname);
				rs = psmt.executeQuery();

				while (rs.next()) {
					DmroomDTO dmr = new DmroomDTO();
				//	dmr.setRoomname(rs.getString("roomname"));
					dmr.setRoomname(rs.getString("id"));
					pList.add(dmr);
				}
			} 
			else {
				String sql = "select * from dmroom where id=?";
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1, s);

				rs = psmt.executeQuery();

				while (rs.next()) {
					DmroomDTO dmr = new DmroomDTO();
					dmr.setRoomname(rs.getString("roomname"));
					dmr.setId(rs.getString("id"));
					pList.add(dmr);
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
