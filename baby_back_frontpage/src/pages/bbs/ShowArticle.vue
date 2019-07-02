<template>
  <div id="view">
    <el-col :span="15">
      <el-card class="box-card">
        <el-row>
          <el-col :span="22">
            <h1 style="margin-bottom: 5px;margin-left:15px;">{{article.title}}</h1>
          </el-col>
        </el-row>
        <br />
        <el-row>
          <el-col :span="2">
            <el-avatar :src="require('@/assets/house.jpg')" />
          </el-col>
          <el-col :span="18">
            <p class="name" style="margin-bottom: 10px;font-size:12px">
              <a class="a-style">
                <span @click="gotoAuthorInfo">&nbsp{{article.user.name}}</span>
              </a>
            </p>
            <p class="name">
              发布于{{dateFormat(article.date)}}
              <span
                style="float:right"
              >&nbsp {{' | 浏览 '+article.viewNum+' '}}</span>
              <span style="float:right">&nbsp{{' | 赞 '+article.likeNum+' '}}</span>
              <span style="float:right">&nbsp{{' 回复 '+article.replyNum+' '}}</span>
            </p>
          </el-col>
        </el-row>
        <el-divider
          style="margin-top: 15px;margin-right: 0px;margin-bottom: 15px;margin-left: 0px;"
        ></el-divider>
        <el-row>
          <p>{{article.content}}</p>
        </el-row>
        <br />
        <br />
        <br />
        <el-button type="primary" :icon="islike" circle @click="like"></el-button>
        <hr style="height:1px;border:none;border-top:1px dashed #0066CC;" />

        <br />

        <el-row>
          <el-col :span="4" style="text-align: left;">
            <span>
              <strong>{{comments.length}}条回帖</strong>
            </span>
          </el-col>
          <el-col :span="4" :offset="16">
            <el-button type="primary" size="small" style="float:right" @click="slideToBottom">
              回帖
              <i class="el-icon-edit el-icon--right"></i>
            </el-button>
          </el-col>
        </el-row>
        <br />
        <br />
        <div v-for="(comment,index) in comments" :key="index">
          <el-row>
            <el-col :span="2">
              <el-avatar :src="require('@/assets/house.jpg')" />
            </el-col>
            <el-col :span="18">
              <p style="margin-bottom: 5px" class="name">
                <a class="a-style">
                  <span @click="gotoAuthorInfo">{{comment.user.name}}</span>
                </a>
              </p>
              <p>
                <span>{{comment.content}}</span>
              </p>
              <p class="name">发布于{{dateFormat(article.date)}}</p>
            </el-col>
          </el-row>
          <el-divider
            style="margin-top: 15px;margin-right: 0px;margin-bottom: 15px;margin-left: 0px;"
          ></el-divider>
        </div>

        <br />
        <el-input id="comment" type="textarea" :rows="5" placeholder="请输入内容" v-model="myComment"></el-input>
        <br />
        <br />
        <el-col :span="4" :offset="20">
          <el-button type="primary" size="small" @click="slideToBottom" style="float:right">
            回帖
            <!-- <i class="el-icon-edit el-icon--right"></i> -->
          </el-button>
        </el-col>
      </el-card>
    </el-col>
    <el-col :span="8" :offset="1">
      <el-card>
        <el-row>
          <p style="font-size:15px">
            <strong>近期热帖</strong>
          </p>
        </el-row>
        <br />
        <div v-for="(like,index) in likeArticle" :key="index">
          <a class="a-style">
            <p style="font-size:15px" @click="gotoArticle(like.id)">{{like.title}}</p>
          </a>
          <p class="name">发布于{{dateFormat(like.date)}}</p>
          <el-divider
            style="margin-top: 15px;margin-right: 0px;margin-bottom: 15px;margin-left: 0px;"
          ></el-divider>
        </div>
      </el-card>
    </el-col>
  </div>
