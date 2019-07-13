<%@ page language="java" import="java.util.*,java.io.*,java.sql.*"  import="javax.swing.JOptionPane,java.awt.Toolkit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
  <title>Food Information</title>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <link href="assets/css/material-kit.css?v=2.0.5" rel="stylesheet" />
</head>

<body>

  <div class="page-header header-filter" data-parallax="true" style="background-image: url('assets/img/BBQ.jpg')">
    <div class="container">
        
      <div class="row">
        <div class="col-md-8 ml-auto mr-auto">
          <div class="brand text-center">

            <h1>用户信息</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="main main-raised">
    <div class="container">
        <button type="button" class="btn btn-outline-warning" onclick="window.location.href='userdb.jsp' ">返回</button>
      <div class="row">
       
        <div class="col-lg-4 col-md-6 ml-auto mr-auto">
        <br>  <br>  <br>
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
  String name=request.getParameter("name");
  Statement stmt = connect.createStatement();
  ResultSet rs;
  String sql;
  sql = "SELECT * FROM user WHERE username LIKE '%"+name+"%';";
  rs = stmt.executeQuery(sql);
  while(rs.next())
  {
  %>

               

      <table class="table table-striped" >
         <tbody>
         
            <tr> <th scope="col"  nowrap = "nowrap">用户名</th><td nowrap="nowrap" ><% out.print(rs.getString(2));	%> </td></tr>
            <tr> <th scope="col"  nowrap = "nowrap">密码 </th> <td nowrap="nowrap" ><% out.print(rs.getString(3));	%> </td>  </tr>
            <tr> <th scope="col"  nowrap = "nowrap">用户id </th>  <td nowrap="nowrap" ><% out.print(rs.getString(1));	%> </td>
            <tr> <th scope="col"  nowrap = "nowrap">电话号码</th>  <td nowrap="nowrap" ><% out.print(rs.getString(4));	%> </td>
            <tr> <th scope="col"  nowrap = "nowrap">  
       <button type="button" class="btn btn-outline-success btn-sm " onclick="window.location ='userdelete.jsp?id=<%=rs.getString(1)%>'">删除</button></th>  
       &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;
       &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;
       &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;   &nbsp;
       <th scope="col"  nowrap = "nowrap">  
          
      <button type="button" class="btn btn-outline-success  btn-sm" onclick="window.location ='userupdate.jsp?id=<%=rs.getString(1)%>'">修改</button></th>
            </tbody>
</table>

              <br><br> <br><br> <br>
         <%
  }%>

              <br>  <br>  <br>  <br>  <br>

                  
              


               
             
           
          </div>
        
    </div>
  </div>

</body>

</html>