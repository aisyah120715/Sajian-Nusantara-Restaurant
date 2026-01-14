<template>
  <div class="signup-page">
    <div class="signup-container">
      <!-- Left Side - Branding -->
      <div class="signup-left">
        <div class="brand-section">
          <div class="logo-wrapper">
            <img src="./logo_restaurant.png" alt="Sajian Nusantara Logo" class="restaurant-logo" />
          </div>
          <div class="brand-tagline">
            <h2 class="brand-name">Sajian Nusantara</h2>
            <p class="brand-description">Authentic Malaysian Cuisine</p>
          </div>
        </div>
        <div class="subtle-pattern">
          <div class="pattern-line line-1"></div>
          <div class="pattern-line line-2"></div>
          <div class="pattern-line line-3"></div>
        </div>
      </div>

      <!-- Right Side - Signup Form -->
      <div class="signup-right">
        <div class="form-header">
          <nav class="signup-nav">
            <a href="#" class="nav-link" @click.prevent="goToLogin">SIGN IN</a>
            <a href="#" class="nav-link active" @click.prevent="goToSignup">CREATE ACCOUNT</a>
          </nav>
        </div>

        <div class="signup-form-container">
          <div class="welcome-section">
            <h1 class="signup-title">Create Account</h1>
            <p class="signup-subtitle">Join us and start your culinary journey</p>
          </div>

          <form @submit.prevent="handleSignup" class="signup-form">
            <div class="form-group">
              <label class="input-label">Full Name</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input type="text" v-model="fullName" placeholder="Enter your full name" class="form-input" required />
              </div>
            </div>

            <div class="form-group">
              <label class="input-label">Username</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input type="text" v-model="username" placeholder="Enter your username" class="form-input" required />
              </div>
            </div>

            <div class="form-group">
              <label class="input-label">Email Address</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M22 6l-10 7L2 6" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input type="email" v-model="email" placeholder="Enter your email" class="form-input" required />
              </div>
            </div>

            <div class="form-group">
              <label class="input-label">Phone Number</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input type="tel" v-model="phoneNumber" placeholder="Enter your phone number" class="form-input" required />
              </div>
            </div>

            <div class="form-group">
              <label class="input-label">Password</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <rect x="3" y="10" width="18" height="11" rx="2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M7 10V6a5 5 0 1 1 10 0v4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input :type="showPassword ? 'text' : 'password'" v-model="password" @input="validatePassword" placeholder="Enter your password" class="form-input" required />
                <button type="button" @click="togglePassword" class="password-toggle">
                  <svg v-if="showPassword" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" stroke="currentColor" stroke-width="1.5"/>
                    <path d="M12 19c-4 0-7.3-2.8-8-5 0 0 2-5 8-5s8 5 8 5c-.7 2.2-4 5-8 5z" stroke="currentColor" stroke-width="1.5"/>
                  </svg>
                  <svg v-else width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                    <line x1="1" y1="1" x2="23" y2="23" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                </button>
              </div>
              
              <!-- Password Security Level Indicator -->
              <div v-if="password" class="password-security">
                <div class="security-level-bar">
                  <div class="security-level-fill" :class="getSecurityLevelClass()" :style="{ width: (passwordStrength * 20) + '%' }"></div>
                </div>
                <div class="security-level-text">
                  Security Level: <span :class="getSecurityLevelClass()">{{ getSecurityLevelText() }}</span>
                  <button type="button" @click="showPasswordRules = !showPasswordRules" class="info-icon-btn" v-if="passwordStrength < 5">
                    <svg width="14" height="14" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                      <line x1="12" y1="16" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      <line x1="12" y1="8" x2="12.01" y2="8" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                  </button>
                </div>
                
                <!-- Password Rules (shown when info icon is clicked) -->
                <div v-if="showPasswordRules && passwordStrength < 5" class="password-rules">
                  <div class="password-rule" :class="{ 'rule-valid': passwordRules.minLength, 'rule-invalid': !passwordRules.minLength }">
                    <svg v-if="passwordRules.minLength" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <svg v-else width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                      <line x1="12" y1="8" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      <line x1="12" y1="16" x2="12.01" y2="16" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <span>At least 8 characters</span>
                  </div>
                  <div class="password-rule" :class="{ 'rule-valid': passwordRules.hasUppercase, 'rule-invalid': !passwordRules.hasUppercase }">
                    <svg v-if="passwordRules.hasUppercase" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <svg v-else width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                      <line x1="12" y1="8" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      <line x1="12" y1="16" x2="12.01" y2="16" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <span>At least 1 uppercase letter (A-Z)</span>
                  </div>
                  <div class="password-rule" :class="{ 'rule-valid': passwordRules.hasLowercase, 'rule-invalid': !passwordRules.hasLowercase }">
                    <svg v-if="passwordRules.hasLowercase" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <svg v-else width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                      <line x1="12" y1="8" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      <line x1="12" y1="16" x2="12.01" y2="16" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <span>At least 1 lowercase letter (a-z)</span>
                  </div>
                  <div class="password-rule" :class="{ 'rule-valid': passwordRules.hasNumber, 'rule-invalid': !passwordRules.hasNumber }">
                    <svg v-if="passwordRules.hasNumber" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <svg v-else width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                      <line x1="12" y1="8" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      <line x1="12" y1="16" x2="12.01" y2="16" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <span>At least 1 number (0-9)</span>
                  </div>
                  <div class="password-rule" :class="{ 'rule-valid': passwordRules.hasSpecialChar, 'rule-invalid': !passwordRules.hasSpecialChar }">
                    <svg v-if="passwordRules.hasSpecialChar" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <svg v-else width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                      <line x1="12" y1="8" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      <line x1="12" y1="16" x2="12.01" y2="16" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <span>At least 1 special character (!@#$%^&*)</span>
                  </div>
                  <div class="password-rule" :class="{ 'rule-valid': passwordRules.noSpaces, 'rule-invalid': !passwordRules.noSpaces }">
                    <svg v-if="passwordRules.noSpaces" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <svg v-else width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                      <line x1="12" y1="8" x2="12" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      <line x1="12" y1="16" x2="12.01" y2="16" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <span>No spaces</span>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group">
              <label class="input-label">Confirm Password</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <rect x="3" y="10" width="18" height="11" rx="2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M7 10V6a5 5 0 1 1 10 0v4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input :type="showConfirmPassword ? 'text' : 'password'" v-model="confirmPassword" placeholder="Confirm your password" class="form-input" required />
                <button type="button" @click="toggleConfirmPassword" class="password-toggle">
                  <svg v-if="showConfirmPassword" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" stroke="currentColor" stroke-width="1.5"/>
                    <path d="M12 19c-4 0-7.3-2.8-8-5 0 0 2-5 8-5s8 5 8 5c-.7 2.2-4 5-8 5z" stroke="currentColor" stroke-width="1.5"/>
                  </svg>
                  <svg v-else width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                    <line x1="1" y1="1" x2="23" y2="23" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                </button>
              </div>
            </div>

            <div class="form-options">
              <label class="checkbox-wrapper">
                <input type="checkbox" v-model="agreeTerms" required />
                <span class="checkbox-label">I agree to the Terms & Conditions</span>
              </label>
            </div>

            <div v-if="errorMessage" class="error-message">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 8v4m0 4h.01M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              {{ errorMessage }}
            </div>

            <div v-if="successMessage" class="success-message">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              {{ successMessage }}
            </div>

            <div class="form-actions">
              <button type="submit" class="btn-signup" :disabled="isLoading">
                <span v-if="isLoading" class="spinner"></span>
                {{ isLoading ? 'Creating account...' : 'Sign Up' }}
              </button>
              <div class="login-section">
                <p>Already have an account? <a href="#" @click.prevent="goToLogin" class="login-link">Sign in</a></p>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Signup',
  data() {
    return {
      fullName: '',
      username: '',
      email: '',
      phoneNumber: '',
      password: '',
      confirmPassword: '',
      showPassword: false,
      showConfirmPassword: false,
      agreeTerms: false,
      errorMessage: '',
      successMessage: '',
      isLoading: false,
      passwordRules: {
        minLength: false,
        hasUppercase: false,
        hasLowercase: false,
        hasNumber: false,
        hasSpecialChar: false,
        noSpaces: true
      },
      passwordStrength: 0,
      showPasswordRules: false
    }
  },
  methods: {
    validatePassword() {
      const pwd = this.password
      
      // Check each rule
      this.passwordRules.minLength = pwd.length >= 8
      this.passwordRules.hasUppercase = /[A-Z]/.test(pwd)
      this.passwordRules.hasLowercase = /[a-z]/.test(pwd)
      this.passwordRules.hasNumber = /[0-9]/.test(pwd)
      this.passwordRules.hasSpecialChar = /[!@#$%^&*]/.test(pwd)
      this.passwordRules.noSpaces = !/\s/.test(pwd)
      
      // Calculate password strength (0-5)
      this.passwordStrength = Object.values(this.passwordRules).filter(rule => rule === true).length
      
      // Auto-hide rules when all are met
      if (this.passwordStrength === 5) {
        this.showPasswordRules = false
      }
    },
    getSecurityLevelClass() {
      if (this.passwordStrength <= 2) return 'security-weak'
      if (this.passwordStrength <= 4) return 'security-medium'
      return 'security-strong'
    },
    getSecurityLevelText() {
      if (this.passwordStrength <= 2) return 'Weak'
      if (this.passwordStrength <= 4) return 'Medium'
      return 'Strong'
    },
    togglePassword() {
      this.showPassword = !this.showPassword
    },
    toggleConfirmPassword() {
      this.showConfirmPassword = !this.showConfirmPassword
    },
    async handleSignup() {
      this.errorMessage = ''
      this.successMessage = ''
      this.isLoading = true

      if (!this.agreeTerms) {
        this.errorMessage = 'Please agree to the Terms & Conditions'
        this.isLoading = false
        return
      }

      // Validate password rules
      if (!this.passwordRules.minLength || 
          !this.passwordRules.hasUppercase || 
          !this.passwordRules.hasLowercase || 
          !this.passwordRules.hasNumber || 
          !this.passwordRules.hasSpecialChar || 
          !this.passwordRules.noSpaces) {
        this.errorMessage = 'Password does not meet all requirements. Please check the rules below.'
        this.showPasswordRules = true
        this.isLoading = false
        return
      }

      if (this.password !== this.confirmPassword) {
        this.errorMessage = 'Passwords do not match'
        this.isLoading = false
        return
      }

      try {
        const response = await fetch('http://localhost:8080/api/auth/signup', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: this.username,
            email: this.email,
            password: this.password,
            phoneNumber: this.phoneNumber
          })
        })

        const data = await response.json()
        
        if (!response.ok) {
          throw new Error(data.error || 'Signup failed')
        }

        const user = data
        console.log('Signup successful:', user)
        this.successMessage = 'Account created successfully! Redirecting to login...'
        
        // Clear form
        this.fullName = ''
        this.username = ''
        this.email = ''
        this.phoneNumber = ''
        this.password = ''
        this.confirmPassword = ''
        
        // Redirect to login after 2 seconds
        setTimeout(() => {
          this.$emit('switch-to-login')
        }, 2000)
      } catch (error) {
        this.errorMessage = error.message || 'Signup failed. Please try again.'
        console.error('Signup error:', error)
      } finally {
        this.isLoading = false
      }
    },
    goToLogin() {
      this.$emit('switch-to-login')
    },
    goToSignup() {
      // Already on signup page
    },
    goToHome() {
      this.$emit('show-home')
    }
  }
}
</script>

