# encoding: utf-8
import io
import os
import urllib
from time import sleep
import requests
from bs4 import BeautifulSoup
from selenium import webdriver

#周梓浩
if __name__ == '__main__':  # 主函数入口
    for each in range(1,131):
        for page_num in range(1, 10, 1):
            food={}
            browser = webdriver.Chrome()
            li_url = "http://www.boohee.com/food/view_group/" + str(each) + "?page=" + str(page_num)
            browser.get(li_url)
            soup = BeautifulSoup(browser.page_source,'html.parser')
            catefind = soup.find(class_ = "widget-food-list pull-right").find("a").text
            firstfind = soup.find(class_="food-list")
            for name in firstfind.findAll('img'):
                for heat in firstfind.findAll('p'):
                    try:
                        food[name.attrs["alt"]] = heat.string
                    except Exception:
                        continue
                    break
            file = io.open('./heat.txt', 'a+',encoding='utf-8')
            file2 = io.open('./name.txt', 'a+',encoding='utf-8')
            for key,value in food.items():
                print(name,' ',catefind,' ',heat)
                file.writelines(key+' '+catefind+' '+value+'\n')
                file2.writelines(key+'\n')
            file.close()
            sleep(1)
            browser.close()
    print("finished")

    
