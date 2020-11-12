import request from '@/utils/request'

export function getWordList(params) {
  return request({
    url: '/admin/word/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getWord(id) {
  return request({
    url: '/admin/word/' + id,
    method: 'get'
  })
}

export function getWordExist(word) {
  return request({
    url: '/admin/word/exist/' + word,
    method: 'get'
  })
}

export function deleteWord(id) {
  return request({
    url: '/admin/word/delete/' + id,
    method: 'post'
  })
}

export function addWord(data) {
  return request({
    url: '/admin/word/add',
    method: 'post',
    data
  })
}

export function updateWord(data) {
  return request({
    url: '/admin/word/edit/' + data.id,
    method: 'post',
    data
  })
}

