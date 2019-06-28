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
            <el-form-item label="邮箱/手机" prop="account">
              <el-input v-model="loginForm.account" placeholder="请输入邮箱或者手机号"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item>
              <el-checkbox v-model="rememberLogin">记住登录</el-checkbox>
              <a class="a-style">
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
  </div>
</template>

<script>
import { request } from "@/api/api";
import URLS from "@/config/config";
import axios from 'axios'
export default {
  name: "Login",
  inject: ["reload"],
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
      wrongPass: false,
      loginForm: {
        account: "",
        password: ""
      },
      rules: {
        account: [
          { required: true, validator: checkPhoneOrEmail, trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      rememberLogin: false,
      loginingLoading: false
    };
  },
  methods: {
    login() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loginingLoading = true;
          axios.post(URLS.loginUrl, this.loginForm)
            .then(data => {
              console.log(data);
              if (data.data.rtnCode == 200) {
                this.$message({
                  message: "登陆成功！",
                  type: "succcess"
                });
                this.loginingLoading = false;
                this.$store.commit("setUserID", {
                  id: 10,
                  flag: this.rememberLogin
                });
                this.$router.push("home");
                this.reload();
              } else {
                this.wrongPass = true;
              }
            })
            .catch(error => {
              this.loginingLoading = false;
              this.$message.error("服务器错误");
              console.log(error);
            });
        }
      });
    }
  },
  mounted() {
    console.log("mouted",this.$refs.leftPart.clientHeight)
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
