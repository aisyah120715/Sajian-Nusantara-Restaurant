<script>
import CuisineWaveRestaurant from './components/LandingPage.vue'
import Login from './components/Login.vue'
import Signup from './components/Signup.vue'
import ForgotPassword from './components/ForgotPassword.vue'
import ResetPassword from './components/ResetPassword.vue'
import TableReservation from './components/TableReservation.vue'
import MenuOrder from './components/MenuOrder.vue'
import AdminDashboard from './components/AdminDashboard.vue'
import Profile from './components/Profile.vue'
import OrderHistory from './components/OrderHistory.vue'

export default {
  name: 'App',
  components: {
    CuisineWaveRestaurant,
    Login,
    Signup,
    ForgotPassword,
    ResetPassword,
    TableReservation,
    MenuOrder,
    AdminDashboard,
    Profile,
    OrderHistory
  },
  data() {
    return {
      currentPage: 'home' // 'home', 'login', 'signup', 'forgot-password', 'reset-password', 'reservation', 'menu', 'admin-dashboard', 'profile', 'order-history'
    }
  },
  mounted() {
    // Check if there's a reset token in the URL
    const urlParams = new URLSearchParams(window.location.search)
    const token = urlParams.get('token')
    
    if (token) {
      // If token exists, show reset password page
      this.currentPage = 'reset-password'
    }
  },
  methods: {
    showLogin() {
      this.currentPage = 'login'
    },
    showSignup() {
      this.currentPage = 'signup'
    },
    showHome() {
      this.currentPage = 'home'
    },
    showReservation() {
      this.currentPage = 'reservation'
    },
    showMenu() {
      this.currentPage = 'menu'
    },
    switchToSignup() {
      this.currentPage = 'signup'
    },
    switchToLogin() {
      this.currentPage = 'login'
    },
    showForgotPassword() {
      this.currentPage = 'forgot-password'
    },
    showResetPassword() {
      this.currentPage = 'reset-password'
    },
    showAdminDashboard() {
      this.currentPage = 'admin-dashboard'
    },
    showProfile() {
      this.currentPage = 'profile'
    },
    showOrderHistory() {
      this.currentPage = 'order-history'
    }
  }
}
</script>

<template>
  <CuisineWaveRestaurant 
    v-if="currentPage === 'home'"
    @show-login="showLogin"
    @show-signup="showSignup"
    @show-reservation="showReservation"
    @show-menu="showMenu"
    @show-profile="showProfile"
    @show-order-history="showOrderHistory"
  />
  <Login 
    v-else-if="currentPage === 'login'"
    @switch-to-signup="switchToSignup"
    @show-home="showHome"
    @show-forgot-password="showForgotPassword"
    @show-admin-dashboard="showAdminDashboard"
  />
  <Signup 
    v-else-if="currentPage === 'signup'"
    @switch-to-login="switchToLogin"
    @show-home="showHome"
  />
  <ForgotPassword 
    v-else-if="currentPage === 'forgot-password'"
    @show-login="switchToLogin"
  />
  <ResetPassword 
    v-else-if="currentPage === 'reset-password'"
    @show-login="switchToLogin"
  />
  <TableReservation 
    v-else-if="currentPage === 'reservation'"
    @show-home="showHome"
    @show-menu="showMenu"
    @show-reservation="showReservation"
    @show-profile="showProfile"
    @show-order-history="showOrderHistory"
    @show-login="switchToLogin"
    @show-signup="showSignup"
  />
  <MenuOrder 
    v-else-if="currentPage === 'menu'"
    @show-home="showHome"
    @show-menu="showMenu"
    @show-reservation="showReservation"
    @show-profile="showProfile"
    @show-order-history="showOrderHistory"
    @show-login="switchToLogin"
    @show-signup="showSignup"
  />
  <AdminDashboard 
    v-else-if="currentPage === 'admin-dashboard'"
    @show-login="switchToLogin"
  />
  <Profile 
    v-else-if="currentPage === 'profile'"
    @show-home="showHome"
    @show-menu="showMenu"
    @show-reservation="showReservation"
    @show-order-history="showOrderHistory"
    @show-login="switchToLogin"
    @show-signup="showSignup"
  />
  <OrderHistory 
    v-else-if="currentPage === 'order-history'"
    @show-home="showHome"
    @show-menu="showMenu"
    @show-reservation="showReservation"
    @show-profile="showProfile"
    @show-login="switchToLogin"
    @show-signup="showSignup"
  />
</template>