<style scoped>
.signup-page {
  min-height: 100vh;
  width: 100%;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 20% 80%, rgba(139, 92, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 159, 28, 0.03) 0%, transparent 50%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 16px;
  position: relative;
  overflow: hidden;
}

.signup-page::before {
  content: '';
  position: absolute;
  inset: 0;
  background: 
    linear-gradient(90deg, transparent 0%, rgba(255, 255, 255, 0.01) 50%, transparent 100%);
  animation: shimmer 3s infinite;
}

@keyframes shimmer {
  0% { transform: translateX(-100%); }
  100% { transform: translateX(100%); }
}

.signup-container {
  display: flex;
  width: 100%;
  max-width: 1000px;
  max-height: 95vh;
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  border-radius: 16px;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
}

/* Left Side */
.signup-left {
  flex: 1;
  background: 
    linear-gradient(135deg, rgba(139, 92, 246, 0.05) 0%, rgba(255, 159, 28, 0.05) 100%),
    radial-gradient(circle at 30% 70%, rgba(139, 92, 246, 0.03) 0%, transparent 50%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  padding: 20px 18px;
  border-right: 1px solid rgba(255, 255, 255, 0.05);
  overflow: hidden;
}

.brand-section {
  text-align: center;
  position: relative;
  z-index: 2;
}

.logo-wrapper {
  margin-bottom: 12px;
}

.restaurant-logo {
  max-width: 200px;
  width: 100%;
  height: auto;
  filter: brightness(1.1);
}

.brand-name {
  font-size: 22px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 3px;
  letter-spacing: 0.5px;
  font-family: 'Georgia', serif;
}

.brand-description {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  letter-spacing: 1.5px;
  text-transform: uppercase;
  font-weight: 400;
}

.subtle-pattern {
  position: absolute;
  inset: 0;
  overflow: hidden;
}

.pattern-line {
  position: absolute;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.02), transparent);
  height: 1px;
}

