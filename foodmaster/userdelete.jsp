<%@ page language="java" import="java.util.*,java.io.*,java.sql.*"  import="javax.swing.JOptionPane,java.awt.Toolkit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <title>删除</title>
</head>
<body>

        <%
        request.setCharacterEncoding("UTF-8");
  String drivername = "com.mysql.jdbc.Driver";
  String userName = "root";
  String userPassWd = "pass0word";
  String dbName = "HealthApp";
  String url = "jdbc:mysql://cdb-0zids4jw.bj.tencentcdb.com";
  Class.forName("com.mysql.jdbc.Driver").newInstance();
  Connection connect = DriverManager.getConnection("jdbc:mysql://cdb-0zids4jw.bj.tencentcdb.com:10107/HealthApp?user=root&password=pass0word&userUnicode=true&characterEncoding=UTF-8");
  request.setCharacterEncoding("UTF-8");
  String id=request.getParameter("id");
  try{
  Statement stmt = connect.createStatement();
  String sql = "DELETE FROM user WHERE userID = '"+ id +"'";
  stmt.executeUpdate(sql);
  out.print("<script LANGUAGE='javascript'>alert('用户删除成功'); window.location.href='userdb.jsp';</script>");
  connect.close();
   }catch(Exception e){
   out.print("数据库连接失败");
   out.print(e.toString());
   } 
   %>


</body>
</html>