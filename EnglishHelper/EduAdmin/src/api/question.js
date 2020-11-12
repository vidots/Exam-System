import request from '@/utils/request'

export function getQuestionList(params) {
  return request({
    url: '/admin/question/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function getQuestion(id) {
  return request({
    url: '/admin/question/' + id,
    method: 'get'
  })
}

export function deleteQuestion(id) {
  return request({
    url: '/admin/question/delete/' + id,
    method: 'post'
  })
}

export function addQuestion(data) {
  return request({
    url: '/admin/question/add',
    method: 'post',
    data
  })
}

export function updateQuestion(data) {
  return request({
    url: '/admin/question/edit/' + data.id,
    method: 'post',
    data
  })
}