.line-1 {
  top: 20%;
  width: 100%;
  animation: lineFlow 20s linear infinite;
}

.line-2 {
  top: 50%;
  width: 100%;
  animation: lineFlow 25s linear infinite reverse;
}

.line-3 {
  top: 80%;
  width: 100%;
  animation: lineFlow 30s linear infinite;
}

@keyframes lineFlow {
  0% { transform: translateX(-100%); }
  100% { transform: translateX(100%); }
}

/* Right Side */
.signup-right {
  flex: 1.2;
  padding: 20px 24px;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 95vh;
}

.form-header {
  margin-bottom: 8px;
}

.signup-nav {
  display: flex;
  gap: 40px;
  justify-content: flex-end;
}

.nav-link {
  color: rgba(255, 255, 255, 0.5);
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  letter-spacing: 0.5px;
  padding-bottom: 8px;
  position: relative;
  transition: color 0.3s ease;
}

.nav-link:hover {
  color: rgba(255, 255, 255, 0.8);
}

.nav-link.active {
  color: #ffffff;
}

.nav-link.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, #8B5CF6, #FF9F1C);
}

.welcome-section {
  margin-bottom: 10px;
}

.signup-title {
  font-size: 22px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 2px;
  letter-spacing: -0.5px;
}

.signup-subtitle {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.6);
  font-weight: 400;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-group {
  width: 100%;
}

