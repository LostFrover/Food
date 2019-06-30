# encoding:utf-8 
import tornado.ioloop
import tornado.web
import pymysql
import time
import account
import re
import search
import healthdata
import json
#周梓浩

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        """get请求"""
        localtime = time.asctime(time.localtime(time.time()))
        choise = self.get_argument('choise')
        #0注册,1登錄,2相片查詢,3登記健康信息,4查詢健康信息,5名字查詢
        if choise == "0":
            name = self.get_argument('name')
            pwd = self.get_argument('pwd')
            if (len(pwd)<8):
                self.write("密碼長度不能少于8")
                return 0
            email = self.get_argument('email')
            p = re.compile(r"[^@]+@[^@]+\.[^@]+")
            if not p.match(email):
                self.write("邮件不合乎格式")
                return 0
            isSuccess = account.regsister(name,pwd,email)
            if isSuccess:
                self.write("Success")
            else:
                self.write("failed")
        if choise == "1":
            name = self.get_argument('name')
            pwd = self.get_argument('pwd')
            result = account.login(name,pwd)
            if result==-1 :
                self.write("Failed")
            else:
                print('用戶ID:',result,'登錄成功 ',localtime)
                self.write(str(result))
        if choise == "2":
            print("todo")
        if choise == "3":  
            data={}
            id = self.get_argument('id')
            height = self.get_argument('height')
            weight = self.get_argument('weight')
            waistline = self.get_argument('waistline')
            beat = self.get_argument('beat')
            bodyFat = self.get_argument('bodyFat')
            bloodSugar = self.get_argument('bloodSugar')
            bloodFat = self.get_argument('bloodFat')
            data['height'] = height
            data['weight'] = weight
            data['waistline'] = waistline
            data['beat'] = beat
            data['bodyFat'] = bodyFat
            data['bloodSugar'] = bloodSugar
            data['bloodFat'] = bloodFat
            result = healthdata.updateHealthdata(id,data)
            if result:
                self.write("succeess")
            else:
                self.write("failed")
        if choise == "4":
            id = self.get_argument('id')
            result = healthdata.selectHealthdata(id)
            self.write(str(result))
        if choise == "5":
            food = self.get_argument('food')
            result = search.searchfortext(food)
            if result == -1:
                self.write("failed")
            else:
                self.write(json.dumps(result,ensure_ascii=False))

application = tornado.web.Application([(r"/HealthApp", MainHandler), ])
 
if __name__ == "__main__":
    application.listen(3389)
    tornado.ioloop.IOLoop.instance().start()
