webpackJsonp([45],{j5Q7:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("Dd8w"),o=a.n(r),s=a("fZjL"),i=a.n(s),n=a("+6Bu"),m=a.n(n),l={labelCol:{span:4},wrapperCol:{span:18}},c={name:"JobEdit",props:{jobEditVisiable:{default:!1}},data:function(){return{loading:!1,formItemLayout:l,form:this.$form.createForm(this),validateStatus:"",help:"",job:{}}},methods:{reset:function(){this.loading=!1,this.validateStatus=this.help="",this.job={},this.form.resetFields()},onClose:function(){this.reset(),this.$emit("close")},open:function(){window.open("http://cron.qqe2.com/")},setFormValues:function(e){var t=this,a=m()(e,[]);this.job.jobId=a.jobId,this.job.status=a.status;var r=["beanName","methodName","params","cronExpression","remark"];i()(a).forEach(function(e){if(-1!==r.indexOf(e)){t.form.getFieldDecorator(e);var o={};o[e]=a[e],t.form.setFieldsValue(o)}})},handleSubmit:function(){var e=this;"success"!==this.validateStatus&&this.checkCron(),this.form.validateFields(function(t,a){if(!t){var r=e.form.getFieldsValue();r.jobId=e.job.jobId,r.status=e.job.status,e.$put("job",o()({},r)).then(function(){e.reset(),e.$emit("success")}).catch(function(){e.loading=!1})}})},checkCron:function(){var e=this,t=this.form.getFieldValue("cronExpression");t.length?this.$get("job/cron/check?cron="+t).then(function(t){t.data?(e.validateStatus="success",e.help=""):(e.validateStatus="error",e.help="请填写合法的Cron表达式")}):(this.validateStatus="error",this.help="请填写Cron表达式")}}},u={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-drawer",{staticStyle:{height:"calc(100% - 55px)",overflow:"auto","padding-bottom":"53px"},attrs:{title:"修改定时任务",maskClosable:!1,width:"650",placement:"right",closable:!1,visible:e.jobEditVisiable},on:{close:e.onClose}},[a("a-form",{attrs:{form:e.form}},[a("a-form-item",e._b({attrs:{label:"Bean名称"}},"a-form-item",e.formItemLayout,!1),[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["beanName",{rules:[{required:!0,message:"Bean名称不能为空"},{max:50,message:"长度不能超过50个字符"}]}],expression:"['beanName',\n                 {rules: [\n                  { required: true, message: 'Bean名称不能为空'},\n                  { max: 50, message: '长度不能超过50个字符'}\n                ]}]"}],staticStyle:{width:"100%"}})],1),e._v(" "),a("a-form-item",e._b({attrs:{label:"方法名称"}},"a-form-item",e.formItemLayout,!1),[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["methodName",{rules:[{required:!0,message:"方法名称不能为空"},{max:50,message:"长度不能超过50个字符"}]}],expression:"['methodName',\n                 {rules: [\n                  { required: true, message: '方法名称不能为空'},\n                  { max: 50, message: '长度不能超过50个字符'}\n                ]}]"}]})],1),e._v(" "),a("a-form-item",e._b({attrs:{label:"方法参数"}},"a-form-item",e.formItemLayout,!1),[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["params",{rules:[{max:50,message:"长度不能超过50个字符"}]}],expression:"['params',\n                 {rules: [\n                  { max: 50, message: '长度不能超过50个字符'}\n                ]}]"}]})],1),e._v(" "),a("a-form-item",e._b({attrs:{label:"Cron表达式",validateStatus:e.validateStatus,help:e.help}},"a-form-item",e.formItemLayout,!1),[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["cronExpression"],expression:"['cronExpression']"}],on:{blur:e.checkCron}},[a("a-icon",{staticStyle:{cursor:"pointer"},attrs:{slot:"addonAfter",type:"read"},on:{click:e.open},slot:"addonAfter"})],1)],1),e._v(" "),a("a-form-item",e._b({attrs:{label:"备注信息"}},"a-form-item",e.formItemLayout,!1),[a("a-textarea",{directives:[{name:"decorator",rawName:"v-decorator",value:["remark",{rules:[{max:100,message:"长度不能超过100个字符"}]}],expression:"[\n        'remark',\n        {rules: [\n          { max: 100, message: '长度不能超过100个字符'}\n        ]}]"}],attrs:{rows:4}})],1)],1),e._v(" "),a("div",{staticClass:"drawer-bootom-button"},[a("a-popconfirm",{attrs:{title:"确定放弃编辑？",okText:"确定",cancelText:"取消"},on:{confirm:e.onClose}},[a("a-button",{staticStyle:{"margin-right":".8rem"}},[e._v("取消")])],1),e._v(" "),a("a-button",{attrs:{type:"primary",loading:e.loading},on:{click:e.handleSubmit}},[e._v("提交")])],1)],1)},staticRenderFns:[]},d=a("VU/8")(c,u,!1,null,null,null);t.default=d.exports}});
//# sourceMappingURL=45.82823ecbf4fed8cfc682.js.map