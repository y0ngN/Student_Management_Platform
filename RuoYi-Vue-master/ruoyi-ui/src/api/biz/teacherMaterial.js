import request from '@/utils/request'

export function listTeacherMaterial(query) {
  return request({
    url: '/biz/teacherMaterial/list',
    method: 'get',
    params: query
  })
}

export function getTeacherMaterial(materialId) {
  return request({
    url: '/biz/teacherMaterial/' + materialId,
    method: 'get'
  })
}

export function addTeacherMaterial(data) {
  return request({
    url: '/biz/teacherMaterial',
    method: 'post',
    data: data
  })
}

export function updateTeacherMaterial(data) {
  return request({
    url: '/biz/teacherMaterial',
    method: 'put',
    data: data
  })
}

export function delTeacherMaterial(materialId) {
  return request({
    url: '/biz/teacherMaterial/' + materialId,
    method: 'delete'
  })
}

export function auditTeacherMaterial(data) {
  return request({
    url: '/biz/teacherMaterial/audit',
    method: 'put',
    data: data
  })
}
