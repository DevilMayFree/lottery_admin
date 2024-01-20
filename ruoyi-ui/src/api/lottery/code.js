import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";

// 列表
export function listCode(query) {
  return request({
    url: '/lottery/code/list',
    method: 'get',
    params: query
  })
}

export function createCode() {
  return request({
    url: '/lottery/code/getCode',
    data: {},
    method: 'post'
  })
}

export function getShare() {
  return request({
    url: '/lottery/code/getShare',
    data: {},
    method: 'post'
  })
}

// 兑奖
export function actionDone(id) {
  return request({
    url: '/lottery/code/action',
    data: {
      id: id
    },
    method: 'post'
  })
}

export function addCode(data) {
  return request({
    url: '/lottery/code',
    method: 'post',
    data: data
  })
}

export function getCode(id) {
  return request({
    url: '/lottery/code/' + parseStrEmpty(id),
    method: 'get'
  })
}

export function updateCode(data) {
  return request({
    url: '/lottery/code',
    method: 'put',
    data: data
  })
}

export function delCode(id) {
  return request({
    url: '/lottery/code/' + id,
    method: 'delete'
  })
}

