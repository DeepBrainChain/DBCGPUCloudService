# mongo database deployment documentation



#1. 下载：  
```shell
  curl -O https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-3.0.6.tgz
  ```
#2. 解压:
```shell   
  tar -zxvf mongodb-linux-x86_64-3.0.6.tgz 
  ```
#3. 将解压包拷贝到指定目录: 
 ```shell
 mkdir /data 
 mkdir /data/mongodb 
 mongodb-linux-x86_64-3.0.6/ /data/mongodb  
```
#4. 设置环境变量：
  ```shell
  sudo echo "export PATH=/data/mongodb/bin:$PATH"  >> /etc/profile 
  source /etc/profile
  ```
#5. 创建数据库目录：	
```shell
  mkdir -p /data/db  mkdir -p /data/db/master
 ```
#6. 创建：/etc/mongod.conf 添加授权（这样数据库需要密码才能登陆）

	storage:
	  dbPath: /data/db/master
	  journal:
		enabled: true

	net:
	  port: 27017
	  bindIp: 0.0.0.0


	security:
	  authorization: enabled


#7. 创建admin和identifier数据库，并且设置密码 
  ```shell
   1)mongod --dbpath /data/db/master,然后  mongo
   2)use admin
   3)db.createUser({ user: "admin", pwd: "*****", roles: [{ role: "root", db: "admin" }] })
   4)db.shutdownServer()
   5)screen -S mongod 
   6)mongod --config /etc/mongod.conf （需要提前配置好mongod.conf文件） 此处如果要配置2个不同服务器的数据库自动备份，
      则用命令：mongod --master --slave --autoresync --config /etc/mongod.conf  --source ip:27017
   7)mongo,然后use admin ,然后 db.auth('admin','******')，
   8)use identifier
   9)db.createUser({user: "dbc", pwd: "*******", roles: [ { role: "dbOwner", db: "identifier" } ]})
   ```
