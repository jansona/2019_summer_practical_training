<template>
  <div class="flex-container">
    <el-row class="fill-width">
      <el-col :span="10" :offset="2">
        <div class="left-part" ref="leftPart">
          <el-form
            label-position="right"
            label-width="100px"
            :model="loginForm"
            :rules="rules"
            ref="loginForm"
          >
            <el-form-item label="邮箱/手机" prop="username">
              <el-input v-model="loginForm.username" placeholder="请输入邮箱或者手机号"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="rememberLogin">记住登录</el-checkbox>
              <a class="a-style" @click="dialogFormVisible = true">
                <span>忘记密码？</span>
              </a>
            </el-form-item>
            <el-form-item>
              <el-button
                class="fill-width"
                type="success"
                @click="login"
                :loading="loginingLoading"
              >立即登录</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <el-col :span="2" :offset="1">
        <el-divider direction="vertical">或</el-divider>
      </el-col>
      <el-col :span="7">
        <div>
          <ul>
            <li>
              <el-button icon="iconfont icon-qq">&nbsp&nbsp&nbspQQ账号登录</el-button>
            </li>
            <li>
              <el-button icon="iconfont icon-weibo">&nbsp&nbsp微博账号登录</el-button>
            </li>
            <li>
              <el-button icon="iconfont icon-weixin">&nbsp&nbsp微信账号登录</el-button>
            </li>
          </ul>
        </div>
      </el-col>
    </el-row>


    <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
  <el-form :model="form">
    <el-form-item label="手机号" label-width="120px">
      <el-input v-model="form.tel" autocomplete="off" placeholder="请输入手机号"></el-input>
    </el-form-item>
    <el-form-item label="验证码" label-width="120px">
        <el-input
          v-model="form.code"
          type="verifacationCode"
          placeholder="请输入手机验证码"
        >
          <el-button slot="append" @click="sendSMS" :disabled="disabled">{{btnTitle}}</el-button>
        </el-input>
      </el-form-item>
    <el-form-item label="新密码" label-width="120px">
      <el-input v-model="form.pwd" autocomplete="off" placeholder="请输入密码" type="password"></el-input>
    </el-form-item>
    <el-form-item label="重复密码" label-width="120px"> 
      <el-input v-model="rePwd" autocomplete="off" placeholder="请输入重复密码" type="password"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
  </div>
</el-dialog>
  </div>
</template>

<script>
import { request } from "@/api/api";
import URLS from "@/config/config";
import axios from "axios";
export default {
  name: "Login",
  inject: ["reload","connect"],
  data() {
    var checkPhoneOrEmail = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
      if (this.wrongPass) {
        this.wrongPass = false;
        return callback(new Error("错误的账号或密码"));
      }
      if (!value) {
        return callback(new Error("请输入邮箱或电话号码"));
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          callback();
        } else if (phoneReg.test(value)) {
          callback();
        } else {
          callback(new Error("请输入正确的邮箱或电话号码"));
        }
      }, 100);
    };
    return {
       disabled: false, 
      btnTitle: "获取验证码",
      countdown: 60,
      rePwd:'',
      dialogFormVisible: false,
      form:{code:'',pwd:'',tel:''},
      wrongPass: false,
      loginForm: {
        username: "",
        password: ""
      },
      rules: {
        username: [
          { required: true, validator: checkPhoneOrEmail, trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      rememberLogin: false,
      loginingLoading: false
    };
  },
  methods: {
    getUserInfo() {
      axios
        .get(URLS.userInfoUrl)
        .then(data => {
          console.log("已经登陆成功 ", data);
          this.$store.commit("setUserID", {
            id: data.data.data.id,
            flag: this.rememberLogin
          });
          this.$store.commit("setUserInfo", data.data.data);
          this.$store.commit("setUserID",{id:data.data.data.id,flag:true})
          this.connect();
          console.log("userInfo", this.$store.state.userInfo);
        })
        .catch(error => {
          console.log(error);
        });
    },
    login() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loginingLoading = true;
          let params = JSON.parse(JSON.stringify(this.loginForm));
          params.grant_type = "password";
          if (
            this.$store.state.token &&
            this.$store.state.token != "undefined"
          ) {
            this.getUserInfo();
            this.loginingLoading = false;
            this.$router.push("home");
          } else {
            request(URLS.loginUrl, params)
              .then(data => {
                console.log(data);
                // if (data.data.rtnCode == 200) {
                this.$notify({
                  title: "登陆成功！",
                  type: "success",
                  duration: 1000,
                  offset: 50
                });
                this.$store.commit("setToken", {
                  token: data.access_token,
                  flag: this.rememberLogin
                });
                this.loginingLoading = false;
                this.getUserInfo();
                // this.$store.commit("setUserID", {
                //   id: data.data.data.id,
                //   flag: this.rememberLogin
                // });

                this.$router.push("home");
              })
              .catch(error => {
                this.wrongPass = true;
                this.loginingLoading = false;
                this.$refs.loginForm.validateField("username", error => {});
                // this.$message.error("服务器错误");
                console.log(error);
              });
          }
        }
      });
    },
    sendSMS() {
      if (this.btnTitle == "获取验证码") {
        if(this.form.tel == ''){
          this.$notify({
                  message: "手机号不能为空",
                  type: "warning",
                  duration: 1500,
                  offset: 50
                });
        }else{
          this.disabled = true;
          let countTime = setInterval(() => {
            --this.countdown;
            this.btnTitle = this.countdown + "S";
            if (this.countdown <= 0) {
              clearInterval(countTime);
              this.countdown = 60;
              this.disabled = false;
              this.btnTitle = "获取验证码";
              return;
            }
          }, 1000);

          let phoneNum = this.form.tel;
          fetch(URLS.sendSMSUrl2, {tel:phoneNum}).then(data => {
            console.log(data);
            if (data.rtnCode != 200) {
              this.$message({
                message: data.msg,
                type: "warning"
              });

              this.disabled = false;
              this.countdown = 60;
              this.btnTitle = "获取验证码";
            } else {
              this.$notify({
                  message: "发送成功",
                  type: "success",
                  duration: 1500,
                  offset: 50
                });
            }
          });
        }
        
         
          
        
      }
    },commit(){
      let url=URLS.resetUrl+'?tel='+this.form.tel+'&pwd='+this.form.pwd+'&code='+this.form.code
      let _this= this
      axios
        .post(url)
        .then(function(response) {
         _this.$notify({
            message: "修改成功",
            type: "success",
            duration: 1500,
            offset: 50
          });
        })
        .catch(function(error) {
          console.log(error);
          _this.$notify({
            message: "网络不稳定",
            type: "warning",
            duration: 1500,
            offset: 50
          });
        });
    }
  },
  mounted() {
    // console.log("mouted",this.$refs.leftPart.clientHeight)
  }
};
</script>

<style scoped>
li {
  list-style-type: none;
  margin-bottom: 20px;
  margin-left: -30px;
}
ul {
  padding-inline-start: 0px;
}
.flex-container /deep/ .el-divider {
  height: 226px;
  /* margin-left: 40px; */
}
.left-part {
  /* margin-left: 60px; */
}
.forget-pass {
  float: right;
}
</style>
