<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header></the-header>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
            v-model:selectedKeys="selectedKeys2"
            v-model:openKeys="openKeys"
            mode="inline"
            :style="{ height: '100%', borderRight: 0 }"
        >

          <!--商品管理栏-->
          <a-sub-menu key="sub1">
            <template #title>
              <span>
                <user-outlined/>
                商品管理
              </span>
            </template>
            <a-menu-item key="/admin/brand">
              <router-link to="/admin/brand">品牌管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/specification">
              <router-link to="/admin/specification">规格管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/templates">
              <router-link to="/admin/templates">模板管理</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/goods">
              <router-link to="/admin/goods">商品审核</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/category">
              <router-link to="/admin/category">分类管理</router-link>
            </a-menu-item>
          </a-sub-menu>


          <!--广告管理栏-->
          <a-sub-menu key="sub2">
            <template #title>
              <span>
                <laptop-outlined/>
                广告管理
              </span>
            </template>
            <a-menu-item key="/admin/contentType">
              <router-link to="/admin/contentType">广告类别</router-link>
            </a-menu-item>
            <a-menu-item key="/admin/content">
              <router-link to="/admin/content">广告内容</router-link>
            </a-menu-item>
          </a-sub-menu>
        </a-menu>
      </a-layout-sider>
      <a-layout-content :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';

export default defineComponent({
  name: "app",
  components: {
    TheHeader,
    TheFooter

  }
})
</script>
<style>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>