</template>
<script>
import store from "@/store/store";
import axios from "axios";
import URLS from "@/config/config";
import dateFormat from "@/api/api";
export default {
  name: "ShowArticle",
  mounted: function() {
    this.getArticles(this.$route.params.id);
    this.getComment(this.$route.params.id);
    this.getLikeArticle();
    //this.getArticles(1);
  },
  watch: {
    $route(to, from) {
      this.getArticles(this.$route.params.id);
      this.getComment(this.$route.params.id);
    }
  },
  data() {
    return {
      article: {},
      likeArticle: [],
      islike: "el-icon-star-off",
      myComment: "",
      comments: [
        {
          id: 1,
          user: {
            id: 1,
            passWord: "19981228lrf",
            name: "吕若凡",
            tel: "18031098057",
            email: "935802216@qq.com"
          },
          article: {
            id: 1,
            title: "我和空姐同居的日子",
            user: {
              id: 1,
              passWord: "19981228lrf",
              name: "吕若凡",
              tel: "18031098057",
              email: "935802216@qq.com"
            },
            content:
              "我和空姐同居的日子，竟是如此美妙。\\n美好的日子呀，请不要溜走。",
            date: "2019-06-29T00:00:00.000+0000",
            likeNum: 2,
            viewNum: 0,
            replyNum: 1
          },
          content: "好哥哥，你真猛",
          date: "2019-07-02T09:04:41.000+0000"
        },
        {
          id: 1,
          user: {
            id: 1,
            passWord: "19981228lrf",
            name: "吕若凡",
            tel: "18031098057",
            email: "935802216@qq.com"
          },
          article: {
            id: 1,
            title: "我和空姐同居的日子",
            user: {
              id: 1,
              passWord: "19981228lrf",
              name: "吕若凡",
              tel: "18031098057",
              email: "935802216@qq.com"
            },
            content:
              "我和空姐同居的日子，竟是如此美妙。\n美好的日子呀，请不要溜走。",
            date: "2019-06-29T00:00:00.000+0000",
            likeNum: 2,
            viewNum: 0,
            replyNum: 1
          },
          content: "duang的一下就顺滑了呀，洗发液我只认霸王。\n--成龙",
          date: "2019-07-02T09:04:41.000+0000"
        }
      ]
    };
  },
  methods: {
    getArticles(articleId) {
      let _this = this;
      let url = "";

      url = URLS.articleFindUrl + "?id=" + articleId;

      axios
        .post(url)
        .then(function(response) {
          _this.article = response.data.data.content[0];
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
    },
    gotoAuthorInfo() {},
    //时间格式化函数，此处仅针对yyyy-MM-dd hh:mm:ss 的格式进行格式化
    dateFormat: function(time) {
      var date = new Date(time);
      var year = date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      var month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      var hours =
        date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      var minutes =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      var seconds =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      // 拼接
      return (
        year +
        "-" +
        month +
        "-" +
        day +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
      );
    },
    like() {
      if (this.islike !== "el-icon-star-on") {
        this.islike = "el-icon-star-on";

        let url = URLS.articleLike + "?article_id=" + this.$route.params.id;
        axios.post(url);
      }
    },
    getComment(id) {
      let url = URLS.commentFindByArticle + "?article=" + id;
      let _this = this;
      axios
        .post(url)
        .then(function(response) {
          _this.comments = response.data.data.content;
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
    },
    slideToBottom() {
      document.getElementById("comment").scrollIntoView();
      //document.documentElement.scrollTop=document.documentElement.scrollHeight
    },
    insertComment() {
      let url = URLS.commentInsertUrl;
      let comment = {};
      let date = new Date();
      comment["user"] = { id: this.$store.state.userID };
      comment["article"] = this.article;
      comment["content"] = this.myComment;
      comment["date"] = date;
      axios
        .put(url, comment)
        .then(function(response) {
          console.log("发布评论成功");
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
    },
    getLikeArticle() {
      let _this = this;
      let url = "";

      url = URLS.articleFindUrl + "?sort=likeNum&size=3";

      axios
        .post(url)
        .then(function(response) {
          _this.likeArticle = response.data.data.content;
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
    },
    gotoArticle(id) {
      this.$router.push({ path: `/showArticle/${id}` });
    }
  }
};
</script>
<style scoped>
.home-conten {
  position: relative;
  width: 60%;
  left: 20%;
}
p {
  text-align: left;
  margin-block-start: 0em;
  margin-block-end: 0em;
  margin-bottom: 5px;
  margin-left: 10px;
  color: black;
}
h1 {
  margin-bottom: 5px;
  margin-left: 10px;
  text-align: left;
  margin-block-start: 0em;
  margin-block-end: 0em;
}
span {
  text-align: left;
}
.el-divider--horizontal {
  margin-top: 15px;
  margin-right: 0px;
  margin-bottom: 15px;
  margin-left: 0px;
}
.name {
  font-size: 10px;
  color: gray;
}
</style>