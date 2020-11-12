import request from '@/utils/request'

export function getPhraseList(params) {
  return request({
    url: '/admin/phrase/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getPhrase(id) {
  return request({
    url: '/admin/phrase/' + id,
    method: 'get'
  })
}

export function getPhraseExist(phrase) {
  return request({
    url: '/admin/phrase/exist/' + phrase,
    method: 'get'
  })
}

export function deletePhrase(id) {
  return request({
    url: '/admin/phrase/delete/' + id,
    method: 'post'
  })
}

export function addPhrase(data) {
  return request({
    url: '/admin/phrase/add',
    method: 'post',
    data
  })
}

export function updatePhrase(data) {
  return request({
    url: '/admin/phrase/edit/' + data.id,
    method: 'post',
    data
  })
}

