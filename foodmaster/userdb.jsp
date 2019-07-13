<%@ page language="java" import="java.util.*,java.io.*,java.sql.*"  import="javax.swing.JOptionPane,java.awt.Toolkit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
  <title>foodmaster</title>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <link href="assets/css/material-kit.css?v=2.0.5" rel="stylesheet" />
  <style type="text/css">
   #box{
     width: 380px;
     margin: 30px auto;
     font-family: 'Microsoft YaHei';
     font-size: 14px;
   }

   input{
     width: 260px;
     border: 1px solid #e2e2e2;
     height: 40px;
     float:left;
    
   }

   #search{
     width: 100px;
     height: 40px;
     float: right;
     background: black;
     color:white;
     text-align: center;
     line-height: 32px;
     cursor: pointer;

   }
    
        
  </style>
</head>

<body>



  <div class="page-header header-filter" data-parallax="true" style="background-image: url('assets/img/food.jpg')">
    <div class="container">
      
      <div class="row">
        <div class="col-md-8 ml-auto mr-auto">
         
          <div class="brand text-center">
            <h1>用户库</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="main main-raised">
    <div class="container">   
            <div align = "left">  <button type="button" class="btn btn-outline-primary" onclick="window.location.href='choose.jsp'">返回</button></div>
      <div class="row">
        <div class="col-lg-4 col-md-6 ml-auto mr-auto">
     
                
          
         <form class="form" action = "userinfo.jsp" method="post">
                <div id= "box" align = "center">
                 <div class="col">
              <input type="text" name="name" placeholder="请输入想要搜索的用户"></div>
               <input type="submit" id = "search" value="搜索">
            </form>
          </div>
                  <br><br><br><br><br> <br><br><br><br><br>
          </div>
        </div>
    </div>
  </div>
</body>

</html>