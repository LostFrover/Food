#encoding:utf-8
import pymysql
#周梓浩
def login(name, pwd):
    isSuccess = False 
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
    )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "select userID from user where userName = %s and Password = %s;" 
    cursor.execute(sql,(name, pwd))
    result = cursor.fetchall()   
    if len(result)>0:
        db.close();
        return result[0]['userID']
    else:
        db.close()
        return -1

def regsister(name,pwd,phone):
    isSuccess = False 
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
        )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql="insert into user(userID,userName,Password,Phone,Regdate) values (null,%s,%s,%s,curdate());"
    try:
        cursor.execute(sql,(name,pwd,phone))
        db.commit()
        id = login(name,pwd)
        sql = "insert into healthdata(userID,height,weight,waistline,beat,bodyFat,bloodSugar,bloodFat,age,targetWeight) values(%s,null,null,null,null,null,null,null,null,null);"
        cursor.execute(sql,(id))
        db.commit()
        sSuccess = True
    except:
        db.rollback()
        isSuccess = False
    finally:
        db.close();
    return isSuccess

def changenameandpwd(name,oldpwd,newpwd):
    isSuccess = False 
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
    )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "select userID from user where userName = %s and Password = %s;"
    cursor.execute(sql,(name, oldpwd))
    result = cursor.fetchall()   
    if len(result)>0:
        sql="Update user set Password=%s where userName=%s;"
        cursor.execute(sql,(newpwd,name))
        db.commit()
        db.close()
        return "success"	
    else:
        db.close()
        return -1
