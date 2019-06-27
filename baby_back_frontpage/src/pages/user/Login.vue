<template>
  <div class="flex-container">
    <div class="left-part">
      <el-form label-position="right" label-width="100px" :model="loginForm" :rules="rules" ref="loginForm">
        <el-form-item label="邮箱/手机" prop="account">
          <el-input v-model="loginForm.account"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="rememberLogin">记住登录</el-checkbox>
          <a class="a-style">
            <span>忘记密码？</span>
          </a>
        </el-form-item>
        <el-form-item>
          <el-button class="fill-width" type="success" @click="login">立即登录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-divider direction="vertical">或</el-divider>
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
  </div>
</template>

<script>
import {request} from '@/api/api'
import URLS from '@/config/config'
export default {
  name: "Login",
  inject: ['reload'],
  data() {
    var checkPhoneOrEmail = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/;
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
      if (!value) {
        return callback(new Error("请输入邮箱或电话号码"));
      }
      setTimeout(() => {
          if(mailReg.test(value)) {
            callback();
          }else if (phoneReg.test(value)) {
            callback();
          } else {
            callback(new Error("请输入正确的邮箱或电话号码"));
          }
      }, 100);
    };

    return {
      loginForm: {
        account: "",
        password: ""
      },
      rules: {
        account: [
          { required: true, validator: checkPhoneOrEmail, trigger: "blur" }
        ],
        password: [
          {required:true, message: "请输入密码", trigger: "blur"}
        ]
      },
      rememberLogin: false
    };
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        if(valid){
          request(URLS.loginUrl,this.loginForm).then(data => {
            console.log(data);
          }).catch(error => {
            console.log(error);
          })
          this.$store.commit('setUserID', {id:10,flag:this.rememberLogin});
          this.$router.push('home');
          this.reload();
        }
      })
    }
  }
};
</script>

<style scoped>
li {
  list-style-type: none;
  margin-bottom: 20px;
}
.flex-container /deep/ .el-divider {
  height: 10em;
  margin-left: 40px;
}
.left-part {
  margin-left: 60px;
}
.forget-pass {
  float: right;
}
</style>
