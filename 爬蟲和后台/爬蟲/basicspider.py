#encoding:utf-8
import io
import os
import urllib
import io
import requests
from bs4 import BeautifulSoup
from selenium.webdriver.chrome.options import Options
from selenium import webdriver
import pymysql
# 周梓浩

if __name__ == "__main__":   
    db = pymysql.connect(
        host="cdb-0zids4jw.bj.tencentcdb.com",
        port=10107,
        user="root",
        password="pass0word",
        database="HealthApp",
        charset="utf8",
    )
    cursor = db.cursor(cursor=pymysql.cursors.DictCursor)
    readfile = io.open("url.txt", 'r', encoding='utf-8')
    while True:
        text = readfile.readline()
        if text == "":
            break
        url = text
        data={}
        chrome_options=Options()
        chrome_options.add_argument('--headless')
        browser = webdriver.Chrome(chrome_options=chrome_options)
        li_url = "http://www.boohee.com"+url
        browser.get(li_url)
        soup = BeautifulSoup(browser.page_source,'html.parser')
        firstfind = soup.find(class_='widget-food-detail pull-left')
        try:
            cate = firstfind.findAll('a')
            cate = cate[1].text
        except:
            cate='其他'
        try:
            second = soup.find(class_='widget-food-detail pull-left')
            advice = second.find('p').text.replace('\n','')
        except:
            advice = '評价:'
        try:
            name = soup.find(class_='food-pic pull-left').find('img').attrs['alt']
        except:
            continue
        try:
            sql = 'Update food set type = "%s", advise="%s" where name="%s";' % (cate,advice,str(name).replace('/','').replace('"','\'').replace('\\',''))
            cursor.execute(sql)
            db.commit()
        except:
            continue
    db.close();
    readfile.close()
    print("finished")
