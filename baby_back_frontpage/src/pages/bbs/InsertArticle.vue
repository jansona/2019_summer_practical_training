<template>
  <div class="hello">
    <el-card class="box-card">
      <el-input v-model="title" placeholder="请输入标题"></el-input>
      <br />
      <br />
      <el-input type="textarea" :rows="20" placeholder="请输入内容" v-model="content"></el-input>
      <br />
      <br />
      <el-row>
        <el-col :span="2">
          <el-button type="danger" round @click="returnBbs">取消</el-button>
        </el-col>
        <el-col :span="3" :offset="19">
          <el-button type="primary" round @click="insert">确认发布</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import store from "@/store/store";
import axios from "axios";
import URLS from "@/config/config";
export default {
  name: "InsertArticle",
  mounted: function() {
    this.getUser(this.$store.state.userID, this.$store.state.hasLogin);
  },
  data() {
    return {
      user: null,
      title: "",
      content: ""
    };
  },
  methods: {
    getUser(userID, hasLogin) {
      let _this = this;
      if (hasLogin) {
        axios
          .post(URLS.userFindUrl, {
            id: userID
          })
          .then(function(response) {
            console.log(response);
            if (response.data.data.numberOfElements == 1) {
              _this.user = response.data.data.content[0];
            } else {
              _this.user = null;
              _this.$router.push("bbsHome");
            }
          })
          .catch(function(error) {
            console.log(error);
            _this.user = null;
            // alert("您尚未登陆，不能发帖");
            _this.$router.push("bbsHome");
          });
      } else {
        // alert("您尚未登陆，不能发帖");
        this.$router.push("bbsHome");
      }
    },
    insert() {
      if (this.title == "") {
        alert("文章标题不能为空");
        return;
      }
      if (this.content == "") {
        alert("文章内容不能为空");
        return;
      }
      var date = new Date();
      var article = {};
      article["date"] = date;
      article["user"] = this.user;
      article["title"] = this.title;
      article["content"] = this.content;
      article["likeNum"] = 0;
      article["viewNum"] = 0;
      console.log(article);
      axios
        .put(URLS.articleInsertUrl, {
          article: article
        })
        .then(function(response) {
          if (response.data.code == 200) {
            alert("发布成功");
          } else {
            alert("发布失败");
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    returnBbs(){
      this.$router.push("bbsHome");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>