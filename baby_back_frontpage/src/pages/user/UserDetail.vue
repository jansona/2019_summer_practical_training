<template>
<div>
<el-card>
  <el-tabs :tab-position="tabPosition" @tab-click="handleTabClick" v-model="activeName">
    <el-tab-pane label="基本信息" name="user_info">
      <UserInfo
      :picUrl='this.picUrl'
      :tableData='this.tableData'
      :user='this.user' ref="userInfo">
      </UserInfo>
    </el-tab-pane>
    <el-tab-pane label="已申报失踪者" name="baby_aply">
      <el-tabs @tab-click="subTabClick" style="margin-top:10px;" v-if='mark===1'>
      <el-tab-pane class="tab-container">
        <div slot="label">
          &nbsp&nbsp
          <span class="font-size-1-3em">家寻宝贝申报</span>
        </div>
        <template v-if="choosed == 0">
          <Pictures :datas="datas" :types="dataTypes" ref="pictures0"></Pictures>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, jumper"
            :total="totalNum"
          ></el-pagination>
        </template>
      </el-tab-pane>
      <el-tab-pane>
        <div slot="label">
          &nbsp&nbsp
          <span class="font-size-1-3em">宝贝寻家申报</span>
        </div>
        <template v-if="choosed == 1">
          <Pictures :datas="datas" :types="dataTypes" ref="pictures1"></Pictures>
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, jumper"
            :total="totalNum"
          ></el-pagination>
        </template>
      </el-tab-pane>
    </el-tabs>
    </el-tab-pane>
    <el-tab-pane label="已发布文章" name="article">
      <ArticleInfiniteList
      :user="this.id" v-if="mark===2"
      >
      </ArticleInfiniteList>
    </el-tab-pane>
    <el-tab-pane label="已发布评论" name="comment">
      <CommentInfiniteList
      :user="this.id" v-if="mark===3"
      ></CommentInfiniteList>
    </el-tab-pane>
  </el-tabs>
</el-card>
</div>
</template>

<script>
import URLS from '@/config/config';
import { request,fetch } from "@/api/api";
import Pictures from "../faceWall/components/Pictures";
import UserInfo from "./components/UserInfo";
import articles from "../bbs/articles";
import ArticleInfiniteList from "./components/ArticleInfiniteList";
import CommentInfiniteList from "./components/CommentInfiniteList";
const nameDict = {
  username: "姓名",
  email: "邮箱",
  tel: "联系方式",
  location: "地址"
};
export default {
  name: 'UserDetail',
  components: {
    UserInfo,
    Pictures,
    articles,
    ArticleInfiniteList,
    CommentInfiniteList
  },
  data () {
    return {
      mark: 0,
      activeName: 'user_info',

      tabPosition: 'left',
      id: parseInt(this.$route.query.id),
      // id: 8,
      // 申报信息分页相关
      tableData: [],
      picUrl: "",
      type: "",
      choosed: 0,
      datas: [],
      dataTypes: [],
      totalNum: 0,
      currentPage: 1,
      pageSize: 3,
      user:{},

      // 文章分页相关
      // articles_of_user: [],
      // articlePageSize: 5,
      // numOfArticles: 0,
      // pageNo: 0
      articles: [],
      articleIndex: 0
    }
  },
  methods: {
    loadUserInfo() {
      this.tableData = [];
      let url = URLS.userFindByIdUrl;
      request(url, { id: this.id })
        .then(data => {
          if (data.rtnCode == 200) {
            this.picUrl =
              data.data.profileUrl
            console.log(data)
            let content = data.data;
            this.user = content;
            for (var key in nameDict) {
              if (content.hasOwnProperty(key)) {
                this.tableData.push({
                  name: nameDict[key],
                  content: content[key]
                });
              }else{
                this.tableData.push({
                  name: nameDict[key],
                  content: ""
                })
              }
            }
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    loadBabyData() {
      request(
        this.choosed != 1 ? URLS.lostBabyFindByUserUrl : URLS.matchBabyFindByUserUrl,
        { size: this.pageSize, page: this.currentPage - 1, user: this.id }
      )
        .then(data => {
          console.log(data);
          if (data.rtnCode == 200) {
            if (this.choosed == -1) this.choosed = 0;
            this.totalNum = data.data.totalElements;
            this.datas = JSON.parse(JSON.stringify(data.data.content));
            this.dataTypes = [];
            for (var i = 0, len = this.datas.length; i < len; i++) {
              this.dataTypes.push(this.choosed);
            }

            let saved = this.choosed;
            this.choosed = -1;
            this.$nextTick(function() {
              this.choosed = saved;
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    handleCurrentChange(e) {
      console.log(e);
      this.currentPage = e;
      this.loadBabyData();
    },
    handleTabClick(e){
      switch(e.name){
        case 'user_info':
          this.mark = 0;
          this.loadUserInfo();
          break;
        case 'baby_aply':
          this.mark = 1;
          this.loadBabyData();
          break;
        case 'article':
          this.mark = 2;
          break;
        case 'comment':
          this.mark = 3;
          break;
        default:
          break;
      }
    },
    refreshLostBabyData(){
      this.loadBabyData(URLS.lostBabyFindByUserUrl);
    },
    refreshMatchBabyData(){
      this.loadBabyData(URLS.matchBabyFindByUserUrl);
    },
    subTabClick(e) {
      this.currentPage = 1;
      this.choosed = e.index;
      this.loadBabyData();
    },
    reachBottom(){
      articles.push()
    }
  },

  mounted() {
    this.loadUserInfo();
  }
}
</script>

<style scoped>

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }

</style>
