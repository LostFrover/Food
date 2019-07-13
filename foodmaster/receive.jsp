<%@ page language="java" import="java.util.*,java.io.*,java.sql.*"  import="javax.swing.JOptionPane,java.awt.Toolkit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
  <head>    <title>验证页面</title>  </head>
   
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

   String DBMID=request.getParameter("username");
   String DBMPWD=request.getParameter("password");
   session.setAttribute("username",DBMID);
   session.setAttribute("password",DBMPWD);
   String sql="select *from manager where DBMID=? and DBMPWD=?";
   PreparedStatement pstmt=connect.prepareStatement(sql);
   pstmt.setString(1,DBMID);
   pstmt.setString(2,DBMPWD);
   ResultSet rs=pstmt.executeQuery();
   if(rs.next())
   {
   if(rs!=null){rs.close();}
   if(pstmt!=null){pstmt.close();}
   if(connect!=null){connect.close();}
   
   %>    
   
   <jsp:forward page="choose.jsp"/>
   <a href="choose.jsp?username=<%=DBMID%>"></a>
   <a href="choose.jsp?password=<%=DBMPWD%>"></a>
  <%} 
     else   {
    //Toolkit.getDefaultToolkit().beep();
  //JOptionPane.showMessageDialog(null, "账号、密码错误", "Error", JOptionPane.ERROR_MESSAGE); 
  %>
  <div class="test-alert">
    <button class="showMore">查看更多</button>
    <div id="mask"></div>
    <div id="alert-container" style="display: none;">
        <div class="alert-content"></div>
        <div id="close-alert-container">收起</div>
    </div>
</div>
  <jsp:forward page="failed.jsp"/>
  <%
  
   if(rs!=null){rs.close();}
   if(pstmt!=null){pstmt.close();}
   if(connect!=null){connect.close();}
   %>
  <%} %>
   
  </body>
</html>
 