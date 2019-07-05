#encoding:utf-8
import pymysql
#周梓浩
def searchforAllLike(text):
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
    )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "select name,heat from food where name like %s;"
    cursor.execute(sql,('%'+text+'%'))
    result = cursor.fetchall()   
    if len(result)>0:
        db.close();
        return result
    else:
        db.close()
        return -1

def searchfortext(foodname):
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
    )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    sql = "select name,heat,advise,type from food where name=%s;"
    cursor.execute(sql,(foodname))
    result = cursor.fetchall()
    if len(result)>0:
        db.close();
        return result
    else:
        db.close()
        return -1
