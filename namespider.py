# encoding: utf-8
import io
import os
import urllib
from time import sleep
import requests
from bs4 import BeautifulSoup
import baiduspider
#周梓浩
def craw(url):
    namelist = []
    try:
        Html = requests.get(url,timeout = 7)
    except Exception:
        print ("network error")
        return 0
    soup = BeautifulSoup(Html.text,'html.parser')
    firstfind = soup.find(class_="food-list")
    for link in firstfind.findAll('img'):
        namelist.append(link.attrs["alt"])
    file = io.open('./name.txt', 'a+',encoding='utf-8')
    for name in namelist:
        file.writelines(name+'\n')
    file.close()

if __name__ == '__main__':  # 主函数入口
    listnum = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    for each in listnum:
        for page_num in range(1, 10, 1):
            li_url = "http://www.boohee.com/food/group/" + str(each) + "?page=" + str(page_num)
            craw(li_url)
            sleep(1)
    for page_num in range(1, 10, 1):
        li_url = "http://www.boohee.com/food/view_menu/" + "?page=" + str(page_num)
        craw(li_url)
    print("finished")

    
