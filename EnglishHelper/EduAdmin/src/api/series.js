import request from '@/utils/request'

export function getSeriesList() {
  return request({
    url: '/admin/series/list',
    method: 'get'
  })
}

export function deleteSeries(id) {
  return request({
    url: '/admin/series/delete/' + id,
    method: 'post'
  })
}

export function addSeries(data) {
  return request({
    url: '/admin/series/add',
    method: 'post',
    data
  })
}

export function updateSeries(data) {
  return request({
    url: '/admin/series/edit',
    method: 'post',
    data
  })
}

