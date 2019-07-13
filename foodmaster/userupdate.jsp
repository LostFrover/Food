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
            <h1>修改</h1>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="main main-raised">
    <div class="container">
            <form class="form" method="post" action="update_user.jsp">
                           <br>  <br>  <br>  <br>  <br>  <br>  <br>  <br>
                    <div class="input-group">
                      <div class="input-group-prepend">
                        <span class="input-group-text">
                         <div>用户名</div>
                        </span>
                      </div>
                      <div class="col-5" align="center">
                      <input type="text" class="form-control" name = "newname" placeholder="新用户名...">
                     </div>
                    </div>
                    <br>  <br>  <br>  
                    <div class="input-group">
                      <div class="input-group-prepend">
                        <span class="input-group-text">
                    <div>密码&nbsp&nbsp;</div>
                        </span>
                      </div>
                      <div class="col-5" align="center">
                      <input type="text" class="form-control" name = "newpassword" placeholder="新密码...">
                    </div>
                    </div>
                   
                    <br>  <br>  <br>   
                  <div class="input-group">
                          <div class="input-group-prepend">
                            <span class="input-group-text">
                             <div>电话</div>
                            </span>
                          </div>
                          <div class="col-5" align="center">
                          <input type="text" class="form-control" name = "newphone" placeholder="新电话...">
                        </div>
                      </div>
                        <br>  <br>  <br>  
                 <div id="button>s" class="cd-section offset-md-4">
                      <button class="btn btn-primary btn-link "><h3>确定</h3></button>
                  </div>
                </div>
     </div>
                      
    
                    </div>
                  </div>
                </form>
       
          
         
    
         
            
            </div>
        </div>
        </div>
        <br> <br> <br> <br>        <br> <br> <br> <br> 

        </div>
    </div>
  </div>

</body>

</html>