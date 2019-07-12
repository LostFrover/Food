# encoding:utf-8
import pymysql

#周梓浩

def recordweight(id,weight):
    db = pymysql.connect(
                host="cdb-0zids4jw.bj.tencentcdb.com",
                port=10107,
                user="root",
                password="pass0word",
                database="HealthApp",
                charset="utf8",
                )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "insert into weight(id,user_id,weight,date) values(null,%s,%s,curdate()));"
    cursor.execute(sql,(id,weight))
    db.commit()
    db.close()

def getweight(id):
    db = pymysql.connect(
                host="cdb-0zids4jw.bj.tencentcdb.com",
                port=10107,
                user="root",
                password="pass0word",
                database="HealthApp",
                charset="utf8",
                )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "select * from weight where user_id = %s  order by date desc,id desc;"
    cursor.execute(sql,(id))
    result=cursor.fetchall()
    db.close()
    return result
