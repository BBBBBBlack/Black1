## 使用说明
#### 项目配置
* MySQL和Redis的配置在同级目录下的application.yml中。该配置文件中默认的MySQL和Redis为团队云服务器的配置，若有修改要求，则需导入同级目录下的filesystem.sql文件，并修改application.yml的相应配置。  
* 文件存储路径、自动备份配置与集群配置在同级目录下的GEMLoadConfig.ini文件中，该文件中已有详细说明，这里不再赘述（特别注意Windows与Linux操作系统配置的文件路径不同）。  
####项目部署
* 开启部署时，双击同级目录下start.bat
* 关闭时，双击同级目录下stop.bat。  
####端口说明
* 项目部署的端口
    即tomcat服务器对外暴露端口，application.yml中server.port值
* 备份协议端口
    在主机和备份机之间，集群模式下各节点之间进行文件传输的端口，不对客户端暴露接口，GEMLoadConfig.ini中[cluster]的port值