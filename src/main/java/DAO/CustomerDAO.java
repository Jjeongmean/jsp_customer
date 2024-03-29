package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.Customer;

public class CustomerDAO {
	final static String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String JDBC_URL ="jdbc:oracle:thin:@localhost:1521:xe";
	
	//데이터 베이스 연결 메소드
	public Connection open() {
			Connection conn = null;
			
			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(JDBC_URL, "test", "test1234");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return conn; //데이터베이스 연결 객체를 리턴
		}
		
		
	//고객 리스트 확인하는 메소드
	public ArrayList<Customer> getList() throws Exception {
			Connection conn = open(); //DB 커넥션 열기
			ArrayList<Customer> customerList = new ArrayList<>(); //Board 객체를 저장할 ArrayList
			
			String sql = "SELECT id, name, address, phone, gender, age, img, point, grade FROM CUSTOMER ORDER BY id"; //쿼리문
			PreparedStatement pstmt = conn.prepareStatement(sql); //쿼리문 등록
			ResultSet rs = pstmt.executeQuery(); //쿼리문 실행 -> 데이터베이스 결과 저장
			
			/*
			 	try {...} catch(Exception e) {...}
			 	finally {
			 		conn.close();
			 		pstmt.close();
			 		rs.close();
			 	}
			 */
			
			//Exception 에서 사용하는 리소스 자동 닫기(try-with-resource)
			try(conn; pstmt; rs) {
				while (rs.next()) {
					Customer c = new Customer();
					
					c.setId(rs.getInt("id"));
					c.setName(rs.getString("name"));
					c.setAddress(rs.getString("address"));
					c.setPhone(rs.getString("phone"));
					c.setGender(rs.getString("gender"));
					c.setAge(rs.getInt("age"));
					c.setImg(rs.getString("img"));
					c.setPoint(rs.getInt("point"));
					c.setGrade(rs.getString("grade"));
					
					customerList.add(c);
					
				}
			} 
			
			return customerList;

		}


	//고객 정보 확인하는 메소드
	public Customer getView(int id) throws Exception {
			Connection conn = open();
			Customer c = new Customer();
			
			String sql = "SELECT id, name, address, phone, gender, age, img, point, grade FROM CUSTOMER WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id); //물음표에 들어갈 값을 반드시 먼저 지정
			ResultSet rs = pstmt.executeQuery();
			
			try(conn; pstmt; rs) {
				while(rs.next()) {
					c.setId(rs.getInt("id"));
					c.setName(rs.getString("name"));
					c.setAddress(rs.getString("address"));
					c.setPhone(rs.getString("phone"));
					c.setGender(rs.getString("gender"));
					c.setAge(rs.getInt("age"));
					c.setImg(rs.getString("img"));
					c.setPoint(rs.getInt("point"));
					c.setGrade(rs.getString("grade"));
				}
			}
			
			return c;
		}

	
	//게시판 글등록 메소드
	public void insertCustomer(Customer c) throws Exception {
		Connection conn = open();
		String sql = "insert into CUSTOMER values(customer_seq.nextval, ? , ? , ? , ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try (conn; pstmt) {
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getAddress());
			pstmt.setString(3, c.getPhone());
			pstmt.setString(4, c.getGender());
			pstmt.setInt(5, c.getAge());
			pstmt.setString(6, c.getImg());
			pstmt.setInt(7, c.getPoint());
			pstmt.setString(8, c.getGrade());
			pstmt.executeUpdate();
		}
		
	}
	

	//고객 정보 수정 메소드
	public void updateCustomer(Customer c) throws Exception {
			Connection conn = open();
			String sql = "UPDATE customer SET name=?, address=?, phone=?, gender=?, age=?, img=?, point=?, grade=? WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			try(conn; pstmt) {
				pstmt.setString(1, c.getName());
				pstmt.setString(2, c.getAddress());
				pstmt.setString(3, c.getPhone());
				pstmt.setString(4, c.getGender());
				pstmt.setInt(5, c.getAge());
				pstmt.setString(6, c.getImg());
				pstmt.setInt(7, c.getPoint());
				pstmt.setString(8, c.getGrade());
				pstmt.setInt(9, c.getId());
				pstmt.executeUpdate();
			}
			
		}
		
	
	//고객 정보 삭제 메소드
	public void deleteCustomer(int id) throws Exception {
			Connection conn = open();
			String sql = "DELETE FROM customer WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			try(conn; pstmt) {
				pstmt.setInt(1, id);
				
				//삭제된 정보가 없을 경우
				if(pstmt.executeUpdate() != 1) {
					throw new Exception("삭제된 정보가 없습니다.");
				}
			}
		}

	}

