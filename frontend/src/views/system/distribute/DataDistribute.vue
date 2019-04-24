<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :width="350"
    @ok="handleSubmit"
    :confirmLoading="loading"
    @cancel="handleCancleClick"
    title="分配">
        <a-form :form="form">
          <a-form-item label='选择操作员'>
            <a-tree-select
              :allowClear="true"
              :dropdownStyle="{ maxHeight: '220px', overflow: 'scroll' }"
              :treeData="operatorData"
              v-decorator="['userId',{rules: [{ required: true, message: '请选择操作员'}]}]"
              >
            </a-tree-select>
          </a-form-item>
        </a-form>
     <!-- <div class="drawer-bootom-button">
        <a-popconfirm title="确定放弃编辑？" @confirm="handleCancleClick" okText="确定" cancelText="取消">
          <a-button style="margin-right: .8rem">取消</a-button>
        </a-popconfirm>
        <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
      </div>-->
  </a-modal>
  <!--<a-modal
    title="Basic Modal"
    v-model="show"
    @ok="handleSubmit"
  >
    <p>Some contents...</p>
    <p>Some contents...</p>
    <p>Some contents...</p>
  </a-modal>-->
</template>

<script>
  const formItemLayout = {
    labelCol: { span: 4 },
    wrapperCol: { span: 18 }
  }
  export default {
    name: 'DataDistribute',
    props: {
      dataDistributeVisiable: {
        require: true,
        default: false
      }
    },
    data () {
      return {
        operatorData: [],
        value: undefined,
        finished: false,
        loading: false,
        dataIds:'',
        form: this.$form.createForm(this),
      }
    },
    methods: {
      reset () {
        this.value = ''
      },
      handleCancleClick () {
        this.loading = false
        this.form.resetFields()
        this.$emit('close')
      },
      setFormValues (user) {
        this.dataIds = user
      },
      handleSubmit () {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.loading = true
            let user = this.form.getFieldsValue()
            user.dataIds = this.dataIds
            this.$get('/ddata/distribute', {
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
    },
    /*mounted () {
      this.$get('/user').then((r) => {
          let operators = r.data.rows
          this.operatorData = [];
          let that = this;
          operators.forEach((op) => {
            that.operatorData.push({
              title : op.username,
              key:op.userId,
              value:op.userId
            })
          })
      })
    },*/
    computed: {
      show: {
        get: function () {
          return this.dataDistributeVisiable
        },
        set: function () {
        }
      }
    },
    watch: {
      /*value (value) {
//        this.$emit('change', value)
      },*/
      dataDistributeVisiable () {
        if (this.dataDistributeVisiable) {
          this.$get('/user').then((r) => {
            let operators = r.data.rows
            this.operatorData = [];
            let that = this;
            operators.forEach((op) => {
              that.operatorData.push({
                title : op.username,
                key:op.userId,
                value:op.userId+''
              })
            })
          })
        }
      }
    }
  }
</script>
