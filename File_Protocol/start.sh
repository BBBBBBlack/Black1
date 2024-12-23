#!/bin/bash  

# 定义jar包名称和端口以及path-root参数  
JAR_NAME="File_Protocol-1.0-SNAPSHOT.jar"  
PORTS=(8081 8082 8083)  
PATH_ROOTS=("test01/" "test02/" "test03/")
TCP_PORTS=(9001 9002 9003)

# 循环启动每个端口的进程，并在screen中打开新的会话  
for i in "${!PORTS[@]}"  
do  
    port=${PORTS[$i]}  
    path_root=${PATH_ROOTS[$i]}  
    tcp_port=${TCP_PORTS[$i]}
    
    echo "Starting $JAR_NAME on port $port with path-root $path_root in a new screen session"  
    
    # 创建一个新的screen会话，并在后台运行  
    screen -dmS "session_$port" bash -c "java -jar -Dserver.port=$port -Dpath-root=$path_root -Dtcp.port=$tcp_port $JAR_NAME; exec bash"  
done  

echo "All instances started in separate screen sessions."
