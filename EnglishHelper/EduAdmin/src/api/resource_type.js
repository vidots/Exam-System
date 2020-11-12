import request from '@/utils/request'

export function getRTList() {
  return request({
    url: '/admin/resource-type/list',
    method: 'get'
  })
}

export function deleteRT(id) {
  return request({
    url: '/admin/resource-type/delete/' + id,
    method: 'post'
  })
}

export function addRT(data) {
  return request({
    url: '/admin/resource-type/add',
    method: 'post',
    data
  })
}

export function updateRT(data) {
  return request({
    url: '/admin/resource-type/edit',
    method: 'post',
    data
  })
}

