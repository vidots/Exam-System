import request from '@/utils/request'

export function getATList() {
  return request({
    url: '/admin/article-type/list',
    method: 'get'
  })
}

export function deleteAT(id) {
  return request({
    url: '/admin/article-type/delete/' + id,
    method: 'post'
  })
}

export function addAT(data) {
  return request({
    url: '/admin/article-type/add',
    method: 'post',
    data
  })
}

export function updateAT(data) {
  return request({
    url: '/admin/article-type/edit',
    method: 'post',
    data
  })
}

