#!/bin/bash  

# 定义jar包名称和端口  
JAR_NAME="File_Protocol-1.0-SNAPSHOT.jar"  
PORTS=(8081 8082 8083)  

# 循环检查每个端口的进程，并关闭对应的screen会话  
for port in "${PORTS[@]}"  
do  
    SESSION_NAME="session_$port"  
    
    # 获取运行在指定端口上的进程ID  
    PID=$(lsof -t -i:$port)  
    
    if [ -n "$PID" ]; then  
        echo "Killing process $PID running on port $port"  
        kill -9 $PID  
    else  
        echo "No process found running on port $port"  
    fi  

    # 关闭并删除screen会话  
    if screen -list | grep -q "$SESSION_NAME"; then  
        echo "Terminating screen session $SESSION_NAME"  
        screen -S "$SESSION_NAME" -X quit  
    else  
        echo "No screen session found named $SESSION_NAME"  
    fi  
done  

echo "All specified processes terminated and screen sessions closed."
