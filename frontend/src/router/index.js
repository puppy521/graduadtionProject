import Vue from 'vue'
import Router from 'vue-router'
import SchoolLogin from "../view/school/Login";
import SchoolHome from "../view/school/Home";
import ChangePassword from "../components/ChangePassword";
import ChangeName from "../components/ChangeName";
import AdminLogin from "../view/AdminLogin";
import AdminHome from "../view/AdminHome";
import AdminSchool from "../components/AdminSchool";
import AdminChangePassword from "../components/AdminChangePassword";
import AdminBaseInformation from "../components/AdminBaseInformation";
import OneDay from "../components/OneDay";

Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/',
      redirect: {name: 'SchoolLogin'}
    }, {
      path: '/schoolLogin',
      name: 'SchoolLogin',
      component: SchoolLogin
    }, {
      path: '/schoolHome',
      component: SchoolHome,
      name: '首页',
      children: [
        {
          path: '/changePassword',
          component: ChangePassword,
          name: '修改密码'
        }, {
          path: '/changeName',
          component: ChangeName,
          name: '修改昵称',
        }, {
          path: '/oneDay',
          component: OneDay,
          name: "一日消费分析"
        }
      ]
    }, {
      path: '/adminLogin',
      name: 'AdminLogin',
      component: AdminLogin
    }, {
      path: '/adminHome',
      name: '管理员首页',
      component: AdminHome,
      children: [{
        path: '/school',
        name: '学校管理员信息管理',
        component: AdminSchool,
      }, {
        path: '/adminChangePassword',
        name: '修改密码',
        component: AdminChangePassword,
      }, {
        path: '/adminBaseInformation',
        name: '基本信息',
        component: AdminBaseInformation
      }]
    },
  ]
});
router.beforeEach((to, from, next) => {
  if (to.path === '/' || to.path === '/schoolLogin' || to.path === '/adminLogin') {
    next();
  } else {
    const tokenStr = localStorage.getItem('token')
    if (!tokenStr) {
      next('/')
    } else {
      next();
    }
  }
})
export default router;

