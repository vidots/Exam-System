import request from '@/utils/request'

export function getResourceList(params) {
  return request({
    url: '/admin/resource/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getResource(id) {
  return request({
    url: '/admin/resource/' + id,
    method: 'get'
  })
}

export function deleteResource(id) {
  return request({
    url: '/admin/resource/delete/' + id,
    method: 'post'
  })
}

export function addResource(data) {
  return request({
    url: '/admin/resource/add',
    method: 'post',
    data
  })
}

export function updateResource(data) {
  return request({
    url: '/admin/resource/edit/' + data.id,
    method: 'post',
    data
  })
}

