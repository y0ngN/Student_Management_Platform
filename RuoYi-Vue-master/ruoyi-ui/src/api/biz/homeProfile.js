import request from '@/utils/request'

export function getHomeProfile() {
  return request({
    url: '/biz/home/profile',
    method: 'get'
  })
}

export function updateHomeProfile(data) {
  return request({
    url: '/biz/home/profile',
    method: 'put',
    data: data
  })
}
