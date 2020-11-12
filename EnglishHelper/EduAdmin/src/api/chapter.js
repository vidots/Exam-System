import request from '@/utils/request'

export function getChapterList(params) {
  return request({
    url: '/admin/chapter/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function deleteChapter(id) {
  return request({
    url: '/admin/chapter/delete/' + id,
    method: 'post'
  })
}

export function addChapter(data) {
  return request({
    url: '/admin/chapter/add',
    method: 'post',
    data
  })
}

export function updateChapter(data) {
  return request({
    url: '/admin/chapter/edit',
    method: 'post',
    data
  })
}

export function getChapter(id) {
  return request({
    url: '/admin/chapter/' + id,
    method: 'post'
  })
}

