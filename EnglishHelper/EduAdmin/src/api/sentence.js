import request from '@/utils/request'

export function getSentenceList(params) {
  return request({
    url: '/admin/sentence/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getSentence(id) {
  return request({
    url: '/admin/sentence/' + id,
    method: 'get'
  })
}

export function deleteSentence(id) {
  return request({
    url: '/admin/sentence/delete/' + id,
    method: 'post'
  })
}

export function addSentence(data) {
  return request({
    url: '/admin/sentence/add',
    method: 'post',
    data
  })
}

export function updateSentence(data) {
  return request({
    url: '/admin/sentence/edit/' + data.id,
    method: 'post',
    data
  })
}

