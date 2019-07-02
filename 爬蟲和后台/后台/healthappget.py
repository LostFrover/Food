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
#周梓浩

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        """get请求"""
        localtime = time.asctime(time.localtime(time.time()))
        choise = self.get_argument('choise')
        #5名字模糊查詢,7詳細查詢
        if choise == "5":
            food = self.get_argument('food')
            result = search.searchforAllLike(food)
            if result == -1:
                self.write("failed")
            else:
                self.write(str(result))
        if choise == '7':
            foodname=self.get_argument('foodname')
            foodname=urllib.parse.unquote(foodname, encoding='utf-8')
            result = search.searchfortext(foodname)
            with open("/home/pic/"+foodname+".jpg",'rb') as f:
                base_data=base64.b64encode(f.read())
            self.write(str(result)+" pic:"+str(base_data).lstrip("b'").rstrip("'"))             

application = tornado.web.Application([(r"/get", MainHandler), ])
 
if __name__ == "__main__":
    application.listen(3389)
    tornado.ioloop.IOLoop.instance().start()
