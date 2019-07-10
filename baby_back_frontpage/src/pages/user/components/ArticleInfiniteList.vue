<template>
  <div class="infinite-list-wrapper">
    <ul class="list" v-infinite-scroll="load" infinite-scroll-disabled="disabled">
      <li v-for="(a,i) in articles" class="list-item" :key="i">
        <el-col :span="24">
          <p class="date" style="margin-bottom: 5px;text-align:left;font-size:15px;color:black" >
            <a class="a-style">
              <span @click="gotoArticle(a.id)">{{a.title}}</span>
            </a>
          </p>
          <p class="date" style="float:left">发布于{{dateFormat(a.date)}}</p>
        </el-col>
        <!-- <el-divider
          style="margin-top: 15px;margin-right: 0px;margin-bottom: 15px;margin-left: 0px;"
        ></el-divider>-->
        <hr />
      </li>
    </ul>
    <p v-if="loading">加载中...</p>
    <p v-if="noMore">没有更多了</p>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { request, fetch } from "@/api/api";

export default {
  name: "ArticleInfiniteList",
  props: {
    // articles: Array
    user: Number
  },
  data() {
    return {
      loading: false,
      noMore: false,
      articles: [],
      index: 0
    };
  },
  computed: {
    disabled() {
      return this.loading || this.noMore;
    }
  },
  methods: {
    load() {
      this.loading = true;
      request(URLS.articleInfiniteUrl, { user: this.user, index: this.index })
        .then(data => {
          // console.log(data);
          if (data.rtnCode === 200) {
            if (data.data !== null) {
              this.articles.push(data.data);
              this.index += 1;
            } else {
              this.noMore = true;
            }
          }
          this.loading = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    gotoArticle(id) {
      this.$router.push({ name: "showArticle", query: { id: id } });
    },
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
    }
  }
};
</script>

<style scoped>
ul li {
  list-style-type: none;
}

.date {
  font-size: 10px;
  color: gray;
}
</style>
