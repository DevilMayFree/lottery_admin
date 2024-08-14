import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";

// 列表
export function listGoods(query) {
  return request({
    url: '/lottery/goods/list',
    method: 'get',
    params: query
  })
}

export function addGoods(data) {
  return request({
    url: '/lottery/goods',
    method: 'post',
    data: data
  })
}

export function getGoods(id) {
  return request({
    url: '/lottery/goods/' + parseStrEmpty(id),
    method: 'get'
  })
}

export function getGoodsByName(name) {
  return request({
    url: '/lottery/goods/getGoodsByName',
    data: {
      name: name,
    },
    method: 'post'
  })
}

export function updateGoods(data) {
  return request({
    url: '/lottery/goods',
    method: 'put',
    data: data
  })
}

export function delGoods(id) {
  return request({
    url: '/lottery/goods/' + id,
    method: 'delete'
  })
}


export function queryLog(code) {
  return request({
    url: '/lottery/play/query',
    method: 'post',
    data: {
      code: code
    }
  })
}
