<template>
  <div class="forgot-password-page">
    <div class="forgot-password-container">
      <!-- Left Side - Logo/Illustration -->
      <div class="forgot-password-left">
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

      <!-- Right Side - Forgot Password Form -->
      <div class="forgot-password-right">
        <nav class="forgot-password-nav">
          <a href="#" class="nav-link" @click.prevent="goToLogin">Back to Login</a>
        </nav>

        <div class="forgot-password-form-container">
          <h1 class="forgot-password-title">Forgot Password?</h1>
          <p class="forgot-password-subtitle">Enter your email address and we'll send you a reset link</p>

          <form @submit.prevent="handleForgotPassword" class="forgot-password-form">
            <div class="form-group">
              <label class="form-label">Email Address</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  <polyline points="22,6 12,13 2,6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input type="email" v-model="email" placeholder="Email Address" class="form-input" required />
              </div>
            </div>

            <div v-if="errorMessage" class="error-message">
              {{ errorMessage }}
            </div>

            <div v-if="successMessage" class="success-message">
              {{ successMessage }}
            </div>

            <div class="form-buttons">
              <button type="submit" class="btn-submit" :disabled="isLoading">
                {{ isLoading ? 'Sending...' : 'Send Reset Link' }}
              </button>
              <button type="button" class="btn-back" @click="goToLogin">Back to Login</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ForgotPassword',
  data() {
    return {
      email: '',
      errorMessage: '',
      successMessage: '',
      isLoading: false
    }
  },
  methods: {
    async handleForgotPassword() {
      this.errorMessage = ''
      this.successMessage = ''
      this.isLoading = true

      try {
        const response = await fetch('http://localhost:8080/api/auth/forgot-password', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: this.email
          })
        })

        const data = await response.json()

        if (!response.ok) {
          throw new Error(data.error || 'Failed to send reset link')
        }

        this.successMessage = data.message || 'Password reset link has been sent to your email. Please check your email inbox.'
        
        // Clear email after success
        setTimeout(() => {
          this.email = ''
        }, 2000)
      } catch (error) {
        this.errorMessage = error.message || 'Failed to send reset link. Please try again.'
        console.error('Forgot password error:', error)
      } finally {
        this.isLoading = false
      }
    },
    goToLogin() {
      this.$emit('show-login')
    }
  }
}
</script>

<style scoped>
.forgot-password-page {
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

.forgot-password-page::before {
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

.forgot-password-container {
  display: flex;
  width: 100%;
  max-width: 1000px;
  max-height: 85vh;
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  border-radius: 16px;
  overflow: hidden;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
}

.forgot-password-left {
  flex: 1;
  background: 
    linear-gradient(135deg, rgba(139, 92, 246, 0.05) 0%, rgba(255, 159, 28, 0.05) 100%),
    radial-gradient(circle at 30% 70%, rgba(139, 92, 246, 0.03) 0%, transparent 50%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  padding: 24px 20px;
  border-right: 1px solid rgba(255, 255, 255, 0.05);
  overflow: hidden;
}

.brand-section {
  text-align: center;
  position: relative;
  z-index: 2;
}

.logo-wrapper {
  margin-bottom: 16px;
}

.restaurant-logo {
  max-width: 140px;
  width: 100%;
  height: auto;
  filter: brightness(1.1);
}

.brand-name {
  font-size: 24px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 4px;
  letter-spacing: 0.5px;
  font-family: 'Georgia', serif;
}

.brand-description {
  font-size: 13px;
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

.forgot-password-right {
  flex: 1.2;
  padding: 28px 32px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  width: 100%;
}

.forgot-password-nav {
  display: flex;
  gap: 40px;
  margin-bottom: 16px;
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

.forgot-password-form-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.forgot-password-title {
  font-size: 26px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 4px;
  letter-spacing: -0.5px;
}

.forgot-password-subtitle {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.6);
  font-weight: 400;
  margin-bottom: 16px;
}

.forgot-password-form {
  display: flex;
  flex-direction: column;
  gap: 14px;
  width: 100%;
  max-width: 100%;
}

.form-group {
  width: 100%;
  max-width: 100%;
}

.form-label {
  display: block;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  margin-bottom: 8px;
  font-weight: 500;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  padding: 12px 16px;
  min-height: 44px;
  transition: all 0.3s ease;
}

.input-wrapper:focus-within {
  border-color: rgba(139, 92, 246, 0.5);
  background: rgba(255, 255, 255, 0.06);
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.input-icon {
  color: rgba(255, 255, 255, 0.4);
  margin-right: 12px;
  flex-shrink: 0;
}

.form-input {
  flex: 1;
  background: transparent;
  border: none;
  outline: none;
  color: #ffffff;
  font-size: 14px;
  font-weight: 400;
}

.form-input::placeholder {
  color: rgba(255, 255, 255, 0.3);
}

.form-buttons {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-top: 4px;
}

.btn-submit {
  width: 100%;
  padding: 14px;
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-submit:hover:not(:disabled) {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(139, 92, 246, 0.3);
}

.btn-submit:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.btn-back {
  width: 100%;
  padding: 14px;
  background: transparent;
  color: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-back:hover {
  border-color: rgba(255, 255, 255, 0.2);
  color: #ffffff;
  background: rgba(255, 255, 255, 0.05);
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
  width: 100%;
  box-sizing: border-box;
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
  width: 100%;
  box-sizing: border-box;
}

@media (max-width: 968px) {
  .forgot-password-container {
    flex-direction: column;
    max-width: 500px;
    max-height: 95vh;
  }

  .forgot-password-left {
    min-height: 160px;
    padding: 24px 20px;
    border-right: none;
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  }

  .restaurant-logo {
    max-width: 100px;
  }

  .forgot-password-right {
    padding: 24px 20px;
    overflow: hidden;
  }

  .forgot-password-title {
    font-size: 24px;
  }

  .forgot-password-subtitle {
    font-size: 13px;
  }
}

@media (max-width: 640px) {
  .forgot-password-page {
    padding: 12px;
    align-items: flex-start;
    padding-top: 20px;
  }

  .forgot-password-container {
    max-width: 100%;
    border-radius: 12px;
    max-height: calc(100vh - 40px);
  }

  .forgot-password-left {
    min-height: 140px;
    padding: 20px 16px;
  }

  .restaurant-logo {
    max-width: 80px;
  }

  .forgot-password-right {
    padding: 24px 20px;
  }

  .forgot-password-nav {
    gap: 30px;
    justify-content: center;
    margin-bottom: 12px;
  }

  .forgot-password-title {
    font-size: 22px;
    margin-bottom: 4px;
  }

  .forgot-password-subtitle {
    font-size: 12px;
  }

  .forgot-password-form {
    gap: 12px;
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

  .btn-submit,
  .btn-back {
    padding: 12px;
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .forgot-password-page {
    padding: 8px;
    padding-top: 16px;
  }

  .forgot-password-container {
    max-height: calc(100vh - 32px);
  }

  .forgot-password-left {
    min-height: 120px;
    padding: 16px;
  }

  .restaurant-logo {
    max-width: 70px;
  }

  .forgot-password-nav {
    gap: 20px;
    font-size: 12px;
  }
}
</style>

