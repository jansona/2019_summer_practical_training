<template>
  <el-tabs :tab-position="tabPosition">
    <el-tab-pane label="基本信息">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>卡片名称</span>
          <el-button style="float: right; padding: 3px 0" type="text">编辑</el-button>
        </div>
        <div>
          <el-table
            :data="tableData"
            stripe
            style="width: 90%"
            :show-header="false"
            size="small">
            <el-table-column prop="name" width="180"></el-table-column>
            <el-table-column prop="content"></el-table-column>
          </el-table>
        </div>
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="我的失踪者申报">配置管理</el-tab-pane>
    <el-tab-pane label="我的文章">角色管理</el-tab-pane>
    <el-tab-pane label="我的评论">定时任务补偿</el-tab-pane>
</el-tabs>
</template>

<script>
import URLS from '@/config/config'
import { request,fetch } from "@/api/api";
const nameDict = {
  name: "姓名",
  tel: "邮箱",
  email: "联系地址"
};
export default {
  name: 'UserDetail',
  data () {
    return {
      tabPosition: 'left',
      // id: this.$route.query.id,
      id: 1,
      tableData: [],
      picUrl: ""
    }
  },
  methods: {
    loadData() {
      let url = URLS.userFindByIdUrl;
      request(url, { id: this.id })
        .then(data => {
          if (data.rtnCode == 200) {
            this.picUrl =
              URLS.baseUrl + "/resource/photo/profile/" + this.id + ".jpg";

            let content = data.data.content[0];

            for (var key in nameDict) {
              if (content.hasOwnProperty(key)) {
                this.tableData.push({
                  name: nameDict[key],
                  content: content[key]
                });
              }
            }
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    showImg(url) {
      if (url === null || url === "") {
        // console.log('待加载的图片地址为:' + url)
        return require("@/assets/house.jpg");
      } else {
        // console.log('待加载的图片地址为:' + url)
        return url;
      }
    }
  },
  mounted() {
    this.loadData();
  }
}
</script>

<style scoped>

  .text {
    font-size: 14px;
  }

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
