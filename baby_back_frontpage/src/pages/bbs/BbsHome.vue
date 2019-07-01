<template>
  <div class="hello">
    <el-card class="box-card">
      <el-row>
        <el-input placeholder="请输入内容搜索" v-model="input" class="input-with-select">
          <el-select v-model="select" slot="prepend" placeholder="请选择" style="width:100px">
            <el-option label="内容" value="内容"></el-option>
            <el-option label="用户" value="用户"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search"></el-button>
        </el-input>
      </el-row>
      <br />
      <el-row>
        <div class="nav-container">
          <el-tabs v-model="activeName" @tab-click="handleClick" class="fill-width">
            <el-tab-pane label="最近回复" name="first">
              <articles :articles="articles"></articles>
            </el-tab-pane>
            <el-tab-pane label="最近发帖" name="second">
              <articles :articles="articles"></articles>
            </el-tab-pane>
          </el-tabs>
          <el-button type="primary" size="small" class="add-btn" @click="gotoInsertArticle">我要发帖</el-button>
        </div>
      </el-row>

      <el-pagination
        @current-change="handleCurrentChange"
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="numOfElements"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
import articles from "@/pages/bbs/articles";
import axios from "axios";
import URLS from "@/config/config";
import { request, fetch } from "@/api/api";
export default {
  name: "BbsHome",
  components: {
    articles
  },
  mounted: function() {
    this.getArticles(0, true);
  },
  data() {
    return {
      numOfElements: 0,
      pageSize: 0,
      input: "",
      select: "",
      activeName: "first",
      articles: [
        {
          title: "震惊，深夜母猪哀嚎究竟为何",
          date: "2019-7-12",
          content: "哈哈哈哈哈",
          likeNum: 10,
          viewNum: 20,
          commentNum: 30,
          user: { id: "2", imagePath: "", name: "黄卜江" }
        },
        {
          title: "男人看了会沉默，女人看了会流泪",
          date: "2019-7-13",
          content: "哈哈哈哈哈",
          likeNum: 10,
          viewNum: 20,
          commentNum: 30,
          user: { id: "2", imagePath: "", name: "尹邦国" }
        }
      ]
    };
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    handleCurrentChange(val) {
      console.log(val);
    },
    gotoInsertArticle() {
      console.log(this.$store.state.hasLogin);
      if (this.$store.state.hasLogin) {
        this.$router.push("insertArticle");
      } else {
        this.$notify({
          message: "您尚未登陆，不能发帖",
          type: "warning",
          duration: 1500,
          offset: 50
        });
      }
    },
    getArticles(pageNum, sort) {
      let _this = this;
      if (sort) {
        axios
          .post(URLS.articleFindUrl, {
            page: pageNum
          })
          .then(function(response) {
            _this.articles = response.data.data.content;
            _this.pageSize = response.data.data.size;
            _this.numOfElements = response.data.data.numberOfElements;
          })
          .catch(function(error) {
            console.log(error);
            alert("查询数据错误，请联系技术人员");
          });
      } else {
      }
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
.el-select .el-input {
  width: 130px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
.nav-container {
  position: relative;
}
.add-btn {
  position: absolute;
  right: 10px;
  top: 0px;
}
</style>
