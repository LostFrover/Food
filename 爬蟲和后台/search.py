#encoding:utf-8
import pymysql
#周梓浩
def searchfortext(text):
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
    sql = "select name,heat from food where name like '%s';"%('%'+text+'%')
    cursor.execute(sql)
    result = cursor.fetchall()   
    if len(result)>0:
        db.close();
        return result
    else:
        db.close()
        return -1
