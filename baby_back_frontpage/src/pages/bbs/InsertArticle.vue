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
      content: "",
      article:{}
    };
  },
  methods: {
    getUser(userID, hasLogin) {
      let _this = this;
      let url=URLS.userFindUrl+'?id='+userID
      if (hasLogin) {
        axios
          .post(url)
          .then(function(response) {
            console.log(response);
            if (response.data.data.numberOfElements >= 1) {
              _this.user = response.data.data.content[0];
            } else {
              _this.user = null;
              _this.$router.push("bbsHome");
            }
          })
          .catch(function(error) {
            console.log(error);
            _this.user = null;
            _this.$router.push("bbsHome");
          });
      } else {
        this.$router.push("bbsHome");
      }
    },
    insert() {
      let _this = this;
      if (this.title == "") {

        _this.$notify({
                  title: "文章标题不能为空",
                  type: "warning",
                  duration: 1500,
                  offset: 50
                });
        return;
      }
      if (this.content == "") {

        _this.$notify({
                  title: "文章内容不能为空",
                  type: "warning",
                  duration: 1500,
                  offset: 50
                });
        return;
      }
      var date = new Date();
      
      _this.article["date"] = date;
      _this.article["user"] = _this.user;
      _this.article["title"] = _this.title;
      _this.article["content"] = _this.content;
      _this.article["likeNum"] = 0;
      _this.article["viewNum"] = 0;
      console.log(_this.article);
      axios
        .put(URLS.articleInsertUrl, _this.article
        )
        .then(function(response) {
          console.log(response)
          if (response.data.rtnCode == 200) {
            _this.$notify({
                  title: "发布成功",
                  type: "success",
                  duration: 1500,
                  offset: 50
                });
            _this.$router.push('bbsHome')
          } else {
            _this.$notify({
                  title: "发布失败",
                  type: "warning",
                  duration: 1500,
                  offset: 50
                });
          }
        })
        .catch(function(error) {
          console.log(error);
          _this.$notify({
                  title: "发布失败",
                  type: "warning",
                  duration: 1500,
                  offset: 50
                });
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