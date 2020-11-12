import request from '@/utils/request'

export function getDyContentList() {
  return request({
    url: '/admin/dycontent/list',
    method: 'get'
  })
}

export function getDyContent(id) {
  return request({
    url: '/admin/dycontent/' + id,
    method: 'get'
  })
}

export function deleteDyContent(id) {
  return request({
    url: '/admin/dycontent/delete/' + id,
    method: 'post'
  })
}

export function addDyContent(data) {
  return request({
    url: '/admin/dycontent/add',
    method: 'post',
    data
  })
}

export function updateDyContent(data) {
  return request({
    url: '/admin/dycontent/edit',
    method: 'post',
    data
  })
}

