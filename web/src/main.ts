import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue'
import axios from "axios"
import 'ant-design-vue/dist/antd.css'

createApp(App).use(store).use(Antd).use(router).mount('#app')
axios.defaults.baseURL = process.env.VUE_APP_SERVER;
