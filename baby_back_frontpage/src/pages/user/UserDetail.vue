<template>
<div>
<el-card>
  <el-tabs :tab-position="tabPosition" @tab-click="handleTabClick">
    <el-tab-pane label="基本信息">
      <UserInfo
      :picUrl='this.picUrl'
      :tableData='this.tableData'>
      </UserInfo>
    </el-tab-pane>
    <el-tab-pane label="我的失踪者申报">
      <el-tabs @tab-click="subTabClick" style="margin-top:10px;">
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
    <el-tab-pane label="我的文章">
      <articles :articles="articles_of_user"></articles>
      <el-pagination
        @current-change="handleCurrentChange"
        background
        layout="prev, pager, next"
        :page-size="articlePageSize"
        :total="numOfArticles"
        :current-page.sync="pageNo"
      ></el-pagination>
    </el-tab-pane>
    <el-tab-pane label="我的评论">我的评论</el-tab-pane>
  </el-tabs>
</el-card>
</div>
</template>

<script>
import URLS from '@/config/config'
import { request,fetch } from "@/api/api";
import Pictures from "../faceWall/components/Pictures";
import UserInfo from "./components/UserInfo";
import articles from "../bbs/articles";
const nameDict = {
  name: "姓名",
  tel: "邮箱",
  email: "联系地址"
};
export default {
  name: 'UserDetail',
  components: {
    UserInfo,
    Pictures,
    articles
  },
  data () {
    return {
      tabPosition: 'left',
      // id: this.$route.query.id,
      id: 8,
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


      // 文章分页相关
      articles_of_user: [],
      articlePageSize: 5,
      numOfArticles: 0,
      pageNo: 0
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
              URLS.baseUrl + "/resource/photo/profile/" + this.id + ".jpg";

            let content = data.data;

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
        this.choosed != 1 ? URLS.lostBabyFindUrl : URLS.matchBabyFindUrl,
        { size: this.pageSize, page: this.currentPage - 1 }
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
      switch(e.label){
        case '基本信息':
          this.loadUserInfo();
          break;
        case '我的失踪者申报':
          this.loadBabyData();
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
      this.choosed = e.index;
      this.loadBabyData();
    },
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
