import request from '@/utils/request'

export function getConceptList(params) {
  return request({
    url: '/admin/concept/list/' + params.page + '/' + params.size,
    method: 'get',
    params
  })
}

export function deleteConcept(id) {
  return request({
    url: '/admin/concept/delete/' + id,
    method: 'post'
  })
}

export function addConcept(data) {
  return request({
    url: '/admin/concept/add',
    method: 'post',
    data
  })
}

export function updateConcept(data) {
  return request({
    url: '/admin/concept/edit',
    method: 'post',
    data
  })
}

