<template>
  <a-drawer
    title="修改用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='客户名' v-bind="formItemLayout">
        <a-input  v-decorator="['clientName',
                   {rules: [
                    { required: true, message: '客户名称不能为空'},
                    { max: 20, message: '长度不能超过10个字'}
                  ]}]"/>
      </a-form-item>
      <a-form-item label='身份证号' v-bind="formItemLayout">
        <a-input v-decorator="['clientIdNum',
                   {rules: [
                    { max: 18, message: '长度不能超过18个字'}
                  ]}]"/>
      </a-form-item>
      <a-form-item label="客户电话" v-bind="formItemLayout">
        <a-input v-decorator="['clientPhone']"/>
      </a-form-item>
      <a-form-item label='金额(万)' v-bind="formItemLayout" :help="help">
        <a-input-number
          type="number"
          :min="1"
          :parser="value => value.replace(/\$\s?|(,*)/g, '')"
          v-decorator="['amount']"
        />
      </a-form-item>
      <a-form-item label='状态' v-bind="formItemLayout">
        <a-radio-group @change="onChange" v-decorator="['dataStatus',
        {rules: [{ required: true, message: '请选择状态'}]}]">
          <a-radio value="init">待签约</a-radio>
          <a-radio value="finish">放款</a-radio>
          <a-radio value="refused">拒绝</a-radio>
          <!--<a-radio disabled value="redist">重新配分</a-radio>-->
        </a-radio-group>
      </a-form-item>
      <a-form-item label='详情' v-bind="formItemLayout">
        <a-textarea disabled
          :rows="10"
          v-decorator="[
          'describe']">
        </a-textarea>
      </a-form-item>
      <a-form-item label='操作' v-bind="formItemLayout">
        <a-textarea
          :rows="10" defaultValue=""
          v-decorator="[
          'describeAdd']">
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
  name: 'UserEdit',
  props: {
    userEditVisiable: {
      default: false
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      deptTreeData: [],
      roleData: [],
      userDept: [],
      id: '',
      loading: false,
      finished: false,
      help:""
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  methods: {
    ...mapMutations({
      setUser: 'account/setUser'
    }),
    onClose () {
      this.loading = false
      this.form.resetFields()
      this.$emit('close')
    },
    setFormValues ({...user}) {
      this.id = user.id
      let fields = ['clientName', 'clientIdNum', 'clientPhone', 'describe','describeAdd','dataStatus','amount']
      Object.keys(user).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          obj[key] = user[key]
          this.form.setFieldsValue(obj)
        }
      })
    },
    handleSubmit () {
      let user = this.form.getFieldsValue()
      if (!this.checkAmount()) {
        return;
      }
        this.form.validateFields((err, values) => {
          if (!err) {
            this.loading = true
            this.help = ''
            user.id = this.id
//            user.operatorName = this.currentUser.username
//          user.operatorId = parseFloat(this.currentUser.id)
            this.$post('ddata/update', {
              ...user
            }).then((r) => {
              this.loading = false
              this.$emit('success')
            }).catch(() => {
              this.loading = false
            })
          }
        })


    },
    onChange (e) {
      if(e.target.value === "finish"){
        this.finished = true
      }else{
        this.finished = false
        this.help = ''
      }
    },
    checkAmount () {
      let user = this.form.getFieldsValue()
      if (this.finished && !user.amount) {
        this.help = "金额不能为空"
        return false;
      }
      return true;
    }
  }
}
</script>
