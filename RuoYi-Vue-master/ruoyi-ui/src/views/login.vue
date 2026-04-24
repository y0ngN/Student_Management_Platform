<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-left">
        <div class="left-content">
          <div class="logo-area">
            <svg-icon icon-class="education" class="edu-logo" />
            <span class="system-name">STUDENT MS</span>
          </div>
          <div class="welcome-text">
            <h2>欢迎回来</h2>
            <p>智慧校园学生管理系统，助力教育数字化升级</p>
          </div>
          <div class="illustration"></div>
        </div>
      </div>

      <div class="login-right">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <h3 class="title">账号登录</h3>

          <el-form-item prop="username">
            <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="请输入账号">
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="请输入密码" @keyup.enter.native="handleLogin">
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>

          <el-form-item prop="code" v-if="captchaEnabled">
            <div class="captcha-wrapper">
              <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" @keyup.enter.native="handleLogin">
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
              </el-input>
              <div class="login-code">
                <img :src="codeUrl" @click="getCode" class="login-code-img"/>
              </div>
            </div>
          </el-form-item>

          <div class="form-options">
            <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
            <router-link v-if="register" class="link-type" :to="'/register'">学生注册</router-link>
          </div>

          <el-form-item style="width:100%;">
            <el-button :loading="loading" size="medium" type="primary" class="submit-btn" @click.native.prevent="handleLogin">
              <span v-if="!loading">立即登录</span>
              <span v-else>登录中...</span>
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>

    <div class="el-login-footer">
      <span>{{ footerContent || 'Copyright © 2024 学生管理平台 All Rights Reserved.' }}</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'
import defaultSettings from '@/settings'

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      footerContent: defaultSettings.footerContent,
      codeUrl: "",
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
        password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      captchaEnabled: true,
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) { this.redirect = route.query && route.query.redirect },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username"); Cookies.remove("password"); Cookies.remove('rememberMe')
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) this.getCode()
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  background-image: url("~@/assets/images/login-background.jpg");
  background-size: cover;
  background-position: center;
}

.login-box {
  display: flex;
  width: 900px;
  height: 550px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25);
  overflow: hidden;
}

/* 左侧装饰区 */
.login-left {
  flex: 1.2;
  background: #409EFF;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  position: relative;
  padding: 40px;
  color: #fff;
  display: flex;
  flex-direction: column;

  .logo-area {
    display: flex;
    align-items: center;
    .edu-logo { font-size: 32px; margin-right: 10px; }
    .system-name { font-size: 24px; font-weight: bold; letter-spacing: 1px; }
  }

  .welcome-text {
    margin-top: 60px;
    h2 { font-size: 32px; margin-bottom: 20px; }
    p { font-size: 16px; line-height: 1.6; opacity: 0.9; }
  }

  .illustration {
    flex: 1;
    background: url("https://cdni.iconscout.com/illustration/premium/thumb/student-learning-on-laptop-2111224-1782061.png") no-repeat bottom center;
    background-size: contain;
  }
}

/* 右侧表单区 */
.login-right {
  flex: 1;
  padding: 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;

  .title {
    margin-bottom: 40px;
    font-size: 26px;
    font-weight: 600;
    color: #333;
    text-align: left;
  }
}

.login-form {
  .el-input {
    height: 45px;
    input {
      height: 45px;
      background-color: #f5f7fa !important;
      border: none !important;
      border-radius: 8px;
    }
  }
  .input-icon { height: 45px; width: 16px; margin-left: 5px; color: #909399; }
}

.captcha-wrapper {
  display: flex;
  justify-content: space-between;
  .el-input { width: 60%; }
}

.login-code {
  width: 35%;
  height: 45px;
  img {
    width: 100%;
    height: 100%;
    border-radius: 8px;
    cursor: pointer;
  }
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  .link-type { color: #409EFF; font-size: 14px; text-decoration: none; }
}

.submit-btn {
  height: 45px;
  font-size: 16px;
  border-radius: 8px;
  background: linear-gradient(to right, #4facfe, #00f2fe) !important;
  border: none !important;
  box-shadow: 0 4px 15px rgba(79, 172, 254, 0.4);
  transition: all 0.3s;
  &:hover { opacity: 0.9; transform: translateY(-1px); }
}

.el-login-footer {
  position: fixed;
  bottom: 20px;
  width: 100%;
  text-align: center;
  color: #fff;
  font-size: 13px;
  text-shadow: 0 1px 2px rgba(0,0,0,0.3);
}
</style>
