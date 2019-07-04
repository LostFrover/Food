#encoding:utf-8
import pymysql
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
    sql = "update healthdata set height = %s,weight = %s,waistline = %s,beat=%s,bodyFat = %s,bloodSugar = %s,bloodFat = %s,sex=%s where userId =%s;"
    try:
        cursor.execute(sql,(data['height'],data['weight'],data['waistline'],data['beat'],data['bodyFat'],data['bloodSugar'],data['bloodFat'],data['sex'],id))
        db.commit()
        isSuccess = True
    except:
        db.rollback()
        isSuccess = False
    finally:
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
    
