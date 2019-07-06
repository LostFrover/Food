# encoding:utf-8
import pymysql
import io

# 周梓浩
def findname(idset):
    foodset=[]
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
    )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    for i in range(5):
        food={}
        id = idset[i]
        sql = "select name,heat from food where id = %s;" 
        cursor.execute(sql,(id))
        result = cursor.fetchall()
        food['name']=result[0]['name']
        food['heat']=result[0]['heat']
        foodset.append(food)
    db.close();
    return foodset

