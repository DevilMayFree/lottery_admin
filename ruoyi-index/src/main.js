import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'

// import '@/assets/styles/index.scss' // global css
import '@/assets/styles/swiper.min.css' // global css
import '@/assets/styles/common_mobile-1.0.0.css' // global css
import '@/assets/styles/common.css' // global css
import '@/assets/styles/animate.min.css' // global css
import '@/assets/styles/index-1.0.0.css' // global css

import App from './App'
// import App2 from './App2'
import { download } from '@/utils/request'

import './assets/icons' // icon
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/ruoyi";
// 头部标签组件
import VueMeta from 'vue-meta'

import VueClipboard from 'vue-clipboard2'

// 全局方法挂载
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

Vue.use(VueClipboard)
Vue.use(VueMeta)

// 完整加载
import VueLuckyCanvas from '@lucky-canvas/vue'
Vue.use(VueLuckyCanvas)

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  render: h => h(App)
})
