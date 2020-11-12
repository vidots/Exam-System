import request from '@/utils/request'

export function getBookList(params) {
  return request({
    url: '/admin/book/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function deleteBook(id) {
  return request({
    url: '/admin/book/delete/' + id,
    method: 'post'
  })
}

export function addBook(data) {
  return request({
    url: '/admin/book/add',
    method: 'post',
    data
  })
}

export function getBook(id) {
  return request({
    url: '/admin/book/' + id,
    method: 'get'
  })
}

export function updateBook(data) {
  return request({
    url: '/admin/book/edit',
    method: 'post',
    data
  })
}

