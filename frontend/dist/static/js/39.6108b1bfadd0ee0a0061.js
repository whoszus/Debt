webpackJsonp([39],{ZkPy:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a("Dd8w"),s=a.n(n),o=a("g4PW"),r=a.n(o),i={labelCol:{span:3},wrapperCol:{span:18}},l={name:"ButtonAdd",props:{buttonAddVisiable:{default:!1}},data:function(){return{loading:!1,formItemLayout:i,form:this.$form.createForm(this),menuTreeKey:+new Date,button:{},checkedKeys:[],expandedKeys:[],menuTreeData:[]}},methods:{reset:function(){this.loading=!1,this.menuTreeKey=+new Date,this.expandedKeys=this.checkedKeys=[],this.button={},this.form.resetFields()},onClose:function(){this.reset(),this.$emit("close")},handleCheck:function(e){this.checkedKeys=e},expandAll:function(){this.expandedKeys=this.allTreeKeys},closeAll:function(){this.expandedKeys=[]},handleExpand:function(e){this.expandedKeys=e},handleSubmit:function(){var e=this,t=r()(this.checkedKeys.checked,void 0)?this.checkedKeys:this.checkedKeys.checked;t.length?t.length>1?this.$message.error("最多只能选择一个上级菜单，请修改"):this.form.validateFields(function(a,n){a||(e.loading=!0,t.length?e.button.parentId=t[0]:e.button.parentId="",e.button.type="1",e.$post("menu",s()({},e.button)).then(function(){e.reset(),e.$emit("success")}).catch(function(){e.loading=!1}))}):this.$message.error("请为按钮选择一个上级菜单")}},watch:{buttonAddVisiable:function(){var e=this;this.buttonAddVisiable&&this.$get("menu",{type:"0"}).then(function(t){e.menuTreeData=t.data.rows.children,e.allTreeKeys=t.data.ids})}}},m={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-drawer",{staticStyle:{height:"calc(100% - 55px)",overflow:"auto","padding-bottom":"53px"},attrs:{title:"新增按钮",maskClosable:!1,width:"650",placement:"right",closable:!1,visible:e.buttonAddVisiable},on:{close:e.onClose}},[a("a-form",{attrs:{form:e.form}},[a("a-form-item",e._b({attrs:{label:"按钮名称"}},"a-form-item",e.formItemLayout,!1),[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["menuName",{rules:[{required:!0,message:"按钮名称不能为空"},{max:10,message:"长度不能超过10个字符"}]}],expression:"['menuName',\n                 {rules: [\n                  { required: true, message: '按钮名称不能为空'},\n                  { max: 10, message: '长度不能超过10个字符'}\n                ]}]"}],model:{value:e.button.menuName,callback:function(t){e.$set(e.button,"menuName",t)},expression:"button.menuName"}})],1),e._v(" "),a("a-form-item",e._b({attrs:{label:"相关权限"}},"a-form-item",e.formItemLayout,!1),[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["perms",{rules:[{max:50,message:"长度不能超过50个字符"}]}],expression:"['perms',\n                 {rules: [\n                  { max: 50, message: '长度不能超过50个字符'}\n                ]}]"}],model:{value:e.button.perms,callback:function(t){e.$set(e.button,"perms",t)},expression:"button.perms"}})],1),e._v(" "),a("a-form-item",e._b({staticStyle:{"margin-bottom":"2rem"},attrs:{label:"上级菜单"}},"a-form-item",e.formItemLayout,!1),[a("a-tree",{key:e.menuTreeKey,attrs:{checkable:!0,checkStrictly:!0,expandedKeys:e.expandedKeys,treeData:e.menuTreeData},on:{check:e.handleCheck,expand:e.handleExpand}})],1)],1),e._v(" "),a("div",{staticClass:"drawer-bootom-button"},[a("a-dropdown",{staticStyle:{float:"left"},attrs:{trigger:["click"],placement:"topCenter"}},[a("a-menu",{attrs:{slot:"overlay"},slot:"overlay"},[a("a-menu-item",{key:"1",on:{click:e.expandAll}},[e._v("展开所有")]),e._v(" "),a("a-menu-item",{key:"2",on:{click:e.closeAll}},[e._v("合并所有")])],1),e._v(" "),a("a-button",[e._v("\n        树操作 "),a("a-icon",{attrs:{type:"up"}})],1)],1),e._v(" "),a("a-popconfirm",{attrs:{title:"确定放弃编辑？",okText:"确定",cancelText:"取消"},on:{confirm:e.onClose}},[a("a-button",{staticStyle:{"margin-right":".8rem"}},[e._v("取消")])],1),e._v(" "),a("a-button",{attrs:{type:"primary",loading:e.loading},on:{click:e.handleSubmit}},[e._v("提交")])],1)],1)},staticRenderFns:[]},c=a("VU/8")(l,m,!1,null,null,null);t.default=c.exports}});
//# sourceMappingURL=39.6108b1bfadd0ee0a0061.js.map