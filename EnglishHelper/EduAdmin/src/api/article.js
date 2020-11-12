import request from '@/utils/request'

export function getArticleList(params) {
  return request({
    url: '/admin/article/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getArticle(id) {
  return request({
    url: '/admin/article/' + id,
    method: 'get'
  })
}

export function deleteArticle(id) {
  return request({
    url: '/admin/article/delete/' + id,
    method: 'post'
  })
}

export function addArticle(data) {
  return request({
    url: '/admin/article/add',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/admin/article/edit/' + data.id,
    method: 'post',
    data
  })
}

