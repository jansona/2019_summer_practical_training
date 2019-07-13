package com.example.demo.controller;

import com.example.demo.entity.PendingMessage;
import com.example.demo.entity.User;
import com.example.demo.reposity.PendingMessageRepository;
import com.example.demo.reposity.UserRepository;
import com.example.demo.utils.Recognizer;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket")
@Component
public class CustomWebSocket {

    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的CumWebSocket对象。
     */
    private static CopyOnWriteArraySet<CustomWebSocket> webSocketSet = new CopyOnWriteArraySet<CustomWebSocket>();

    private Session session;
    private int id;

    @Autowired
    PendingMessageRepository pendingMessageRepository;
    @Autowired
    UserRepository userRepository;


    private static CustomWebSocket customWebSocket;
    //我也很绝望啊，LostBabyRepository自动注入为null，只有这样解决
    @PostConstruct
    public void init() {
        customWebSocket = this;
        customWebSocket.pendingMessageRepository = this.pendingMessageRepository;
        customWebSocket.userRepository = this.userRepository;
    }

    public static boolean sendMessageToUser(int id, String message){
        boolean doesExist = false;
        for(CustomWebSocket socket : webSocketSet){
            try {
                System.out.println(String.format("SocketUserID: %d, UserID: %d", socket.id, id));
                if (socket.id == id) {
                    socket.sendMessage(message);
                    doesExist = true;
                    break;
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        return doesExist;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        try {
            session.getBasicRemote().sendText("Who are you?");
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        Set<String> keys = session.getUserProperties().keySet();    // TODO 这一步有望得到用户ID
        for(String key : keys){
            System.out.println(key);
        }
        //加入set中
        webSocketSet.add(this);
        //添加在线人数
        addOnlineCount();
        System.out.println("新连接接入。当前在线人数为：" + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        //在线数减1
        subOnlineCount();
        System.out.println("有连接关闭。当前在线人数为：" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自前端的消息体" + message);
        if(!message.contains("message:")){
            try {
                // 前端发来id说明是刚建立连接，此时有两个工作：
                // 1.为CusWebSocket对象初始化id；2.发送对应该用户的PendingMessage。

                int idGot = Integer.valueOf(message);

                for(CustomWebSocket webSocket : webSocketSet){
                    if(webSocket.id == idGot){
                        webSocketSet.remove(webSocket);
                    }
                }

                this.id = Integer.valueOf(message);

                System.out.println("用户id:" + this.id);
                User user = customWebSocket.userRepository.findById(this.id).get();
                List<PendingMessage> pendingMessageList = customWebSocket.pendingMessageRepository.findByUser(user);
                // TODO 暂时只返回lostbaby的ID
                for(PendingMessage pendingMessage : pendingMessageList){
                    session.getBasicRemote().sendText(pendingMessage.getContent());
                    customWebSocket.pendingMessageRepository.delete(pendingMessage);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("其他消息：" + message);
        }
    }

    public static void sendInfo(String message) throws IOException {
        sendAll(message);
    }

    private static void sendAll(String message) {
        Arrays.asList(webSocketSet.toArray()).forEach(item -> {
            CustomWebSocket customWebSocket = (CustomWebSocket) item;
            //群发
            try {
                customWebSocket.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @OnError
    public void onError(Session session, Throwable error) {
        try {
            session.close();
            System.out.println("----websocket-------有异常啦");
            error.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void subOnlineCount() {
        CustomWebSocket.onlineCount--;
    }

    private void addOnlineCount() {
        CustomWebSocket.onlineCount++;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public void sendMessage(String message) throws IOException {
        //获取session远程基本连接发送文本消息
        this.session.getBasicRemote().sendText(message);
    }
}

