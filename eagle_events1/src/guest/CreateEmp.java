package guest;

import java.sql.*;

public class CreateEmp {
	private int eid,salary;
	private String ename,desig,pass;
	private Connection con;
	private String email;
	
   public CreateEmp(int id,String name,int sal, String desig, String pass, String email,Connection con) {
      this.eid = id;
      this.ename = name;
      this.salary = sal;
      this.desig = desig;  
      this.pass=pass;
      this.con = con;
      this.email = email;
   }
   
   public String insertEmp(){
	   try{
		   Statement st = this.con.createStatement();
		   st.executeUpdate("INSERT INTO `eagle_events`.`employee` (`name`,`salary`,`designation`,`password`,email) VALUES ('"+this.ename+"',"+this.salary+",'"+this.desig+"','"+this.pass+"','"+this.email+"')");
		   return "Successfully stored data for "+this.ename;
	   }catch(SQLException e){
		   e.printStackTrace();
		   return "";
	   }
   }
   
   public String updateEmp(){
	   try{
		   Statement st = this.con.createStatement();
		   st.executeUpdate("UPDATE `eagle_events`.`employee` SET `name` = '"+this.ename+"', `salary` = "+this.salary+" , `designation` = '"+this.desig+"', `password` = '"+this.pass+"', `email` = '"+this.email+"' WHERE `id` = "+this.eid);
		   return "Successfully updated data for "+this.ename;
	   }catch(SQLException e){
		   e.printStackTrace();
		   return "";
	   }
   }
   
   public String deleteEmp(){
	   try{
		   Statement st = this.con.createStatement();
		   st.executeUpdate("DELETE FROM `eagle_events`.`employee` WHERE id="+this.eid);
		   return "Successfully deleted data for "+this.ename;
	   }catch(SQLException e){
		   e.printStackTrace();
		   return "";
	   }
   }
}