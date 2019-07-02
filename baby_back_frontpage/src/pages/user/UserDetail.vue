<template>
<el-card>
  <el-tabs :tab-position="tabPosition" @tab-click="handleTabClick">
    <el-tab-pane label="基本信息">
      <UserInfo
      :picUrl='this.picUrl'
      :tableData='this.tableData'>
      </UserInfo>
    </el-tab-pane>
    <el-tab-pane label="我的失踪者申报">
      <Pictures :datas="this.datas" :types="this.dataTypes" ref="pictures0"></Pictures>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="totalNum">
      </el-pagination>
    </el-tab-pane>
    <el-tab-pane label="我的文章">我的文章</el-tab-pane>
    <el-tab-pane label="我的评论">我的评论</el-tab-pane>
  </el-tabs>
</el-card>
</template>

<script>
import URLS from '@/config/config'
import { request,fetch } from "@/api/api";
import Pictures from "../faceWall/components/Pictures";
import UserInfo from "./components/UserInfo";
const nameDict = {
  name: "姓名",
  tel: "邮箱",
  email: "联系地址"
};
export default {
  name: 'UserDetail',
  components: {
    UserInfo,
    Pictures
  },
  data () {
    return {
      tabPosition: 'left',
      // id: this.$route.query.id,
      id: 8,
      tableData: [],
      picUrl: "",
      type: "",
      datas: [],
      dataTypes: [],
      totalNum: 0,
      currentPage: 1,
      pageSize: 2
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
      let url = URLS.lostBabyFindByUserUrl;
      request(url, { size: this.pageSize, page: this.currentPage - 1, user: this.id })
        .then(data => {
          console.log(data);
          if (data.rtnCode == 200) {
            this.totalNum = data.data.totalElements;
            this.datas = JSON.parse(JSON.stringify(data.data.content));
            this.dataTypes = [];
            for (var i = 0, len = this.datas.length; i < len; i++) {
              this.dataTypes.push(this.choosed);
            }
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
