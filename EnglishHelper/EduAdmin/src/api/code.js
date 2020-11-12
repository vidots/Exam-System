import request from '@/utils/request'

export function getCodeList(params) {
  return request({
    url: '/admin/code/list/' + params.page + '/' + params.size,
    method: 'post',
    params
  })
}

export function generateCode() {
  return request({
    url: '/admin/code/generate',
    method: 'post'
  })
}

