# encoding:utf-8
import pymysql
import io

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
    file= io.open("./name.txt", 'r', encoding='utf-8')
    file1= io.open("./heat.txt", 'r', encoding='utf-8')
    file.readline()
    file1.readline()
    while True:
        name = file.readline()
        heat = file1.readline()
        if name == "":
            break
        print(name, " ", heat, '\n')
        sql = "insert into food(id,name,heat) values (null,'%s','%s');" % (name, heat)
        cursor.execute(sql)
        db.commit()
db.close();
print("finshed")

