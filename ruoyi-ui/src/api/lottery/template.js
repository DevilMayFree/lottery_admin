import request from "@/utils/request";
import {parseStrEmpty} from "@/utils/ruoyi";

// 列表
export function listTemplate(query) {
    return request({
        url: '/lottery/template/list',
        method: 'get',
        params: query
    })
}

export function addTemplate(data) {
    return request({
        url: '/lottery/template',
        method: 'post',
        data: data
    })
}

export function getTemplate(id) {
    return request({
        url: '/lottery/template/' + parseStrEmpty(id),
        method: 'get'
    })
}

export function getTemplatesByName(name) {
  return request({
    url: '/lottery/template/getTemplatesByName',
    data: {
      name:name,
    },
    method: 'post'
  })
}

export function updateTemplate(data) {
    return request({
        url: '/lottery/template',
        method: 'put',
        data: data
    })
}

export function delTemplate(id) {
    return request({
        url: '/lottery/template/' + id,
        method: 'delete'
    })
}

