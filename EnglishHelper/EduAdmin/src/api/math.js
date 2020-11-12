import request from '@/utils/request'

export function getMathList(params) {
  return request({
    url: '/admin/math/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function deleteMath(id) {
  return request({
    url: '/admin/math/delete/' + id,
    method: 'post'
  })
}

export function addMath(data) {
  return request({
    url: '/admin/math/add',
    method: 'post',
    data
  })
}

export function getMath(id) {
  return request({
    url: '/admin/math/' + id,
    method: 'get'
  })
}

export function updateMath(data) {
  return request({
    url: '/admin/math/edit',
    method: 'post',
    data
  })
}

