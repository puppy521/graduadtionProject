<template>
  <div style="margin: 0">
    <div class="background">
      <img :src="imgSrc" width="100%" height="100%" alt=""/>
    </div>
    <div class="front">
      <el-form :rules="rules" class="login-container" label-position="left"
               label-width="0px" v-loading="loading" :model="ruleForm" ref="ruleForm">
        <h3 class="login_title">高校学生校园消费数据分析系统</h3>
        <h3 class="login_title">登录</h3>
        <el-form-item prop="id">
          <el-input type="text" v-model="ruleForm.id" auto-complete="off" placeholder="账号" autocomplete="off"
                    prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off" placeholder="密码" autocomplete="off"
                    show-password></el-input>
        </el-form-item>
        <!--    <el-checkbox class="login_remember" v-model="checked" label-position="left">记住密码</el-checkbox>-->
        <el-form-item style="width: 100%">
          <el-button type="primary" @click="submitForm('ruleForm')" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
import {mapMutations} from 'vuex'

export default {
  name: "SchoolLogin",
  data() {
    let validateId = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'));
      } else if (value.length !== 10) {
        callback(new Error('账号长度不为10'));
      } else {
        callback();
      }
    };
    let validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    return {
      imgSrc:require('../../assets/bg.jpeg'),
      ruleForm: {
        id: '',
        password: ''
      },
      loading: false,
      rules: {
        id: [
          {validator: validateId, trigger: 'blur'}
        ],
        password: [
          {validator: validatePassword, trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    ...mapMutations(['setToken', 'setSchoolUser']),
    submitForm(formName) {
      let _this = this
      _this.loading = true;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          _this.$axios.post('http://localhost:9090/schoolUser/login', {
            id: _this.ruleForm.id,
            password: _this.ruleForm.password
          }).then(function (response) {
            _this.loading = false;
            let data = response.data
            if (data.code === 2) {
              _this.token = data.token;
              _this.setToken({token: data.token}, {token: data.token})
              _this.setSchoolUser({id: data.data.id, name: data.data.name}, {id: data.data.id, name: data.data.name})
              _this.$router.push('/schoolHome')
            } else if (data.code === 0) {
              alert(data.message)
            } else if (data.code === 1) {
              alert(data.message)
            } else {
              alert(data.message)
            }
          }).catch(function (error) {
            _this.loading = false;
            console.log(error);
          });
        } else {
          _this.loading = false;
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>
<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 10px auto;
  text-align: center;
  color: #505458;
}
.login_title1 {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.background{
  width:100%;
  height:100%;  /**宽高100%是为了图片铺满屏幕 */
  z-index:-1;
  margin:0;
  position: absolute;
}

.front{
  z-index:1;
  margin-left: 35%;
  position: absolute;
}

</style>

