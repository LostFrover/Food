# encoding:utf-8
import pymysql
import io

# 周梓浩
def findname(idset):
    nameset=[]
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
    )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    for i in range(4):
        id = idset[i]
        sql = "select name from food where id = %s;" % (id)
        cursor.execute(sql)
        result = cursor.fetchall()
        nameset.append(result[0]['id'])
    db.close();
    return nameset

