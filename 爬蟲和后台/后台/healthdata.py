#encoding:utf-8
import pymysql
import time
#周梓浩
def updateHealthdata(id,data):
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
    sql = "update healthdata set height = %s,weight = %s,waistline = %s,beat=%s,bodyFat = %s,bloodSugar = %s,bloodFat = %s,sex=%s,age=%s,targetWeight=%s where userId =%s;"
    cursor.execute(sql,(data['height'],data['weight'],data['waistline'],data['beat'],data['bodyFat'],data['bloodSugar'],data['bloodFat'],data['sex'],data['age'],data['targetWeight'],id))
    db.commit()
    sql="insert into weight(id,user_id,weight,date) values(null,%s,%s,curdate());"
    cursor.execute(sql,(id,data['weight']))
    db.commit()
    isSuccess = True
    db.close();
    return isSuccess

def selectHealthdata(id):
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
    sql="select * from healthdata where userID = %s;"
    cursor.execute(sql,(id))
    result = cursor.fetchall()   
    db.close();
    return result
    
