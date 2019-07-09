<template>
  <div class="pic-container">
    <el-collapse accordion class="search-container">
      <el-collapse-item>
        <div slot="title" class="base-search-container" @click.stop>
          <el-input
            v-model="input"
            placeholder="请输入关键字"
            style="width:200px;"
            @click.stop="inputClick"
          ></el-input>
          <el-select v-model="type" placeholder="类型" style="width:100px;">
            <el-option
              v-for="item in types"
              :key="item.value"
              :label="item.value"
              :value="item.value"
            ></el-option>
          </el-select>
          <el-button icon="el-icon-search" circle @click="doSearch" class="search-btn"></el-button>
        </div>
        <div class="options-container">
          <el-select v-model="sex" placeholder="性别" style="width:100px;">
            <el-option
              v-for="item in sexs"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
          <el-select v-model="hasPhoto" placeholder="照片" style="width:100px;">
            <el-option
              v-for="item in hasPhotos"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
          <el-date-picker
            v-model="date"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions"
          ></el-date-picker>
        </div>
      </el-collapse-item>
    </el-collapse>
    <div class="content-margins">
    <el-tabs @tab-click="tabClick" style="margin-top:10px;">
      <el-tab-pane class="tab-container">
        <div slot="label">
          &nbsp&nbsp
          <span class="font-size-1-3em">家寻宝贝</span>
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
          <span class="font-size-1-3em">宝贝寻家</span>
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
    </div>
  </div>
</template>

<script>
import URLS from "@/config/config";
import { request, fetch } from "@/api/api";
import Pictures from "./components/Pictures";
export default {
  name: "FaceWall",
  components: {
    Pictures
  },
  data() {
    return {
      hasPhoto: "",
      input: "",
      sex: "",
      date: "",
      type: "",
      datas: [],
      dataTypes: [],
      choosed: 0,
      totalNum: 0,
      currentPage: 1,
      pageSize: 12,
      sexs: [
        {
          value: 0,
          label: "全部"
        },
        {
          value: 1,
          label: "男"
        },
        {
          value: 2,
          label: "女"
        }
      ],
      hasPhotos: [
        {
          value: 0,
          label: "全部"
        },
        {
          value: 1,
          label: "有照片"
        },
        {
          value: 2,
          label: "无照片"
        }
      ],
      types: [
        {
          value: "全部"
        },
        {
          value: "姓名"
        },
        {
          value: "特征"
        },
        {
          value: "失踪地点"
        }
      ],
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      }
    };
  },
  methods: {
    inputClick() {
      console.log("asdasd");
    },
    doSearch() {
      console.log("search");
      console.log(this.$refs.pictures0);
    },
    loadData() {
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
            // console.log("datas2:",this.datas,"type2:",this.dataTypes);
            // this.$refs["pictures"+this.choosed].reloadData();

            // 通过使用vif重新加载子组件
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
    tabClick(e) {
      this.choosed = e.index;
      this.loadData();
    },
    handleCurrentChange(e) {
      console.log(e);
      this.currentPage = e;
      this.loadData();
    }
  },
  mounted() {
    this.loadData();
  }
};
</script>

<style scoped>
.tab-container {
  height: 100%;
}
.content-container .el-tabs {
  border-radius: 5px;
}
.to-right {
  text-align: right;
}
.base-search-container {
  width: 100%;
  text-align: right;
  position: relative;
}
.base-search-container /deep/ .el-button {
  width: 40px;
  height: 40px;
}
.base-search-container /deep/ .el-select {
  padding-right: 5px;
}
.search-btn {
  margin-left: 10px;
  margin-top: 4px;
  position: relative;
  margin-right: 20px;
  float: right;
}
.search-container {
  /* padding-bottom: 10px; */
}
.search-container /deep/ .el-collapse-item__content {
  padding-bottom: 10px;
}
.content-container /deep/ .is-active {
  height: 100%;
}
.content-container .el-tabs {
  margin-top: 20px !important;
}
.content-container .el-collapse {
  /* border-radius: 10px; */
}
.options-container {
  text-align: right;
  padding-right: 20px;
}
.pic-container {
  background-color: white
}
.content-margins {
  margin: 10px 20px;
}
</style>
