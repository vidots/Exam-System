import request from '@/utils/request'

export function getUnitList(params) {
  return request({
    url: '/admin/unit/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function deleteUnit(id) {
  return request({
    url: '/admin/unit/delete/' + id,
    method: 'post'
  })
}

export function addUnit(data) {
  return request({
    url: '/admin/unit/add',
    method: 'post',
    data
  })
}

export function updateUnit(data) {
  return request({
    url: '/admin/unit/edit',
    method: 'post',
    data
  })
}

export function getUnit(id) {
  return request({
    url: '/admin/unit/' + id,
    method: 'post'
  })
}