.input-label {
  display: block;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 4px;
  font-weight: 500;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  padding: 8px 12px;
  transition: all 0.3s ease;
}

.input-wrapper:focus-within {
  border-color: rgba(139, 92, 246, 0.5);
  background: rgba(255, 255, 255, 0.06);
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.input-icon {
  color: rgba(255, 255, 255, 0.4);
  margin-right: 10px;
  flex-shrink: 0;
  width: 18px;
  height: 18px;
}

.form-input {
  flex: 1;
  background: transparent;
  border: none;
  outline: none;
  color: #ffffff;
  font-size: 13px;
  font-weight: 400;
}

.form-input::placeholder {
  color: rgba(255, 255, 255, 0.3);
}

.password-toggle {
  background: transparent;
  border: none;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.4);
  padding: 2px;
  display: flex;
  align-items: center;
  transition: color 0.3s ease;
}

.password-toggle svg {
  width: 18px;
  height: 18px;
}

.password-toggle:hover {
  color: #ffffff;
}

.form-options {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-top: -4px;
  margin-bottom: 0px;
}

.checkbox-wrapper {
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
}

.checkbox-wrapper input[type="checkbox"] {
  width: 14px;
  height: 14px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 4px;
  background: rgba(255, 255, 255, 0.05);
  cursor: pointer;
}

.checkbox-label {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.7);
}

.password-security {
  margin-top: 6px;
  padding: 8px;
  background: rgba(255, 255, 255, 0.03);
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.08);
}

.security-level-bar {
  width: 100%;
  height: 3px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 2px;
  overflow: hidden;
  margin-bottom: 6px;
}

.security-level-fill {
  height: 100%;
  transition: all 0.3s ease;
  border-radius: 2px;
}

.security-level-fill.security-weak {
  background: #FF6B6B;
}

.security-level-fill.security-medium {
  background: #FFA500;
}

.security-level-fill.security-strong {
  background: #4CAF50;
}

.security-level-text {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 10px;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 0;
}

.security-level-text .security-weak {
  color: #FF6B6B;
}

.security-level-text .security-medium {
  color: #FFA500;
}

.security-level-text .security-strong {
  color: #4CAF50;
}

.info-icon-btn {
  background: transparent;
  border: none;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.5);
  padding: 2px;
  display: flex;
  align-items: center;
  transition: color 0.3s ease;
  margin-left: auto;
}

.info-icon-btn:hover {
  color: rgba(255, 255, 255, 0.8);
}

.info-icon-btn svg {
  width: 14px;
  height: 14px;
}

