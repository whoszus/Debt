<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row >
        <div :class="advanced ? null: 'fold'">
            <a-col :sm="5" >
              <a-form-item>
                <a-input v-model="queryParams.clientPhone" placeholder="客户手机号">
                 <a-icon slot="prefix" type="mobile" />
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :sm="5" >
              <a-form-item>
                <a-input v-model="queryParams.dataSource" placeholder="数据源">
                   <a-icon slot="prefix" type="lock" />
                </a-input>
              </a-form-item>
            </a-col>
            <a-col :sm="5" >
              <a-form-item>
                <a-input v-model="queryParams.operatorName" placeholder="业务员姓名">
                   <a-icon slot="prefix" type="user" />
                </a-input>
              </a-form-item>
            </a-col>
        </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
             <!--<a @click="toggleAdvanced" style="margin-left: 8px">
              {{advanced ? '收起' : '展开'}}
              <a-icon :type="advanced ? 'up' : 'down'" />
            </a>-->
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">

        <!--<a-button @click="import" v-hasPermission="'user:export'">导入</a-button>-->
        <a-upload
          class="upload-area"
          :fileList="fileList"
          :remove="handleRemove"
          :disabled="fileList.length === 1"
          :beforeUpload="beforeUpload"
          v-hasPermission="'user:export'" >
          <a-button>
            <a-icon type="upload" /> 选择.xlsx文件
          </a-button>
        </a-upload>
        <a-button v-hasPermission="'user:export'" type="primary" @click="downloadTemplate" style="margin-right: .5rem">
          模板下载
        </a-button>
        <a-button
          v-hasPermission="'data:import'"
          @click="handleUpload"
          :disabled="fileList.length === 0"
          :loading="uploading">
          {{uploading ? '导入中' : '导入Excel' }}
        </a-button>
        <a-button type="primary" ghost @click="add" v-hasPermission="'user:add'">新增</a-button>
        <a-button type="primary" ghost @click="search" >刷新</a-button>
        <a-button type="primary" ghost @click="distribute" >分配</a-button>
        <a-button type="danger" ghost @click="batchDelete" >删除</a-button>

        <!--<a-tree-select
          v-show="showSelectOperator"
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'scroll' }"
          :treeData="operatorData"
          @change="onOperatorChange">
        </a-tree-select>-->
        <!--<a-dropdown v-hasAnyPermission="'user:reset','user:export'">
          <a-menu slot="overlay">
            &lt;!&ndash;<a-menu-item v-hasPermission="'user:reset'" key="password-reset" @click="resetPassword">密码重置</a-menu-item>&ndash;&gt;
            <a-menu-item v-hasPermission="'user:export'" key="export-data" @click="exportExcel">导出Excel</a-menu-item>
          </a-menu>
          <a-button>
            更多操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>-->
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="remark" slot-scope="text, record">
          <a-popover placement="topLeft">
            <template slot="content">
              <div style="max-width: 200px">{{text}}</div>
            </template>
            <p style="width: 200px;margin-bottom: 0">{{text}}</p>
          </a-popover>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon v-hasPermission="'user:update'" type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修改用户"></a-icon>
          &nbsp;
          <a-icon v-hasPermission="'user:view'" type="eye" theme="twoTone" twoToneColor="#42b983" @click="view(record)" title="查看"></a-icon>
          <a-badge v-hasNoPermission="'user:update','user:view'" status="warning" text="无权限"></a-badge>
        </template>
      </a-table>
      <import-result
        @close="handleClose"
        :importData="importData"
        :errors="errors"
        :times="times"
        :importResultVisible="importResultVisible">
      </import-result>
    </div>
    <!-- 用户信息查看 -->
    <user-info
      :userInfoData="userInfo.data"
      :userInfoVisiable="userInfo.visiable"
      @close="handleUserInfoClose">
    </user-info>
    <!-- 新增用户 -->
    <user-add
      @close="handleUserAddClose"
      @success="handleUserAddSuccess"
      :userAddVisiable="userAdd.visiable">
    </user-add>
    <!-- 修改用户 -->
    <user-edit
      ref="userEdit"
      @close="handleUserEditClose"
      @success="handleUserEditSuccess"
      :userEditVisiable="userEdit.visiable">
    </user-edit>
    <!--分配用户 -->
    <data-distribute
      ref="dataDistribute"
      @close="handleDataDistributeClose"
      @success="handleDataDistributeSuccess"
      :dataDistributeVisiable="dataDistribute.visiable">
    </data-distribute>
  </a-card>
</template>

