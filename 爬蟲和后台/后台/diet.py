# encoding:utf-8
import pymysql

#周梓浩

def recorddiet(id,food,type,quantity):
    db = pymysql.connect(
                host="cdb-0zids4jw.bj.tencentcdb.com",
                port=10107,
                user="root",
                password="pass0word",
                database="HealthApp",
                charset="utf8",
                )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "insert into diet(id,user_id,food,type,quantity,date) values(null,%s,%s,%s,%s,curdate());"
    cursor.execute(sql,(id,food,type,quantity))
    db.commit()
    db.close()

def getdiet(id):
    db = pymysql.connect(
                host="cdb-0zids4jw.bj.tencentcdb.com",
                port=10107,
                user="root",
                password="pass0word",
                database="HealthApp",
                charset="utf8",
                )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "select * from diet where user_id = %s ;"
    cursor.execute(sql,(id))
    result=cursor.fetchall()
    db.close()
    return result
