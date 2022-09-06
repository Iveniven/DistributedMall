import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AdminBrand from '../views/admin/admin-brand.vue'
import AdminCategory from '../views/admin/admin-category.vue'
import AdminGoods from '../views/admin/admin-goods.vue'
import AdminSpecification from '../views/admin/admin-specification.vue'
import AdminTemplates from '../views/admin/admin-templates.vue'
import AdminContent from '../views/content/admin-content.vue'
import AdminContentType from '../views/content/admin-content-type.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    // 品牌管理
    path: '/admin/brand',
    name: 'AdminBrand',
    component: AdminBrand
  },
  {
    // 分类管理
    path: '/admin/category',
    name: 'AdminCategory',
    component: AdminCategory
  },
  {
    // 商品审核
    path: '/admin/goods',
    name: 'AdminGoods',
    component: AdminGoods
  },
  {
    // 规格管理
    path: '/admin/specification',
    name: 'AdminSpecification',
    component: AdminSpecification
  },
  {
    // 模板管理
    path: '/admin/templates',
    name: 'AdminTemplates',
    component: AdminTemplates
  },
  {
    // 广告类别
    path: '/admin/contentType',
    name: 'AdminContentType',
    component: AdminContentType
  },
  {
    // 广告内容
    path: '/admin/content',
    name: 'AdminContent',
    component: AdminContent
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
