webpackJsonp([40],{OvzD:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("Dd8w"),i=a.n(r),s={name:"DataDistribute",props:{dataDistributeVisiable:{require:!0,default:!1}},data:function(){return{operatorData:[],value:void 0,finished:!1,loading:!1,dataIds:"",form:this.$form.createForm(this)}},methods:{reset:function(){this.value=""},handleCancleClick:function(){this.loading=!1,this.form.resetFields(),this.$emit("close")},setFormValues:function(e){this.dataIds=e},handleSubmit:function(){var e=this;this.form.validateFields(function(t,a){if(!t){e.loading=!0;var r=e.form.getFieldsValue();r.dataIds=e.dataIds,e.$get("/ddata/distribute",i()({},r)).then(function(t){e.loading=!1,e.$emit("success")}).catch(function(){e.loading=!1})}})}},computed:{show:{get:function(){return this.dataDistributeVisiable},set:function(){}}},watch:{dataDistributeVisiable:function(){var e=this;this.dataDistributeVisiable&&this.$get("/user").then(function(t){var a=t.data.rows;e.operatorData=[];var r=e;a.forEach(function(e){r.operatorData.push({title:e.username,key:e.userId,value:e.userId+""})})})}}},o={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-modal",{attrs:{centered:!0,keyboard:!1,width:350,confirmLoading:e.loading,title:"分配"},on:{ok:e.handleSubmit,cancel:e.handleCancleClick},model:{value:e.show,callback:function(t){e.show=t},expression:"show"}},[a("a-form",{attrs:{form:e.form}},[a("a-form-item",{attrs:{label:"选择操作员"}},[a("a-tree-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["userId",{rules:[{required:!0,message:"请选择操作员"}]}],expression:"['userId',{rules: [{ required: true, message: '请选择操作员'}]}]"}],attrs:{allowClear:!0,dropdownStyle:{maxHeight:"220px",overflow:"scroll"},treeData:e.operatorData}})],1)],1)],1)},staticRenderFns:[]},n=a("VU/8")(s,o,!1,null,null,null);t.default=n.exports}});
//# sourceMappingURL=40.958951905ff8457c7c62.js.map