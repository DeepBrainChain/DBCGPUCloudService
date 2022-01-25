# DBCGPUCloudService
DBC  JAVA GPU Cloud Service

CPU服务器配置要求：8核CPU,16G内存，200G硬盘空间


云平台服务器JAVA代码部署文档

#1. 修改配置文件中的dbc算力网络客户端域名

  application-dev.properties  application-dev.properties   application-dev.properties 中clientUrl修改用自己部署的dbc客户端域名替代
  
#2. 修改wss链上访问DBC钱包客户端域名
  修改 chainUrl = wss://infotest.dbcwallet.io:7777 为 chainUrl = wss://info.dbcwallet.io
  也可以修改为自己部署的DBC钱包客户端地址，info.dbcwallet.io为公开的地址，不保证100%稳定
  
#3. 修改数据库地址信息
  spring.data.mongodb.uri=mongodb://usr:passwd@localhost:27017/database_name
  
#4. 编译打包程序
```shell
   打包开发版本， 执行命令： mvn package -P dev
   打包测试版本， 执行命令： mvn package -P test
   打包正式版本， 执行命令： mvn package -P prod
```
#5. 上传jar文件到服务器
   上传 DBCWebService\target\DBCWebService-0.1.5-SNAPSHOT.jar 文件到服务器 lib文件夹下面
   
#6. 服务器start.sh 脚本修改，脚本下载地址：
   ```shell
   appName="GalaxyValidationService-0.0.1-SNAPSHOT.jar" ,之前编译出来的jar包名字
   serverPort=8081，可以自定义启动端口，此处端口设置要和nginx配置中的端口号保持一致
   profiles="test"  ，可以设置为: dev\test\prod
   ```
   
#7. 服务器stop.sh 脚本修改，脚本下载地址：
   APP_NAME="GalaxyValidationService-0.0.1-SNAPSHOT.jar",之前编译出来的jar包名字
   SERVER_PORT=8081 和start.sh 脚本中的端口号保持一致
   
#8. 部署启动服务器程序
  ```shell
   1) mkdir /data
   2) cd /data &  mkdir /bin,将start.sh和 stop.sh拷贝到 bin文件夹中
   3) cd /data &  mkdir /lib,将DBCWebService-0.1.5-SNAPSHOT.jar 拷贝到 lib中
   4) bash start.sh  启动程序
   ```