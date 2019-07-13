<%@ page language="java" import="java.util.*,java.io.*,java.sql.*"  import="javax.swing.JOptionPane,java.awt.Toolkit" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">

<head>
  <title>user</title>
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

            <h1>建议审查</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="main main-raised">
    <div class="container">
        <button type="button" class="btn btn-outline-warning" onclick="window.location.href='choose.jsp'">返回</button>
      <div class="row">
       
        <div class="col-lg-4 col-md-6 ml-auto mr-auto">
            <form class="form" method=""action="">
        <br>  <br>  <br>
   
              <table class="table table-striped" width="1000" >
                <thead>
                
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">食品名</th>
                    <td>猪饲料</td>
                  
                  </tr>
              
              
                </tbody>
              </table>
         

              <br>  <br>  <br>  <br>  <br>
          <div class="row text-primary"><strong>请输入建议</strong><br><br>
              <input type="text" class="form-control" placeholder="不要再吃猪饲料了...">
             </div>
             <div id="button>s" class="cd-section offset-md-4">
               <br><br>
                <button class="btn btn-primary btn-link "><h3>确定</h3></button>
            </div>
             <br>  <br>  <br>  <br>  <br><br>  <br>  <br>  <br>  <br>

                </div>
             
            </form>
          </div>
        
    </div>
  </div>

</body>

</html>