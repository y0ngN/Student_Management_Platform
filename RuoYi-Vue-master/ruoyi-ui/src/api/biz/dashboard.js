import request from '@/utils/request'

export function getOverview(query) {
  return request({
    url: '/biz/dashboard/overview',
    method: 'get',
    params: query
  })
}

export function getRoleStat(query) {
  return request({
    url: '/biz/dashboard/roleStat',
    method: 'get',
    params: query
  })
}

export function getDepartmentStat(query) {
  return request({
    url: '/biz/dashboard/departmentStat',
    method: 'get',
    params: query
  })
}

export function getMonthlyTrend(query) {
  return request({
    url: '/biz/dashboard/monthlyTrend',
    method: 'get',
    params: query
  })
}

export function getRawSubmissions(query) {
  return request({
    url: '/biz/dashboard/rawSubmissions',
    method: 'get',
    params: query
  })
}
