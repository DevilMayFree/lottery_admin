<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--奖品数据-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
          <el-form-item label="邀请码" prop="userName">
            <el-input
              v-model="queryParams.code"
              placeholder="請輸入邀請碼"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="dataList">
          <el-table-column label="编号" align="center" key="id" prop="id"/>
<!--          <el-table-column label="邀请码" align="center" key="code" prop="code" :show-overflow-tooltip="true"/>-->
          <el-table-column label="中奖商品名称" align="center" key="name" prop="name"
                           :show-overflow-tooltip="true"/>
          <el-table-column label="预览" align="center" key="image" prop="image">
            <template slot-scope="scope">
              <img v-if="scope.row.image && scope.row.image.startsWith('https://')" :src="scope.row.image" style="height: 40px;width: 40px">
              <img v-else :src="uploadUrl + scope.row.image" style="height: 40px;width: 40px">
            </template>
          </el-table-column>
          <el-table-column label="中奖时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.logTime) }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {queryLog} from "@/api/lottery/goods";

export default {
  name: "Query",
  dicts: ['lottery_action_status', 'sys_user_sex'],
  data() {
    return {
      uploadUrl: process.env.VUE_APP_BASE_API, // 图片服务器地址
      // 遮罩层
      loading: false,
      dataList: [],
      // 查询参数
      queryParams: {
        code: undefined
      }
    };
  },
  created() {
    // this.getList();
  },
  methods: {
    /** 查询模板列表 */
    getList() {
     const queryCode = this.queryParams.code;
      this.loading = true;
      queryLog(queryCode).then(response => {
          this.dataList = response.list;
          this.loading = false;
        }
      ).catch((e)=>{
        console.error(e)
        this.loading = false;
      })
    },
    // 表单重置
    reset() {
      this.form = {
        code: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  }
};
</script>
