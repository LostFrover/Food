# encoding:utf-8
import io
import os
import urllib
import io
import requests
from bs4 import BeautifulSoup

from selenium.webdriver.chrome.options import Options
from selenium import webdriver
# 周梓浩

def getdata(url):
    data={}
    chrome_options=Options()
    chrome_options.add_argument('--headless')
    browser = webdriver.Chrome(chrome_options=chrome_options)
    li_url = "http://www.boohee.com"+url
    browser.get(li_url)
    soup = BeautifulSoup(browser.page_source,'html.parser')
    try:
        firstfind = soup.find(class_='widget-food-detail pull-left')
        cate = first.findAll('a')
        cate = cate[1].text
    except:
        cate=''
    try:
        second = soup.find(class_='widget-food-detail pull-left')
        advice = second.find('p').text
    except:
        advice = ''
    data['pic'] = soup.find(class_='food-pic pull-left').find('img').attrs['src']
    data['name'] = soup.find(class_='food-pic pull-left').find('img').attrs['alt'].replace('\'','').replace('\"','\'')
    data['cate'] = cate
    data['advice'] = advice
    return data
    
if __name__ == "__main__":
    readfile = io.open("url.txt", 'r', encoding='utf-8')
    file = io.open('picname.txt', 'a+',encoding='utf-8')
    file2= io.open('type.txt', 'a+',encoding='utf-8')
    file3= io.open('advice.txt', 'a+',encoding='utf-8')
    text=readfile.readline()
    while True:
        text = readfile.readline()
        if text == "":
            break
        url = text
        data=getdata(url)
        pic_url = data['pic']
        string = "pic" + '\\' +data["name"]+ '.jpg'
        pic = requests.get(pic_url,timeout = 7)
        fp = open(string, 'wb')
        fp.write(pic.content)
        fp.close()
        file.write(data['name']+'\n')
        file2.write(data['cate']+'\n')
        file3.write(data['advice']+'\n')
        print(data['cate'],data['advice'])
    readfile.close()
    file.close()
    file2.close()
    file3.close()
    print("finished")
