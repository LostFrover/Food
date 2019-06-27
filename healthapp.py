# encoding:utf-8 
import tornado.ioloop
import tornado.web
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
    sql = "select userID from user where userName = %s and Password = %s;" % (name, pwd)
    cursor.execute(sql)
    result = cursor.fetchall()   
    print(result[0]['userID'])
    if result.count()>0:
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
    sql="insert into user(userID,userName,Password,email) values (null,%s,%s,%s);"%(name,pwd,email)
    try:
        cursor.execute(sql)
        db.commit()
        isSuccess = True
    except:
        db.rollback()
        isSuccess = False
    finally:
        db.close();
    return isSuccess

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        """get请求"""
        choose = self.get_argument('choose')
        #0注册,1登錄,2查詢,3登記健康信息,4查詢健康信息
        if choose == "0":
            name = self.get_argument('name')
            pwd = self.get_argument('pwd')
            email = self.get_argument('email')
            isSuccess = regsister(name,pwd,email)
            print(name,pwd,email)
            if isSuccess:
                self.write("Success")
            else:
                self.write("failed")
        if choose == "1":
            name = self.get_argument('name')
            pwd = self.get_argument('pwd')
            result = login(name,pwd)
            print(result)
            if result==-1 :
                self.write("Failed")
            else:
                self.write(result)
        if choose == "2":
            print("todo")
        if choose == "3":
            print("todo")
        if choose == "4":
            print("todo")
application = tornado.web.Application([(r"/healthapp", MainHandler), ])
 
if __name__ == "__main__":
    application.listen(3389)
    tornado.ioloop.IOLoop.instance().start()
