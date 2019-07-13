<span style="font-size:12px;"><span style="font-size:14px;"></span>
<%@ page language="java" import="java.util.*,java.io.*,java.sql.*"  import="javax.swing.JOptionPane,java.awt.Toolkit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 String path =request.getContextPath();
 String  basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>

<!doctype html>
<html lang="en">

<head>
  <title>manager</title>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <link href="assets/css/material-kit.css?v=2.0.5" rel="stylesheet" />
</head>




<body>


  <div class="page-header header-filter " data-parallax="true" style="background-image: url('./assets/img/lemon.jpg');">
    <div class="container">
      <div class="row">
        <div class="col-md-8 ml-auto mr-auto">
          <div class="brand text-center">
            <h1>管理员信息</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="main main-raised">
    <div class="container">
      <div align = "left">
        <button type="button" class="btn btn-outline-warning" onclick="window.location.href='choose.jsp'">返回</button>
   </div>
    
      <br><br>
      <div class="row">
      
    </div>
     <div class="row">
      
          <table class="table" width = "400">
          
              <tr class="table-warning">
                <th scope="col-md-5"  nowrap = "nowrap">用户名</th>
                <th scope="col"  nowrap = "nowrap">密码 </th>
                <th scope="col"  nowrap = "nowrap">姓名 </th>
                <th scope="col"  nowrap = "nowrap">年龄</th>
                <th scope="col"  nowrap = "nowrap">性别</th>
                <th scope="col"  nowrap = "nowrap">邀请码</th>
              </tr>
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
  String username=(String)session.getAttribute("username");
  String password=(String)session.getAttribute("password");
  Statement stmt = connect.createStatement();
  
        ResultSet rs;
        String sql ="";
        sql = "SELECT * FROM manager WHERE DBMID = '"+ username +"' AND DBMPWD = '"+ password +"';";
        rs = stmt.executeQuery(sql);
        rs.next();
      
	   try{
 
      
    %>
            <tbody>
              <tr>
              <td nowrap="nowrap" ><% out.print(rs.getString(1));	%> </td>
              <td nowrap="nowrap" ><% out.print(rs.getString(2));	%> </td>
              <td nowrap="nowrap" ><% out.print(rs.getString(3));	%> </td>
              <td nowrap="nowrap" ><% out.print(rs.getString(4));	%> </td>
              <td nowrap="nowrap" ><% out.print(rs.getString(5));	%> </td>
              <td nowrap="nowrap" ><% out.print(rs.getString(6));	%> </td>
            </tr>
            </tbody>
            
            <br> <br> <br> <br> <br> <br> <br> <br>
            <br> <br> <br> <br> <br> <br> <br> <br>
            <%}
            catch(Exception e){
                  out.println(e.getMessage());
            }
            %>
            </table>

            <br> <br> <br> <br> <br> <br> <br> <br>
          </div>
        </div>
    </div>
  </div>
</body>

</html>