<script>
import UserInfo from '../data/UserInfo'
import RangeDate from '@/components/datetime/RangeDate'
import UserAdd from '../data/UserAdd'
import UserEdit from '../data/UserEdit'
import ImportResult from '../../others/DataImportResult'
import DataDistribute from './DataDistribute'
import {mapState, mapMutations} from 'vuex'
export default {
  name: 'User',
  components: {UserInfo, UserAdd, UserEdit, RangeDate,DataDistribute,ImportResult},
  data () {
    return {
      fileList: [],
      importData: [],
      errors: [],
      times: '',
      uploading: false,
      advanced: false,
      importResultVisible: false,
      userInfo: {
        visiable: false,
        data: {}
      },
      userAdd: {
        visiable: false
      },
      userEdit: {
        visiable: false
      },
      dataDistribute: {
        visiable: false
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
//      operatorData:[],
      loading: false,
//      showSelectOperator:false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      }
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      let { sortedInfo, filteredInfo } = this
      sortedInfo = sortedInfo || {}
      filteredInfo = filteredInfo || {}
      return [{
        title: '姓名',
        dataIndex: 'clientName'
      }, {
        title: '身份',
        dataIndex: 'clientIdNum'
      }, {
        title: '电话号码',
        dataIndex: 'clientPhone'
      }, {
        title: '数据来源',
        dataIndex: 'dataSource'
      }, {
        title: '业务员',
        dataIndex: 'operatorName'
      }, {
        title: '金额',
        dataIndex: 'amount'
      },{
        title: '状态',
        dataIndex: 'dataStatus',
        customRender: (text, row, index) => {
          switch (text) {
            case 'init':
              return <a-tag color="blue">待分配</a-tag>
            case 'redist':
              return <a-tag color="orange">重新配分</a-tag>
            case 'finish':
              return <a-tag color="green">放款</a-tag>
            case 'refused':
              return <a-tag color="red">拒绝</a-tag>
            default:
              return text
          }
        },
        filters: [
          { text: '待分配', value: 'init' },
          { text: '重新配分', value: 'redist' },
          { text: '放款', value: 'finish' },
          { text: '拒绝', value: 'refused' },
        ],
        filterMultiple: false,
        filteredValue: filteredInfo.dataStatus || null,
        onFilter: (value, record) => record.dataStatus.includes(value)
      },{
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' }
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    handleClose () {
      this.importResultVisible = false
    },
    downloadTemplate () {
      this.$download('ddata/template', {}, '导入模板.xlsx')
    },
    handleRemove (file) {
      if (this.uploading) {
        this.$message.warning('文件导入中，请勿删除')
        return
      }
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
    },
    beforeUpload (file) {
      this.fileList = [...this.fileList, file]
      return false
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true
      this.$upload('ddata/import', formData).then((r) => {
        let data = r.data.data
        if (data.data.length) {
          this.fetch()
        }
        this.importData = data.data
        this.errors = data.error
        this.times = data.time / 1000
        this.uploading = false
        this.fileList = []
        this.importResultVisible = true
      }).catch((r) => {
        console.error(r)
        this.uploading = false
        this.fileList = []
      })
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
      if (!this.advanced) {
        this.queryParams.createTimeFrom = ''
        this.queryParams.createTimeTo = ''
      }
    },
    view (record) {
      this.userInfo.data = record
      this.userInfo.visiable = true
    },
    add () {
      this.userAdd.visiable = true
    },
    handleUserAddClose () {
      this.userAdd.visiable = false
    },
    handleUserAddSuccess () {
      this.userAdd.visiable = false
      this.$message.success('新增客户成功')
      this.search()
    },
    edit (record) {
      this.$refs.userEdit.setFormValues(record)
      this.userEdit.visiable = true
    },
    handleUserEditClose () {
      this.userEdit.visiable = false
    },
    handleUserEditSuccess () {
      this.userEdit.visiable = false
      this.$message.success('修改客户信息成功')
      this.search()
    },
    handleUserInfoClose () {
      this.userInfo.visiable = false
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    handleDateChange (value) {
      if (value) {
        this.queryParams.createTimeFrom = value[0]
        this.queryParams.createTimeTo = value[1]
      }
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let userIds = []
          for (let key of that.selectedRowKeys) {
            userIds.push(that.dataSource[key].id)
          }
          that.$delete('ddata/delete/' + userIds.join(',')).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    distribute () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要分配的数据')
        return
      }
      let userids = []
      for (let key of this.selectedRowKeys) {
        userids.push(this.dataSource[key].id)
      }
      this.$refs.dataDistribute.setFormValues(userids.join(','))
      this.dataDistribute.visiable = true
    },
    handleDataDistributeClose () {
      this.dataDistribute.visiable = false
    },
    handleDataDistributeSuccess () {
      this.dataDistribute.visiable = false
      this.$message.success('分配成功')
      this.search()
    },
    exportExcel () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('user/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      // 清空部门树选择
//      this.$refs.deptTree.reset()
      // 清空时间选择
      if (this.advanced) {
        this.$refs.createTime.reset()
      }
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.userInfo.visiable = false
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      params.userId = this.currentUser.userId;
      this.$get('ddata', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  }
}
</script>
<style lang="less" scoped>
  .upload-area {
    display: inline;
    float: left;
    /*width: 50%*/
  }
  .button-area {
    margin-left: 1rem;
    display: inline;
    /*float: right;*/
    /*width: 30%;*/
  }
@import "../../../../static/less/Common";
</style>
