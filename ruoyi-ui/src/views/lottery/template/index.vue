<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--奖品数据-->
      <el-col :span="24" :xs="24">
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
          <el-table-column label="名称" align="center" key="name" prop="name" :show-overflow-tooltip="true"/>
          <el-table-column label="抽奖次数" align="center" key="count" prop="count" :show-overflow-tooltip="true"/>
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
                @click="handleDetail(scope.row)"
              >详情
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
            <el-form-item label="模板名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入模板名称" maxlength="300"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="抽奖次数" prop="count">
              <el-input v-model="form.count" placeholder="请输入中奖次数" maxlength="5"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-for="(item,index) in form.goods" :key="index">
          <el-col :span="12">
            <el-form-item :label="`奖品${index+1}设置`" prop="goods">
              <el-autocomplete
                v-model="form.goods[index].name"
                :fetch-suggestions="querySearchAsync"
                placeholder="搜索奖品"
                @select="handleSelect"
              ></el-autocomplete>
            </el-form-item>

          </el-col>
          <el-col :span="12">
            <el-form-item :label="`中奖概率`" prop="rate">
              <el-input v-model="form.goods[index].rate" maxlength="5" minlength="1">
                <template slot="append">%</template>
              </el-input>
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
import {getUser} from "@/api/system/user";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getGoodsByName} from "@/api/lottery/goods";
import {addTemplate, delTemplate, getTemplate, listTemplate, updateTemplate} from "@/api/lottery/template";

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
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
        name: undefined,
        goods: [
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
        ],
        count: 0,
        image: undefined,
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
      listTemplate(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
        name: undefined,
        goods: [
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
          {
            id: undefined,
            name: undefined,
            rate: 0,
          },
        ],
        count: 0,
        image: undefined,
        status: "0",
      };
      this.resetForm("form");
    },
    querySearchAsync(queryString, cb) {
      if (queryString == undefined) {
        queryString = "";
      }
      getGoodsByName(queryString).then(response => {
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
      getUser().then(response => {
        this.open = true;
        this.title = "添加中奖模板";
      });
    },
    handleDetail(row) {
      this.reset();
      const userId = row.id || this.ids;
      getTemplate(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "中奖模板详情";
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.id || this.ids;
      getTemplate(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改模板";
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
        return delTemplate(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    trueSubmit() {

      let rateSum = 0;
      let check = true;

      for (let index in this.form.goods) {
        const item = this.form.goods[index];
        if (item.name === undefined) {
          check = false;
          break;
        }
        if (parseFloat(item.rate) * 100 > 0) {
          rateSum += parseFloat(item.rate) * 100;
        }
      }

      if (!check) {
        this.$modal.msgError("奖品不能为空");
        return;
      }

      if (rateSum !== 10000) {
        this.$modal.msgError("所有奖品的中奖概率合必须为100%");
        return;
      }

      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTemplate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTemplate(this.form).then(response => {
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
