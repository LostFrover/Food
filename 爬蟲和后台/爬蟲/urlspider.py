# encoding: utf-8
import io
import os
import urllib
from time import sleep
import requests
from bs4 import BeautifulSoup
from selenium.webdriver.chrome.options import Options
from selenium import webdriver
#周梓浩
if __name__ == '__main__':  # 主函数入口
    for each in range(1,131):
        for page_num in range(1, 10, 1):
            food={}
            chrome_options=Options()
            chrome_options.add_argument('--headless')
            browser = webdriver.Chrome(chrome_options=chrome_options)
            li_url = "http://www.boohee.com/food/view_group/" + str(each) + "?page=" + str(page_num)
            print(li_url)
            browser.get(li_url)
            soup = BeautifulSoup(browser.page_source,'html.parser')
            try:
                firstfind = soup.find(class_="food-list")
                for item in firstfind.findAll(class_ ="item clearfix"):
                    try:
                        name = item.find('img').attrs["alt"]
                        url1=item.find(class_='img-box pull-left')
                        url2= url1.find('a').attrs['href']
                        food[name] = url2
                    except:
                        continue
            except:
                continue
            file = io.open('./url.txt', 'a+',encoding='utf-8')
            for key,value in food.items():
                file.writelines(value+'\n')
                print(key,' ',value)
            file.close()
            sleep(1)
            browser.close()
    print("finished")


    
