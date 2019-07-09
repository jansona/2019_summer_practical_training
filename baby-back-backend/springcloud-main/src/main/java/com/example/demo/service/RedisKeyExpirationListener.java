//package com.example.demo.service;
//
//import com.example.demo.entity.PendingMessage;
//import com.example.demo.entity.User;
//import com.example.demo.reposity.LostBabyRepository;
//import com.example.demo.reposity.UserRepository;
//import com.example.demo.utils.LocationConvertor;
//import com.example.demo.utils.UserInformer;
//import io.lettuce.core.RedisClient;
//import javafx.util.Pair;
//import org.hibernate.internal.CoordinatingEntityNameResolver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
//
//    public final int TWO_HOURS = 2;
//    public final int SIX_HOURS = 4;
//    public final int HALF_DAY = 6;
//
//    @Autowired
//    LostBabyRepository lostBabyRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    UserInformer userInformer = new UserInformer();
//
//    private final static Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
//
//    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
//        String expiredKey = message.toString();
//        String info[] = expiredKey.split("-");
//        Integer id = Integer.valueOf(info[0]);
//        Integer duration = Integer.valueOf(info[1]);
//
//        // 得到失踪者坐标
//        Pair<Float, Float> coordinate = lostBabyRepository.findById(id).get().getCoordinate();
//
//        // 判断得出失踪者消息通知范围，以及设定一下次通知计时
//        Float distance = 0.0F;
//        int waitingTime = 0;
//        switch (duration){
//            case TWO_HOURS:
//                distance = LocationConvertor.DISTANCE_I;
//                waitingTime = SIX_HOURS;
//                String compositedKey = String.format("%d-%d", id, waitingTime);
//                stringRedisTemplate.opsForValue().set(compositedKey, "", waitingTime, TimeUnit.SECONDS);
//                break;
//            case SIX_HOURS:
//                distance = LocationConvertor.DISTANCE_II;
//                waitingTime = HALF_DAY;
//                compositedKey = String.format("%d-%d", id, waitingTime);
//                stringRedisTemplate.opsForValue().set(compositedKey, "", waitingTime, TimeUnit.SECONDS);
//                break;
//            case HALF_DAY:
//                distance = LocationConvertor.DISTANCE_III;
//        }
//
//        // 判断得出本次通知用户id
//        List<User> userList = userRepository.findAll();
//        ArrayList<Integer> ids = new ArrayList<>();
//        for(User user : userList){
//            Pair<Float, Float> coord = user.getCoordinate();
//            if(coord != null){
//                if(LocationConvertor.shouldResponse(coord, coordinate, distance)){
//                    ids.add(user.getId());
//                }
//            }
//        }
//
//        // 通知目标用户
//        userInformer.infoUser(ids, PendingMessage.MessageType.LOST_NOTIFICATION, id.toString());
//    }
//}
