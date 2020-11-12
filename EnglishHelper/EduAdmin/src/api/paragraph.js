import request from '@/utils/request'

export function getParagraphList(params) {
  return request({
    url: '/admin/paragraph/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function deleteParagraph(id) {
  return request({
    url: '/admin/paragraph/delete/' + id,
    method: 'post'
  })
}

export function addParagraph(data) {
  return request({
    url: '/admin/paragraph/add',
    method: 'post',
    data
  })
}

export function updateParagraph(data) {
  return request({
    url: '/admin/paragraph/edit',
    method: 'post',
    data
  })
}

