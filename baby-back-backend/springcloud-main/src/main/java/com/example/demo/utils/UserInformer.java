package com.example.demo.utils;

import com.example.demo.controller.CustomWebSocket;
import com.example.demo.entity.PendingMessage;
import com.example.demo.entity.User;
import com.example.demo.reposity.PendingMessageRepository;
import com.example.demo.reposity.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
public class UserInformer {

    @Autowired
    PendingMessageRepository pendingMessageRepository;
    @Autowired
    UserRepository userRepository;

    private static UserInformer userInformer;
    //我也很绝望啊，LostBabyRepository自动注入为null，只有这样解决
    @PostConstruct
    public void init() {
        userInformer = this;
        userInformer.pendingMessageRepository = this.pendingMessageRepository;
        userInformer.userRepository = this.userRepository;
    }

    public void infoUser(ArrayList<Integer> ids, PendingMessage.MessageType type, String content){

        String prefix = "";
        switch (type){
            case LOST_NOTIFICATION:
                prefix = "LOST:";
                break;
            case MATCH_NOTIFICATION:
                prefix = "MATCH:";
                break;
        }

        for(int id : ids){
            if(!CustomWebSocket.sendMessageToUser(id, prefix + content)){
                System.out.println("目标用户不在");
                // 此时目标用户不在线
                userInformer.userRepository.findById(id);
                User user = userInformer.userRepository.findById(id).get();
                PendingMessage pendingMessage = new PendingMessage();
                pendingMessage.setType(type);
                pendingMessage.setContent(content);
                pendingMessage.setUser(user);

                userInformer.pendingMessageRepository.save(pendingMessage);
            }else{
                System.out.println("目标用户在线");
            }
        }
    }

}
