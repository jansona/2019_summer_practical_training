<template>
  <div>
    <div v-for="(article,index) in articles" :key="index">
      <el-row>
        <el-col :span="2">
          <el-avatar :src="article.user.profileUrl" />
        </el-col>
        <el-col :span="18">
          <p style="margin-bottom: 5px">
            <a class="a-style">
              <span @click="gotoArticle(article.id)">{{article.title}}</span>
            </a>
          </p>
          <p class="name">
            <a class="a-style">
              <span @click="gotoAuthorInfo">{{article.user.name}}</span>
            </a>
            &nbsp{{' '+dateFormat(article.date)}}
            <span
              style="float:right"
            >&nbsp {{' | 浏览 '+article.viewNum+' '}}</span>
            <span style="float:right">&nbsp{{' | 赞 '+article.likeNum+' '}}</span>
            <span style="float:right">&nbsp{{' 回复 '+article.replyNum+' '}}</span>
          </p>
        </el-col>
      </el-row>
      <el-divider style="margin-top: 15px;margin-right: 0px;margin-bottom: 15px;margin-left: 0px;"></el-divider>
    </div>
  </div>
</template>

<script>
import URLS from "@/config/config";
export default {
  name: "articles",
  props: {
    articles: Array
  },
  watch: {},
  data() {
    return {};
  },
  methods: {
    gotoArticle(id) {
      this.$router.push({name:"showArticle",query:{id:id}});
    },
    gotoAuthorInfo(e){
      
    },
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
    yieldPicUrl(partialUrl){
      return URLS.baseUrl + partialUrl;
    }
  }
};
</script>

<style scoped>
.round_icon {
  width: 40px;
  height: 40px;
  display: flex;
  border-radius: 50%;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}
p {
  text-align: left;
  margin-block-start: 0em;
  margin-block-end: 0em;
  margin-bottom: 5px;
  margin-left:10px
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
