webpackJsonp([21],{Hd9R:function(t,e){},f1tp:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=a("BO1k"),n=a.n(r),o=a("PJh5"),s=a.n(o);s.a.locale("zh-cn");var c={data:function(){return{advanced:!1,dataSource:[],pagination:{defaultPageSize:10,defaultCurrent:1,pageSizeOptions:["10","20","30","40","100"],showQuickJumper:!0,showSizeChanger:!0,showTotal:function(t,e){return"显示 "+e[0]+" ~ "+e[1]+" 条记录，共 "+t+" 条记录"}},loading:!1}},computed:{columns:function(){var t=this.$createElement;return[{title:"请求时间",dataIndex:"timestamp",customRender:function(t,e,a){return s()(t).format("YYYY-MM-DD HH:mm:ss")}},{title:"请求方法",dataIndex:"request.method",customRender:function(e,a,r){switch(e){case"GET":return t("a-tag",{attrs:{color:"#87d068"}},[e]);case"POST":return t("a-tag",{attrs:{color:"#2db7f5"}},[e]);case"PUT":return t("a-tag",{attrs:{color:"#ffba5a"}},[e]);case"DELETE":return t("a-tag",{attrs:{color:"#f50"}},[e]);default:return e}},filters:[{text:"GET",value:"GET"},{text:"POST",value:"POST"},{text:"PUT",value:"PUT"},{text:"DELETE",value:"DELETE"}],filterMultiple:!0,onFilter:function(t,e){return e.request.method.includes(t)}},{title:"请求URL",dataIndex:"request.uri",customRender:function(t,e,a){return t.split("?")[0]}},{title:"响应状态",dataIndex:"response.status",customRender:function(e,a,r){return e<200?t("a-tag",{attrs:{color:"pink"}},[e]):e<201?t("a-tag",{attrs:{color:"green"}},[e]):e<399?t("a-tag",{attrs:{color:"cyan"}},[e]):e<403?t("a-tag",{attrs:{color:"orange"}},[e]):e<501?t("a-tag",{attrs:{color:"red"}},[e]):e}},{title:"请求耗时",dataIndex:"timeTaken",customRender:function(e,a,r){return t("a-tag",e<500?{attrs:{color:"green"}}:e<1e3?{attrs:{color:"cyan"}}:e<1500?{attrs:{color:"orange"}}:{attrs:{color:"red"}},[e," ms"])}}]}},mounted:function(){this.fetch()},methods:{search:function(){this.fetch()},handleTableChange:function(t,e,a){this.fetch()},fetch:function(){var t=this;this.loading=!0,this.$get("actuator/httptrace").then(function(e){var a=e.data;t.loading=!1;var r=[],o=!0,s=!1,c=void 0;try{for(var u,i=n()(a.traces);!(o=(u=i.next()).done);o=!0){var l=u.value;"OPTIONS"!==l.request.method&&-1===l.request.uri.indexOf("httptrace")&&r.push(l)}}catch(t){s=!0,c=t}finally{try{!o&&i.return&&i.return()}finally{if(s)throw c}}t.dataSource=r})}}},u={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("a-card",{staticClass:"card-area",attrs:{bordered:!1}},[a("div",[a("div",{staticClass:"alert"},[a("a-alert",{attrs:{type:"success","show-icon":!0}},[a("div",{attrs:{slot:"message"},slot:"message"},[t._v("\n          共追踪到 "+t._s(t.dataSource.length)+" 条近期HTTP请求记录\n          "),a("a",{staticStyle:{"margin-left":"24px"},on:{click:t.search}},[t._v("点击刷新")])])])],1),t._v(" "),a("a-table",{attrs:{columns:t.columns,dataSource:t.dataSource,pagination:t.pagination,loading:t.loading,scroll:{x:900}},on:{change:t.handleTableChange}})],1)])},staticRenderFns:[]};var i=a("VU/8")(c,u,!1,function(t){a("Hd9R")},"data-v-53780da6",null);e.default=i.exports}});
//# sourceMappingURL=21.acdf92d55a71cdab7c95.js.map