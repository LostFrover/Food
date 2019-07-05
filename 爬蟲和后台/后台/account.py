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

def regsister(name,pwd,email):
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
    sql="insert into user(userID,userName,Password,email) values (null,%s,%s,%s);"
    try:
        cursor.execute(sql,(name,pwd,email))
        db.commit()
        id = login(name,pwd)
        sql = "insert into healthdata(userID,height,weight,waistline,beat,bodyFat,bloodSugar,bloodFat) values(%s,null,null,null,null,null,null,null);"
        try:
            cursor.execute(sql,(id))
            db.commit()
            isSuccess = True
        except:
            db.rollback()
            isSuccess = False
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
