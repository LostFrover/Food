<%@ page language="java" import="java.util.*,java.io.*,java.sql.*"  import="javax.swing.JOptionPane,java.awt.Toolkit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
  <title>Choose one!</title>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <link href="assets/css/material-kit.css?v=2.0.5" rel="stylesheet" />
</head>

<body>

    <div class="page-header header-filter" data-parallax="true" style="background-image: url('./assets/img/makalong.jpg');">
    <div class="container">
      <div class="row">
        <div class="col-md-8 ml-auto mr-auto">
          <div class="brand text-center">
            <h1>绿查</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="main main-raised">
    <div class="container">
     
         <div class="row">
           <div><h3>欢迎登陆管理员</h3></div>
           
           <div class="col" id = "name"><h3>
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
  String username=request.getParameter("username");
  String password=request.getParameter("password");
  Statement stmt = connect.createStatement();
  
        ResultSet rs;
        String sql;
        try{
        sql = "SELECT * FROM manager WHERE DBMID = '"+ username +"' AND DBMPWD = '"+ password +"';";
        rs = stmt.executeQuery(sql);
        rs.next();
        out.print(rs.getString(3)); 
      }
	   
      catch(Exception e){
          out.print("出现异常，失败");
         e.printStackTrace();
       }
      
    %>
  </h3>

          </div>
          </div>
          <br> <br> <br> 
          <div class="row">
          <div class = "col" align = "center">
                
          <button type="button" class=" col-md-5 btn btn-outline-warning btn-lg  " style="margin-right: 10px" onclick="window.location.href= 'manager_home.jsp'"><h2>个人资料</h2></button>
  
          <button type="button" class=" col-md-5 btn btn-outline-success btn-lg " onclick="window.location.href= 'fooddb.jsp'"><h2>食品库</h2></button>
          </div></div>
        
      <div class="row">
            <div class =  "col " align="center">
              <button type="button" class="col-md-5 btn   btn-outline-info btn-lg"  style="margin-right: 10px" onclick="window.location.href='suggestion.jsp'"><h2>建议审查</h2></button>
              <button type="button" class="col-md-5 btn   btn-outline-primary btn-lg" onclick="window.location.href = 'userdb.jsp'"><h2>用户库</h2></button>               
              <a href="suggestion.jsp.jsp?username=<%=username%>"></a>
              <a href="suggestion.jsp.jsp?password=<%=password%>"></a>
            
            </div>
        </div>
        </div>
        <br> <br> <br> <br>        <br> <br> <br> <br> 

        </div>
    </div>
  </div>

</body>

</html>