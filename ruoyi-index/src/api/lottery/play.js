import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";


// 获取奖品数据
export function codeDetail(code) {
  return request({
    url: '/lottery/play/codeDetail',
    data: {
      code: code
    },
    method: 'post'
  })
}

// 抽奖
export function lotteryApi(code) {
  return request({
    url: '/lottery/play/lottery',
    data: {
      code: code
    },
    method: 'post'
  })
}
