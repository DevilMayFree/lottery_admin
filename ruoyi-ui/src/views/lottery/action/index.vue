<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--奖品数据-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="邀请码" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入邀请码"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="状态"
              clearable
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.lottery_action_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增
            </el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="编号" align="center" key="id" prop="id"/>
          <el-table-column label="邀请码" align="center" key="code" prop="code" :show-overflow-tooltip="true"/>
          <el-table-column label="关联模板" align="center" key="templateName" prop="templateName"
                           :show-overflow-tooltip="true"/>
          <el-table-column label="状态" align="center" key="status" prop="status"
                           :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span v-if="scope.row.status == '0' ">未使用</span>
              <span v-if="scope.row.status == '1' ">未中奖</span>
              <span v-if="scope.row.status == '2' ">中奖</span>
              <span v-if="scope.row.status == '3' ">已兑换</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleCopy(scope.row)"
              >复制分享链接
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleDone(scope.row)"
              >兑奖
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改奖品配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="邀请码" prop="name">
              <el-input v-model="form.code" disabled placeholder="点击生成邀请码" maxlength="300"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-button @click="createCode">生成邀请码</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="选择模板" prop="templateName">
              <el-autocomplete
                v-model="form.templateName"
                :fetch-suggestions="querySearchAsync"
                placeholder="搜索模板"
                @select="handleSelect"
              ></el-autocomplete>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getShare, addCode, delCode, getCode, listCode, updateCode, createCode, actionDone} from "@/api/lottery/code";
import {getTemplatesByName} from "@/api/lottery/template";

export default {
  name: "User",
  dicts: ['lottery_action_status', 'sys_user_sex'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 奖品表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {
        id: undefined,
        code: undefined,
        templateId: undefined,
        templateName: undefined,
        status: "0",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询模板列表 */
    getList() {
      this.loading = true;
      listCode(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    createCode() {
      createCode().then(response => {
        this.form.code = response.data;
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        code: undefined,
        templateId: undefined,
        templateName: undefined,
        status: "0",
      };
      this.resetForm("form");
    },
    querySearchAsync(queryString, cb) {
      if (queryString == undefined) {
        queryString = "";
      }
      getTemplatesByName(queryString).then(response => {
        const findOut = response.data;
        const result = findOut.map(x => ({...x, value: x.name}))
        cb(result);
      })
    },
    handleSelect(item) {
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.deptId = undefined;
      this.$refs.tree.setCurrentKey(null);
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加抽奖";
    },
    handleCopy(row) {
      const shareCode = row.code;
      const that = this;
      getShare().then(response => {
        const shareLink = response.data + shareCode;

        this.$copyText(shareLink).then(e => {
          that.$modal.msgSuccess("复制成功");
        }, function (e) {
          that.$modal.msgError("复制失败");
        })
      })
    },
    handleDone(row) {
      const id = row.id;
      const that = this;
      actionDone(id).then(response => {
        that.$modal.msgSuccess("该邀请码已兑奖");
        this.getList();
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.id || this.ids;
      getCode(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改抽奖";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.trueSubmit();
      if (this.form.id != undefined) {
        this.trueSubmit();
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids;
      this.$modal.confirm('是否确认删除模板编号为"' + id + '"的数据项？').then(function () {
        return delCode(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    trueSubmit() {
      if (this.form.code === undefined) {
        this.$modal.msgError("邀请码不能为空");
        return;
      }

      if (this.form.templateName === undefined) {
        this.$modal.msgError("中奖模板不能为空");
        return;
      }

      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCode(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCode(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
  }
};
</script>
