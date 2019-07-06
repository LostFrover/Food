# encoding:utf-8 
import tornado.ioloop
import tornado.web
import pymysql
import time
import account
import re
import search
import healthdata
import base64
import urllib
import test
import findnamewithid
import os
import weight
import diet
#周梓浩


class Reg(tornado.web.RequestHandler):
    def post(self):
        name = self.get_argument('name')
        name=urllib.parse.unquote(name, encoding='utf-8')
        pwd = self.get_argument('pwd')
        if (len(pwd)<8):
            self.write("密碼長度不能少于8位")
            return 0
        phone = self.get_argument('phone')
        if (len(phone)!=11):
            self.write("電話格式不對")
            return 0
        isSuccess = account.regsister(name,pwd,phone)
        if isSuccess:
            self.write("Success")
        else:
            self.write("failed")

class Login(tornado.web.RequestHandler):
    def post(self):
        localtime = time.asctime(time.localtime(time.time()))
        name = self.get_argument('name')
        name=urllib.parse.unquote(name, encoding='utf-8')
        pwd = self.get_argument('pwd')
        result = account.login(name,pwd)
        if result==-1 :
            self.write("Failed")
        else:
            print('用戶ID:',result,'登錄成功 ',localtime)
            self.write(str(result))

class Photo(tornado.web.RequestHandler):
    def post(self):
        localtime = time.asctime(time.localtime(time.time()))
        id = self.get_argument('id')
        pic = self.get_argument('pic')
        pic = str(pic).replace('data:image/jpeg;base64,','')
        pic = base64.b64decode(pic)
        string='tmp/'+str(localtime)+str(id)+'.jpg'
        file=open(string,'wb')
        file.write(pic)
        file.close()
        result=test.pic(string)
        os.remove(string)
        result=findnamewithid.findname(result)
        self.write(str(result))       

class setHealthData(tornado.web.RequestHandler):
    def get(self):
        data={}
        id = self.get_argument('id')
        sex=self.get_argument('sex')
        height = self.get_argument('height')
        weight = self.get_argument('weight')
        waistline = self.get_argument('waistline')
        beat = self.get_argument('beat')
        bodyFat = self.get_argument('bodyFat')
        bloodSugar = self.get_argument('bloodSugar')
        bloodFat = self.get_argument('bloodFat')
        age = self.get_argument('age')
        targetWeight = self.get_argument('targetWeight')
        data['sex'] = sex
        data['height'] = height
        data['weight'] = weight
        data['waistline'] = waistline
        data['beat'] = beat
        data['bodyFat'] = bodyFat
        data['bloodSugar'] = bloodSugar
        data['bloodFat'] = bloodFat
        data['age'] = age
        data['targetWeight'] = targetWeight
        result = healthdata.updateHealthdata(id,data)
        if result:
            self.write("succeess")
        else:
            self.write("failed")

class getHealthData(tornado.web.RequestHandler):
    def get(self):
        id = self.get_argument('id')
        result = healthdata.selectHealthdata(id)
        self.write(str(result))

class changeAccount(tornado.web.RequestHandler):
    def post(self):
        name=self.get_argument('name')
        name=urllib.parse.unquote(name, encoding='utf-8')
        oldpwd=self.get_argument('oldpwd')
        newpwd=self.get_argument('newpwd')
        if (len(newpwd)<8):
            self.write("密碼長度不能少于8")
            return 0
        result = account.changenameandpwd(name,oldpwd,newpwd)
        if result == -1:
            self.write("failed")
        else:
            self.write("success")

class searchFoodForAlikeText(tornado.web.RequestHandler):
    def get(self):
        food = self.get_argument('food')
        result = search.searchforAllLike(food)
        if result == -1:
            self.write("failed")
        else:
            self.write(str(result))

class searchFoodForText(tornado.web.RequestHandler):        
    def get(self):
        foodname=self.get_argument('foodname')
        foodname=urllib.parse.unquote(foodname, encoding='utf-8')
        result = search.searchfortext(foodname)
        try:
            with open("/home/pic/"+foodname+".jpg",'rb') as f:
                base_data=base64.b64encode(f.read())
        except:
            with open("/home/pic/"+nopic+".jpg",'rb') as f:
                base_data=base64.b64encode(f.read())
            db = pymysql.connect(
                host="cdb-0zids4jw.bj.tencentcdb.com",
                port=10107,
                user="root",
                password="pass0word",
                database="HealthApp",
                charset="utf8",
                )
            cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
            sql = 'update food set pic= null where name="%s"'
            cursor.execute(sql,(foodname))
            db.commit()
            db.close();
        self.write(str(result)+" pic:"+str(base_data).lstrip("b'").rstrip("'"))

class downloadApk(tornado.web.RequestHandler):
    def get(self):
        self.set_header('Content-Type', 'application/vnd.android.package-archive')
        self.set_header('Content-Disposition', 'attachment; filename=%s'%'food_master.apk')
        with open('./food_master.apk', 'rb') as f:
            while True:
                data = f.read(1024)
                if not data:
                    break
                self.write(data)
        self.finish()

class Recorddiet(tornado.web.RequestHandler):
    def post(self):
        id= self.get_argument('id')
        food=self.get_argumnet('food')
        type = self.get_argument('type')
        quantity=self.get_argument('quantity')
        diet.recorddiet(id,food,type,quantity)
        self.write("finished")
        
class Recordweight(tornado.web.RequestHandler):
    def post(self):
        id= self.get_argument('id')
        weight=self.get_argument('weight')
        weight.recordweight(id,weight)
        self.write('finished')
    
class getdiet(tornado.web.RequestHandler):
    def get(self):
        id = self.get_argument('id')
        result= diet.getdiet(id)
        return str(result)
        
class getweight(tornado.web.RequestHandler):
    def get(self):
        id = self.get_argument('id')
        result= weight.getweight(id)
        return str(result)


Hander=[(r"/Login", Login), (r"/Reg", Reg),(r"/Photo", Photo),(r"/HealthDataPost", setHealthData),(r"/HealthDataGet",getHealthData),(r"/AccountData", changeAccount),(r"/AlikeText", searchFoodForAlikeText),(r"/Text", searchFoodForText),(r"/APK",downloadApk),(r"/dietGet",getdiet),(r"/weightget",getweight),(r"/recorddiet", Recorddiet),(r"/recordweight", Recordweight)]

application = tornado.web.Application(Hander)
 
if __name__ == "__main__":
    application.listen(3389)
    tornado.ioloop.IOLoop.instance().start()
