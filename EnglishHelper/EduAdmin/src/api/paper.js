import request from '@/utils/request'

export function getSeriesList() {
  return request({
    url: '/admin/series/list',
    method: 'get'
  })
}

export function getPaperList(params) {
  return request({
    url: '/admin/paper/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getPaper(id) {
  return request({
    url: '/admin/paper/' + id,
    method: 'get'
  })
}

export function deletePaper(id) {
  return request({
    url: '/admin/paper/delete/' + id,
    method: 'post'
  })
}

export function addPaper(data) {
  return request({
    url: '/admin/paper/add',
    method: 'post',
    data
  })
}

export function updatePaper(data) {
  return request({
    url: '/admin/paper/edit/' + data.id,
    method: 'post',
    data
  })
}

