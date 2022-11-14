package com.aakash.practiceproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController
{
	@GetMapping("/add_employee")
	public Map addEmployee(HttpServletRequest req,details d) throws ClassNotFoundException, SQLException
	{
//		String name = req.getParameter("empname");
//		String emailid = req.getParameter("email");
//		String rupee = req.getParameter("salary");
//		String date = req.getParameter("joiningdate");
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660");
		String query = "insert into employee_management(Employee_Name,Email,Salary,Joining_Date) values(?,?,?,CURDATE())";
			
		PreparedStatement pstmt = c.prepareStatement(query);
		pstmt.setString(1, d.getEmpname());
		pstmt.setString(2, d.getEmail());
		pstmt.setInt(3, d.getSalary());
		
		HashMap map = new HashMap<>();
		int i = pstmt.executeUpdate();
		if(i>=1)
		{
			map.put("mssg","employee added successfully");
			return map ;
		}
		else 
		{
			map.put("mssg", "information inappropriate");
			return map ;
		}
	}
	
	
	
	
	@GetMapping("/searchEmployeeInfo")
	public List searchInfo(details d) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660");
		Statement stmt = c.createStatement();
		String  query = "select * from employee_management where Email = '"+d.getEmail()+"'"  ;
		ResultSet rs = stmt.executeQuery(query);
		HashMap map = new HashMap<>();
		ArrayList list = new ArrayList<>();
		
		if(rs.next())
		{
			map.put("Name", rs.getString("Employee_Name"));
			map.put("salary", rs.getString("Salary"));
			map.put("date of joining", rs.getString("Joining_Date"));
			list.add(map);
		}
		return list ;
	}
	
	
	
	
	@GetMapping("/deleteemployee")
	public Map deleteEmployeeInfo(details d) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660");
		String query = "delete from employee_management where  Email = '"+d.getEmail()+"' " ;
		PreparedStatement pstmt = c.prepareStatement(query);
		int j = pstmt.executeUpdate();
		HashMap map = new HashMap<>();
		
		if(j>=1)
		{
			map.put("mssg","employee deleted successfully");
		}
		else 
		{
			map.put("mssg","something went wrong");
		}
		
		
		return map;
	}
	
	
	
	

	@GetMapping("/modifyemployeedetails")
	public Map modifyEmployeeDetails(HttpServletRequest req,details d) throws SQLException, ClassNotFoundException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660");
		Statement stmt = c.createStatement();
		String query = "select * from employee_management where Joining_Date='"+d.getJoiningdate()+"' ";
		ResultSet rs = stmt.executeQuery(query);
		HashMap map = new HashMap<>();
		
		if(rs.next())
		{
			Date dat = d.getJoiningdate();
			String query1 = "update employee_management set Employee_Name=?,Email=?,Salary=? where Joining_Date= '"+dat+"'  " ;
			PreparedStatement pstmt = c.prepareStatement(query1);
			pstmt.setString(1, d.getEmpname());
			pstmt.setString(2, d.getEmail());
			pstmt.setInt(3, d.getSalary());
			
			int i = pstmt.executeUpdate();
			
			if(i>=1)
			{
				map.put("mssg","updated successfully");
				return map ;
			}
			else 
			{
				map.put("mssg","error");
				return map ;
			}
					
		}
		return map ;
	}
	
	
	
	
	
  @PostMapping("/addnotice")
  public Map add_notice(HttpServletRequest req) throws ClassNotFoundException, SQLException {
  String n=req.getParameter("notice");
//  String time=req.getParameter(n)
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660");
	String query = "insert into notice(timee,notice) values(CURDATE(),?)";
	PreparedStatement stmt1=con.prepareStatement(query);
	
//	stmt1.setString(1,n);
	stmt1.setString(1,n);
	int i=stmt1.executeUpdate();
	HashMap map=new HashMap();
	if(i>=1)
	{
		map.put("msg","Notice added");
		return map;
	}
	return map;
  }
  
  
  
  
  @PostMapping("/delete_notice")
  public Map deleteNotice(HttpServletRequest req) throws ClassNotFoundException, SQLException {
    String num=req.getParameter("serial_num");
//  String time=req.getParameter👎
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660");
	String query = "delete from notice where timee=?";
	PreparedStatement stmt1=con.prepareStatement(query);
	
//	stmt1.setString(1,n);
	stmt1.setString(1,num);
	int i=stmt1.executeUpdate();
	HashMap map=new HashMap();
	if(i>=1)
	{
		map.put("msg","Notice deleted");
		return map;
	}
  return map;
  }
	
	
  
	
	@GetMapping("/get_notice")
	public List fetchNotice(HttpServletRequest req) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660");
		Statement stmt=con.createStatement();
		String query="select * from notice";
		ResultSet rs=stmt.executeQuery(query);
		ArrayList list=new ArrayList();
