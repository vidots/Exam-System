import request from '@/utils/request'

export function getNewsList(params) {
  return request({
    url: '/admin/news/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getNews(id) {
  return request({
    url: '/admin/news/' + id,
    method: 'get'
  })
}

export function deleteNews(id) {
  return request({
    url: '/admin/news/delete/' + id,
    method: 'post'
  })
}

export function addNews(data) {
  return request({
    url: '/admin/news/add',
    method: 'post',
    data
  })
}

export function updateNews(data) {
  return request({
    url: '/admin/news/edit/' + data.id,
    method: 'post',
    data
  })
}