.password-rules {
  display: flex;
  flex-direction: column;
  gap: 4px;
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.password-rule {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 10px;
  color: rgba(255, 255, 255, 0.5);
  transition: color 0.3s ease;
}

.password-rule svg {
  flex-shrink: 0;
  width: 12px;
  height: 12px;
}

.password-rule.rule-valid {
  color: #4CAF50;
}

.password-rule.rule-invalid {
  color: rgba(255, 255, 255, 0.4);
}

.password-rule span {
  flex: 1;
}

.error-message {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #FF6B6B;
  background: rgba(255, 107, 107, 0.08);
  border: 1px solid rgba(255, 107, 107, 0.2);
  border-radius: 8px;
  padding: 6px 10px;
  font-size: 11px;
  margin-top: 0px;
}

.success-message {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #4CAF50;
  background: rgba(76, 175, 80, 0.08);
  border: 1px solid rgba(76, 175, 80, 0.2);
  border-radius: 8px;
  padding: 6px 10px;
  font-size: 11px;
  margin-top: 0px;
}

.form-actions {
  margin-top: 8px;
}

.btn-signup {
  width: 100%;
  padding: 11px;
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.btn-signup:hover {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(139, 92, 246, 0.3);
}

.btn-signup:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.spinner {
  width: 18px;
  height: 18px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.login-section {
  text-align: center;
  margin-top: 8px;
  color: rgba(255, 255, 255, 0.6);
  font-size: 11px;
}

.login-link {
  color: #8B5CF6;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.login-link:hover {
  color: #A78BFA;
}

/* Responsive */
@media (max-width: 968px) {
  .signup-container {
    flex-direction: column;
    max-width: 500px;
    max-height: 95vh;
  }

  .signup-left {
    min-height: 160px;
    padding: 24px 20px;
    border-right: none;
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  }

  .logo-wrapper {
    margin-bottom: 12px;
  }

  .restaurant-logo {
    max-width: 150px;
  }

  .brand-name {
    font-size: 22px;
    margin-bottom: 4px;
  }

  .brand-description {
    font-size: 11px;
  }

  .signup-right {
    padding: 20px 18px;
    overflow-y: auto;
    overflow-x: hidden;
  }

  .form-header {
    margin-bottom: 10px;
  }

  .welcome-section {
    margin-bottom: 10px;
  }

  .signup-title {
    font-size: 22px;
  }

  .signup-subtitle {
    font-size: 12px;
  }

  .signup-nav {
    gap: 30px;
    justify-content: center;
  }

  .signup-form {
    gap: 8px;
  }
}

@media (max-width: 640px) {
  .signup-page {
    padding: 12px;
    align-items: flex-start;
    padding-top: 20px;
  }

  .signup-container {
    max-width: 100%;
    border-radius: 12px;
    max-height: calc(100vh - 40px);
  }

  .signup-left {
    min-height: 140px;
    padding: 20px 16px;
  }

  .logo-wrapper {
    margin-bottom: 10px;
  }

  .restaurant-logo {
    max-width: 120px;
  }

  .brand-name {
    font-size: 20px;
  }

  .brand-description {
    font-size: 10px;
    letter-spacing: 1px;
  }

  .signup-right {
    padding: 24px 20px;
    overflow-y: auto;
    overflow-x: hidden;
    max-height: calc(100vh - 40px - 140px);
  }

  .form-header {
    margin-bottom: 10px;
  }

  .signup-nav {
    gap: 20px;
    font-size: 12px;
  }

  .welcome-section {
    margin-bottom: 10px;
  }

  .signup-title {
    font-size: 22px;
    margin-bottom: 4px;
  }

  .signup-subtitle {
    font-size: 12px;
  }

  .signup-form {
    gap: 10px;
  }

  .input-wrapper {
    padding: 10px 14px;
  }

  .input-icon {
    width: 18px;
    height: 18px;
    margin-right: 10px;
  }

  .form-input {
    font-size: 14px;
  }

  .btn-signup {
    padding: 12px;
    font-size: 14px;
  }

  .login-section {
    margin-top: 10px;
    font-size: 11px;
  }
}

@media (max-width: 480px) {
  .signup-page {
    padding: 8px;
    padding-top: 16px;
  }

  .signup-container {
    max-height: calc(100vh - 32px);
  }

  .signup-left {
    min-height: 120px;
    padding: 16px;
  }

  .restaurant-logo {
    max-width: 100px;
  }

  .brand-name {
    font-size: 18px;
  }

  .brand-description {
    font-size: 9px;
  }

  .signup-right {
    padding: 20px 16px;
    overflow-y: auto;
    overflow-x: hidden;
    max-height: calc(100vh - 32px - 120px);
  }

  .signup-form {
    gap: 8px;
  }
}
</style>

