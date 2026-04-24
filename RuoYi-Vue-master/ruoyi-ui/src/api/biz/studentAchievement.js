import request from '@/utils/request'

export function listStudentAchievement(query) {
  return request({
    url: '/biz/studentAchievement/list',
    method: 'get',
    params: query
  })
}

export function getStudentAchievement(achievementId) {
  return request({
    url: '/biz/studentAchievement/' + achievementId,
    method: 'get'
  })
}

export function addStudentAchievement(data) {
  return request({
    url: '/biz/studentAchievement',
    method: 'post',
    data: data
  })
}

export function updateStudentAchievement(data) {
  return request({
    url: '/biz/studentAchievement',
    method: 'put',
    data: data
  })
}

export function delStudentAchievement(achievementId) {
  return request({
    url: '/biz/studentAchievement/' + achievementId,
    method: 'delete'
  })
}

export function auditStudentAchievement(data) {
  return request({
    url: '/biz/studentAchievement/audit',
    method: 'put',
    data: data
  })
}
