webpackJsonp([18],{"7W1C":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a("Dd8w"),o=a.n(n),i={name:"Statistics",components:{RangeDate:a("JpjP").a},data:function(){return{userInfo:{visiable:!1,data:{}},userAdd:{visiable:!1},filteredInfo:null,sortedInfo:null,paginationInfo:null,dataSource:[],selectedRowKeys:[],loading:!1,pagination:{pageSizeOptions:["10","20","30","40","100"],defaultCurrent:1,defaultPageSize:10,showQuickJumper:!0,showSizeChanger:!0,showTotal:function(e,t){return"显示 "+t[0]+" ~ "+t[1]+" 条记录，共 "+e+" 条记录"}}}},computed:{columns:function(){var e=this.sortedInfo,t=this.filteredInfo;return e=e||{},t=t||{},[{title:"用户名",dataIndex:"userName",sorter:!0,sortOrder:"username"===e.columnKey&&e.order},{title:"本月完成单数",dataIndex:"month"},{title:"完成额度",dataIndex:"total"},{}]}},mounted:function(){this.fetch()},methods:{exportExcel:function(){var e=this.sortedInfo,t=this.filteredInfo,a=void 0,n=void 0;e&&(a=e.field,n=e.order),this.$export("statistics/excel",o()({sortField:a,sortOrder:n},this.queryParams,t))},fetch:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,this.paginationInfo?(this.$refs.TableInfo.pagination.current=this.paginationInfo.current,this.$refs.TableInfo.pagination.pageSize=this.paginationInfo.pageSize,t.pageSize=this.paginationInfo.pageSize,t.pageNum=this.paginationInfo.current):(t.pageSize=this.pagination.defaultPageSize,t.pageNum=this.pagination.defaultCurrent),this.$get("statistics",o()({},t)).then(function(t){var a=t.data,n=o()({},e.pagination);n.total=a.total,e.dataSource=a.rows,e.pagination=n,e.loading=!1})}}},s={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-card",{staticClass:"card-area",attrs:{bordered:!1}},[a("div",[a("div",{staticClass:"operator"},[a("a-button",{directives:[{name:"hasPermission",rawName:"v-hasPermission",value:"user:export",expression:"'user:export'"}],attrs:{type:"primary",ghost:""},on:{click:e.exportExcel}},[e._v("导出Excel")])],1),e._v(" "),a("a-table",{ref:"TableInfo",attrs:{columns:e.columns,dataSource:e.dataSource,pagination:e.pagination,loading:e.loading,rowSelection:{selectedRowKeys:e.selectedRowKeys,onChange:e.onSelectChange},scroll:{x:900}},on:{change:e.handleTableChange},scopedSlots:e._u([{key:"email",fn:function(t,n){return[a("a-popover",{attrs:{placement:"topLeft"}},[a("template",{slot:"content"},[a("div",[e._v(e._s(t))])]),e._v(" "),a("p",{staticStyle:{width:"150px","margin-bottom":"0"}},[e._v(e._s(t))])],2)]}}])})],1),e._v(" "),a("user-info",{attrs:{userInfoData:e.userInfo.data,userInfoVisiable:e.userInfo.visiable},on:{close:e.handleUserInfoClose}})],1)},staticRenderFns:[]};var r=a("VU/8")(i,s,!1,function(e){a("ER0j")},"data-v-72492804",null);t.default=r.exports},ER0j:function(e,t){}});
//# sourceMappingURL=18.a8ff49bd2913308d7720.js.map