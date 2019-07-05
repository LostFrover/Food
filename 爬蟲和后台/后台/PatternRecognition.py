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
#周梓浩

class MainPostHandler(tornado.web.RequestHandler):
    def post(self):
        """post请求"""
        localtime = time.asctime(time.localtime(time.time()))
        pic = self.get_argument('pic')
        pic = str(pic).replace('data:image/jpeg;base64,','')
        pic = base64.b64decode(pic)
        string='tmp/'+str(localtime)+'.jpg'
        file=open(string,'wb')
        file.write(pic)
        file.close()
        result=test.pic(string)
        os.remove(string)
        result=findnamewithid.findname(result)
        self.write(str(result))

application = tornado.web.Application([(r"/PatternRecognition", MainPostHandler), ])
 
if __name__ == "__main__":
    application.listen(8080)
    tornado.ioloop.IOLoop.instance().start()
