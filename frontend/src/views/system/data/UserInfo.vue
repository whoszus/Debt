<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :footer="null"
    :width="750"
    @cancel="handleCancleClick"
    title="用户信息">
    <a-layout class="user-info">
      <!--<a-layout-sider class="user-info-side">
        <a-avatar shape="square" :size="115" icon="user" :src="`static/avatar/${userInfoData.avatar}`"/>
      </a-layout-sider>-->
      <a-layout-content class="user-content-one">
        <p><a-icon type="user"/>客户名：{{userInfoData.clientName}}</p>
        <p><a-icon type="skin"/>身份证号：{{userInfoData.clientIdNum ? userInfoData.clientIdNum : '暂无身份证信息'}}</p>
        <p><a-icon type="phone"/>客户电话：{{userInfoData.clientPhone ? userInfoData.clientPhone : '暂无电话'}}</p>
        <p><a-icon type="lock"/>金额：{{userInfoData.amount ? userInfoData.amount+'万' : '暂无金额信息'}}</p>
        <p>
          <a-icon type="smile" v-if="userInfoData.status === 'finish'"/>
          <a-icon type="frown" v-else/>状态：
          <template v-if="userInfoData.dataStatus === 'init'">
            <a-tag color="blue">待签约</a-tag>
          </template>
          <template v-else-if="userInfoData.status === 'finish'">
            <a-tag color="green">放款</a-tag>
          </template>
          <template v-else-if="userInfoData.status === 'refused'">
            <a-tag color="red">拒绝</a-tag>
          </template>
          <template v-else-if="userInfoData.status === 'redist'">
            <a-tag color="orange">重新配分</a-tag>
          </template>
          <template v-else>
            {{userInfoData.dataStatus}}
          </template>
        </p>
      </a-layout-content>
      <a-layout-sider class="user-info-side">
        <div :title="userInfoData.describe"><a-icon type="message"/>详情：{{userInfoData.describe}}</div>
      </a-layout-sider>
    </a-layout>
  </a-modal>
</template>
<script>
export default {
  name: 'UserInfo',
  props: {
    userInfoVisiable: {
      require: true,
      default: false
    },
    userInfoData: {
      require: true
    }
  },
  computed: {
    show: {
      get: function () {
        return this.userInfoVisiable
      },
      set: function () {
      }
    }
  },
  methods: {
    handleCancleClick () {
      this.$emit('close')
    }
  }
}
</script>
<style lang="less" scoped>
@import "UserInfo";
</style>