//		HashMap map=new LinkedHashMap();
		while(rs.next())
		{
			HashMap map=new LinkedHashMap();
			map.put("Date",rs.getDate("timee"));
			map.put("Notice",rs.getString("notice"));
			rs.getDate("timee");
			list.add(map);
		}
		return list;
		
	}
	
	
	
	
	
	@PostMapping("/User_SignUp")
	public Map details(HttpServletRequest req) throws ClassNotFoundException, SQLException
	{
		String u = req.getParameter("username");
		String p = req.getParameter("password");
		String permissions = req.getParameter("user or owner");
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root","aakash9660");                                 
		Statement stmt = con.createStatement();
		String query = "select * from user where username =  '"+u+"'";
		ResultSet rs = stmt.executeQuery(query);
		HashMap map = new HashMap<>();
		if(rs.next())
		{
			map.put("mssg1","you are already signed up please try to login : ");
			return map ;
		}
		else
		{
			String query1 = "Insert into user (username,password,permissions) values (?,?,?) ";
			PreparedStatement pstmt = con.prepareStatement(query1);
			pstmt.setString(1, u);
			pstmt.setString(2, p);
			pstmt.setString(3, permissions);
			
			int i = pstmt.executeUpdate();
			if(i>=1)
			{
				map.put("mssg2", "you are signed up successfully");
				return map ;
			}
			else 
			{
				map.put("mssg3", "something went wrong try later : ");
				return map ;
			}
		}
	}
	
	
	
	
	@PostMapping("/User_login")
	public Map credential(HttpServletRequest req) throws ClassNotFoundException, SQLException
	{
		String u = req.getParameter("username");
		String p = req.getParameter("password");
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root","aakash9660");                                 
		Statement stmt = con.createStatement();
		String query = "select * from user where username =  '"+u+"' OR password = '"+p+"'";
		ResultSet rs = stmt.executeQuery(query);
		HashMap map = new HashMap<>();
		if(rs.next())
		{
			if(rs.getString("username").equals(u))
			{
				if(rs.getString("password").equals(p))
				{
					map.put("mssg1", "you are logged in ");
					return map ;
				}
				else
				{
					map.put("mssg2", "password is incorrect ");
					return map ;
				}
			}
		}
		else
		{
			map.put("mssg3", "first try to sign up ");
			return map ;
		}
		return map ;
	}
	
	
	
	
	
	@PostMapping("/ForgotPassword")
	public Map credentialUpdate(HttpServletRequest req) throws ClassNotFoundException, SQLException
	{
		String u = req.getParameter("username");
		String p = req.getParameter("password");
		String p1 = req.getParameter("new password");
		String p2 = req.getParameter("new password");
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root","aakash9660");                                 
		Statement stmt = con.createStatement();
		String query = "select * from user where username =  '"+u+"' AND password = '"+p+"'" ;
		ResultSet rs = stmt.executeQuery(query);
		HashMap map = new HashMap<>();
		if(rs.next())
		{
			if(rs.getString("username").equals(u))
			{
				if(rs.getString("password").equals(p))
				{
					String query1 = "update  user set password = ? where username = '"+u+"'" ;
					PreparedStatement pstmt = con.prepareStatement(query1);
					pstmt.setString(1, p1);
					int i = pstmt.executeUpdate();
					
					if(i>=1)
					{
						map.put("mssg1", "password changed successfully");
						return map ;
					}
				}
			}
		}
		return map ;
	}
	
	
	
	
	@PostMapping("/Logged out")
	public String loggedOut(HttpServletRequest req) throws ClassNotFoundException, SQLException
	{
		String f = req.getParameter("flag"); 
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant","root","aakash9660");  
		HashMap map = new HashMap();
		
		if(f.equals("1"))
		{
			try
			{
				System.out.println(10/0);
			}
//			catch(ArithmeticException ae)
//			{
//				return "hello" ;
//			}
			finally 
			{
				System.out.println("logged out");
			}
		}	
		return null ;
	}
	
	
	
	
	
	@GetMapping("/leavegranted")
	public Map leave(HttpServletRequest req) throws SQLException, ClassNotFoundException
	{
		String id = req.getParameter("ID");
		String r = req.getParameter("reason");
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660"); 
		HashMap map = new HashMap<>();
		if(r.equals("casual"))
		{
			String query = "insert into employeeleave (employee_id,casual) values(?,1)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			int i = pstmt.executeUpdate();
			if(i>=1)
			{
				map.put("mssg", "leave granted");
				return map ;
			}
		}
		else 
		{
			String query = "insert into employeeleave (employee_id,medical) values(?,1)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			int i = pstmt.executeUpdate();
			if(i>=1)
			{
				map.put("mssg", "leave granted");
				return map ;
			}
		}
		return map ;
	}
	
	
	
	
	
	
	@GetMapping("/totalleave")
	public List totalLeave(HttpServletRequest req) throws SQLException, ClassNotFoundException
	{
		String id = req.getParameter("ID");
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Management","root","aakash9660"); 
		String query = "select count(*) as total_leave from employeeleave where employee_id="+id;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ArrayList list = new ArrayList<>();
		if(rs.next())
		{
			list.add(rs.getInt("total_leave"));
			return list ;
		}
		return list ;
	}
	
	
}














