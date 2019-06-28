<template>
  <div>
    <el-card>
      <div slot="header">
        <span class="font-size-3em">
          <i class="el-icon-document-copy"></i>
        </span>
        <span class="font-size-2em">&nbsp&nbsp登记信息</span>
      </div>
      <div>
        <el-row>
          <el-col :span="8">
            <div class="pic-container">
              <el-image :src="require('@/assets/house.jpg')">
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </div>
          </el-col>
          <el-col :span="1" class="divider-container">
            <el-divider direction="vertical"></el-divider>
          </el-col>
          <el-col :span="15">
            <el-card>
              <div slot="header">
                <span class="font-size-2em">
                  <i class="el-icon-user"></i>
                </span>
                <span class="font-size-1em">&nbsp&nbsp个人信息</span>
              </div>
              <el-table
                :data="tableData1"
                stripe
                style="width: 90%"
                :show-header="false"
                size="small"
              >
                <el-table-column prop="name" width="180"></el-table-column>
                <el-table-column prop="content"></el-table-column>
              </el-table>
            </el-card>
            <el-card>
              <div slot="header">
                <span class="font-size-2em">
                  <i class="el-icon-phone-outline"></i>
                </span>
                <span class="font-size-1em">&nbsp&nbsp{{type == 1? "联系人信息":"报备人信息"}}</span>
              </div>
              <el-table
                :data="tableData2"
                stripe
                style="width: 90%"
                :show-header="false"
                size="small"
              >
                <el-table-column prop="name" width="180"></el-table-column>
                <el-table-column prop="content"></el-table-column>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { request } from "@/api/api";
const nameDict1 = {
  name: "姓名",
  sex: "性别",
  birthday: "出生日期",
  height: "失踪时身高",
  place: "失踪地点",
  nativePlace: "籍贯",
  date: "失踪日期",
  babyDescription: "特征描述",
  missDescription: "失踪经过描述",
  backGround: "家庭背景",
  otherDescription: "其他信息",
  otherExplain: "其他说明"
};
const nameDict2 = {
  contactName: "姓名",
  contactRel: "关系",
  contactAddress: "联系地址",
  contactEmail: "联系邮箱",
  contactPhone: "联系电话",
  otherContactMethod: "其他联系方式"
};

export default {
  name: "FaceDetail",
  data() {
    return {
      tableData1: [],
      tableData2: [],
      type: this.$route.params.type,
      id: this.$route.params.id,
      picUrl: '',
    };
  },
  methods: {
    loadData() {
      let url = this.type == 1 ? URLS.lostBabyFindUrl : URLS.matchBabyFindUrl;
      this.tableData1.push({
        name: "寻亲类型",
        content: this.type == 1 ? "家寻宝贝" : "宝贝寻家"
      });
      request(url, { id: this.id })
        .then(data => {
          if (data.rtnCode == 200) {
            let content = data.data.content[0];
            for (var key in nameDict1) {
              if (content.hasOwnProperty(key)) {
                this.tableData1.push({
                  name: nameDict1[key],
                  content: content[key]
                });
              }
            }
            for (var key in nameDict2) {
              if (content.hasOwnProperty(key)) {
                this.tableData2.push({
                  name: nameDict2[key],
                  content: content[key]
                });
              }
            }
            
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  mounted() {
    this.loadData();
  }
};
</script>

<style scoped>
.divider-container /deep/ .el-divider {
  height: 956px;
}
.pic-container {
  margin: 150px auto 50px auto;
}
.pic-container .el-image {
  min-width: 100px;
  min-height: 100px;
  font-size: 3em;
  max-width: 250px;
  max-height: 350px;
  border: 5px solid #ddd;
  padding: 5px;
  background: #fff;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
