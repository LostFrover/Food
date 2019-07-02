#encoding:utf-8
import io
import os
import urllib
import io
import requests
from bs4 import BeautifulSoup
from selenium.webdriver.chrome.options import Options
from selenium import webdriver
# 周梓浩

if __name__ == "__main__":
    readfile = io.open("url.txt", 'r', encoding='utf-8')
    file = io.open('picname.txt', 'a+',encoding='utf-8')
    file2= io.open('type.txt', 'a+',encoding='utf-8')
    file3= io.open('advice.txt', 'a+',encoding='utf-8')
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
        file.write(str(name).replace('/','').replace('"','\'').replace('\\','')+'\n')
        file2.write(cate+'\n')
        file3.write(advice+'\n')
        print(name,cate,advice)
    readfile.close()
    file.close()
    file2.close()
    file3.close()
    print("finished")
