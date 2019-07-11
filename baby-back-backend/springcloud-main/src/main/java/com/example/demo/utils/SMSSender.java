package com.example.demo.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;

public class SMSSender {

    static final int codeLength = 6;
    static Random random = new Random();

    public static String sendSMS(String tel) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIIDYDeNbGY15M", "677hr6fbFS4DvIgIsJAZZEJNpiOUSD");
            IAcsClient client = new DefaultAcsClient(profile);

        String code = verifyCode();

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", tel);
        request.putQueryParameter("SignName", "宝贝回家");
        request.putQueryParameter("TemplateCode", "SMS_169103269");
        request.putQueryParameter("TemplateParam", String.format("{\"code\":\"%s\"}", code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return code;
    }

    static String verifyCode() {
        String str = "";
        String[] ch = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < codeLength; i++) {
            String num = ch[random.nextInt(ch.length)];
            str += num;
        }
        return str;
    }
}
