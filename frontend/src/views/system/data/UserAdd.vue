<template>
  <a-drawer
    title="新增用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='客户名称' v-bind="formItemLayout">
        <a-input style="width: 100%"
                 v-model="user.clientName"
                 v-decorator="['clientName',
                   {rules: [
                    { required: true, message: '客户名称不能为空'},
                    { max: 20, message: '长度不能超过10个字'}
                  ]}]"/>
      </a-form-item>
      <a-form-item label='身份证号' v-bind="formItemLayout">
        <a-input v-model="user.clientIdNum"
                 v-decorator="['clientIdNum',
                   {rules: [
                    { max: 18, message: '长度不能超过18个字'}
                  ]}]"/>
      </a-form-item>
      <a-form-item label='客户电话' v-bind="formItemLayout">
        <a-input v-model="user.clientPhone"
                 v-decorator="['clientPhone',
                   {rules: [
                    { required: true, message: '电话不能为空'}
                  ]}]"/>
      </a-form-item>
      <a-form-item label='状态' v-bind="formItemLayout">
        <a-radio-group readOnly
          v-model="user.dataStatus"
          v-decorator="['dataStatus',{rules: [{ required: true, message: '请选择状态'}]}]">
          <a-radio defaultChecked value="init">待签约</a-radio>
          <a-radio value="finish">放款</a-radio>
          <a-radio value="refused">拒绝</a-radio>
         <!-- <a-radio disabled value="redist">重新配分</a-radio>-->
        </a-radio-group>
      </a-form-item>
      <a-form-item label='详情' v-bind="formItemLayout">
        <a-textarea
          :rows="24"
          v-model="user.describe"
          v-decorator="[
          'describe']">
        </a-textarea>
      </a-form-item>
    </a-form>
      <div class="drawer-bootom-button">
        <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
          <a-button style="margin-right: .8rem">取消</a-button>
        </a-popconfirm>
        <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
      </div>
  </a-drawer>
</template>
<script>

  import {mapState, mapMutations} from 'vuex'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'UserAdd',
  props: {
    userAddVisiable: {
      default: false
    }
  },
  data () {
    return {
      user: {
        clientName: '',
        clientIdNum:'',
        clientPhone:'',
        describe:''
      },
      loading: false,
      roleData: [],
      deptTreeData: [],
      formItemLayout,
//      defaultPassword: '1234qwer',
      form: this.$form.createForm(this),
      validateStatus: 'success',
      help: ''
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  methods: {
    reset () {
//      this.validateStatus = ''
      this.help = ''
      this.user =  {
        clientName: '',
        clientIdNum:'',
        clientPhone:'',
        describe:''
      };
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      /*if (this.validateStatus !== 'success') {
        this.handleUserNameBlur()
      }*/
      this.form.validateFields((err, values) => {
        if (!err && this.validateStatus === 'success') {
          this.loading = true
          this.user.createUserId = this.currentUser.username;
          this.$post('ddata/create', {
            ...this.user
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    handleUserNameBlur () {
      let username = this.user.username.trim()
      if (username.length) {
        if (username.length > 10) {
          this.validateStatus = 'error'
          this.help = '用户名不能超过10个字符'
        } else if (username.length < 2) {
          this.validateStatus = 'error'
          this.help = '用户名不能少于2个字符'
        } else {
          this.validateStatus = 'validating'
          this.$get(`user/check/${username}`).then((r) => {
            if (r.data) {
              this.validateStatus = 'success'
              this.help = ''
            } else {
              this.validateStatus = 'error'
              this.help = '抱歉，该用户名已存在'
            }
          })
        }
      } else {
        this.validateStatus = 'error'
        this.help = '用户名不能为空'
      }
    }
  }
}
</script>
