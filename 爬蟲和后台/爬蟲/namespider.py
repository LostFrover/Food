# encoding: utf-8
import io
import os
import urllib
from time import sleep
import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
#周梓浩
if __name__ == '__main__':  # 主函数入口
    for each in range(1,131):
        for page_num in range(1, 10, 1):
            food={}
            chrome_options=Options()
            chrome_options.add_argument('--headless')
            browser = webdriver.Chrome(chrome_options=chrome_options)
            li_url = "http://www.boohee.com/food/view_group/" + str(each) + "?page=" + str(page_num)
            browser.get(li_url)
            soup = BeautifulSoup(browser.page_source,'html.parser')
            try:
                firstfind = soup.find(class_="food-list")
                for item in firstfind.findAll(class_ ="item clearfix"):
                    try:
                        food[item.find('img').attrs["alt"]] = item.find('p').string
                    except Exception:
                        continue
            except Exception:
                continue
            file = io.open('./heat.txt', 'a+',encoding='utf-8')
            file2 = io.open('./name.txt', 'a+',encoding='utf-8')
            for key,value in food.items():
                file.writelines(value+'\n')
                file2.writelines(key+'\n')
                print(key,' ',value)
            file.close()
            file2.close()
            sleep(1)
            browser.close()
    print("finished")

    
