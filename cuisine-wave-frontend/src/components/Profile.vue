<template>
  <div class="profile-page">
    <!-- Header Navigation -->
    <header class="header">
      <div class="header-content">
        <div class="logo">
          <img src="./logo_restaurant.png" alt="Sajian Nusantara Logo" class="logo-icon" />
          <div class="logo-text">
            <div class="logo-main">SAJIAN NUSANTARA</div>
            <div class="logo-sub">RESTAURANT</div>
          </div>
        </div>
        <button class="mobile-menu-toggle" :class="{ active: isMobileMenuOpen }" @click="toggleMobileMenu" aria-label="Toggle menu">
          <span></span>
          <span></span>
          <span></span>
        </button>
      
        <div class="header-right-section">
          <div class="header-auth" :class="{ 'mobile-open': isMobileMenuOpen }">
            <a href="#" class="auth-link" @click.prevent="handleHomeClick">Home</a>
            <span class="auth-separator">|</span>
            <a href="#" class="auth-link" @click.prevent="handleContactUsClick">Contact Us</a>
            <span class="auth-separator" v-if="!isLoggedIn">|</span>
            <a v-if="!isLoggedIn" href="#" class="auth-link" @click.prevent="handleLoginClick">Login</a>
            <span class="auth-separator" v-if="!isLoggedIn">|</span>
            <a v-if="!isLoggedIn" href="#" class="auth-link" @click.prevent="handleSignupClick">Sign Up</a>
          </div>
          <div class="header-buttons">
            <button class="btn-yellow" @click="$emit('show-menu')">See Menu & Order</button>
            <button class="btn-red" @click="$emit('show-reservation')">Table Reservation</button>
            <div v-if="isLoggedIn" class="user-menu-container" @click.stop>
              <button class="user-menu-btn" @click="toggleUserMenu" :title="currentUser?.username || currentUser?.email || 'User'">
                <span class="user-initial">{{ getUserInitial() }}</span>
              </button>
              <div v-if="isUserMenuOpen" class="user-menu-dropdown">
                <a href="#" class="user-menu-item" @click.prevent="handleProfile">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                  Profile
                </a>
                <a href="#" class="user-menu-item" @click.prevent="handleHistoryOrder">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M9 11l3 3L22 4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                  History Order
                </a>
                <a href="#" class="user-menu-item" @click.prevent="handleLogout">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <polyline points="16 17 21 12 16 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <line x1="21" y1="12" x2="9" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                  Logout
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Container - Modified for no-scroll layout -->
    <div class="profile-container">
      <div class="profile-card">
        <!-- Profile Avatar -->
        <div class="profile-avatar-section">
          <div class="profile-avatar-circle">
            <span class="profile-initial">{{ getUserInitial() }}</span>
          </div>
          <h2 class="profile-name">{{ userProfile?.username || 'User' }}</h2>
        </div>

        <!-- Loading State -->
        <div v-if="loadingProfile" class="profile-loading">
          <div class="spinner"></div>
          <p>Loading profile...</p>
        </div>

        <!-- Error State -->
        <div v-else-if="profileError" class="profile-error">
          <p>{{ profileError }}</p>
          <button @click="loadUserProfile" class="btn-retry">Retry</button>
        </div>

        <!-- Profile Form - Modified for side-by-side layout -->
        <form v-else-if="userProfile" @submit.prevent="saveProfile" class="profile-form">
          <div class="profile-form-grid">
            <!-- Left Column -->
            <div class="profile-form-column">
              <div class="form-group">
                <label class="form-label">Username</label>
                <input 
                  type="text" 
                  v-model="profileForm.username" 
                  class="form-input"
                  required
                  placeholder="Enter username"
                />
              </div>

              <div class="form-group">
                <label class="form-label">Email</label>
                <input 
                  type="email" 
                  :value="userProfile.email" 
                  class="form-input"
                  disabled
                  placeholder="Email cannot be changed"
                />
                <small class="form-hint">Email cannot be changed</small>
              </div>

              <div class="form-group">
                <label class="form-label">Phone Number</label>
                <input 
                  type="tel" 
                  v-model="profileForm.phoneNumber" 
                  class="form-input"
                  placeholder="Enter phone number"
                />
              </div>
            </div>

            <!-- Right Column -->
            <div class="profile-form-column">
              <div class="form-group">
                <label class="form-label">Role</label>
                <div class="form-input disabled">
                  <span :class="['profile-role-badge', userProfile.role?.toLowerCase()]">
                    {{ userProfile.role || 'USER' }}
                  </span>
                </div>
                <small class="form-hint">Role cannot be changed</small>
              </div>

              <div class="form-group">
                <label class="form-label">Member Since</label>
                <div class="form-input disabled">
                  {{ formatProfileDate(userProfile.createdAt) }}
                </div>
              </div>

              <!-- Empty space to align with left column -->
              <div class="form-group placeholder"></div>
            </div>
          </div>

          <!-- Messages and Actions -->
          <div class="profile-form-bottom">
            <div v-if="saveError" class="error-message">
              {{ saveError }}
            </div>

            <div v-if="saveSuccess" class="success-message">
              Profile updated successfully!
            </div>

            <div class="form-actions">
              <button type="button" @click="$emit('show-home')" class="btn-cancel">
                Cancel
              </button>
              <button type="submit" class="btn-save" :disabled="isSaving">
                {{ isSaving ? 'Saving...' : 'Save Changes' }}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  data() {
    return {
      userProfile: null,
      loadingProfile: false,
      profileError: null,
      profileForm: {
        username: '',
        phoneNumber: ''
      },
      isSaving: false,
      saveError: null,
      saveSuccess: false,
      isMobileMenuOpen: false,
      isUserMenuOpen: false,
      currentUser: null
    }
  },
  mounted() {
    this.loadUserProfile()
    this.checkLoginStatus()
    document.addEventListener('click', this.handleClickOutside)
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside)
  },
  methods: {
    async loadUserProfile() {
      this.loadingProfile = true
      this.profileError = null
      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.profileError = 'You must be logged in to view your profile'
          return
        }

        const response = await fetch('http://localhost:8080/api/auth/me', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        })

        if (!response.ok) {
          if (response.status === 401) {
            this.profileError = 'Session expired. Please login again.'
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            this.$emit('show-login')
          } else {
            const errorData = await response.json()
            this.profileError = errorData.error || 'Failed to load profile'
          }
          return
        }

        this.userProfile = await response.json()
        // Populate form with current values
        this.profileForm.username = this.userProfile.username || ''
        this.profileForm.phoneNumber = this.userProfile.phoneNumber || ''
      } catch (error) {
        this.profileError = 'Error loading profile: ' + error.message
        console.error('Error loading profile:', error)
      } finally {
        this.loadingProfile = false
      }
    },
    async saveProfile() {
      this.isSaving = true
      this.saveError = null
      this.saveSuccess = false

      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.saveError = 'You must be logged in to update your profile'
          return
        }

        const response = await fetch('http://localhost:8080/api/auth/profile', {
          method: 'PUT',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            username: this.profileForm.username,
            phoneNumber: this.profileForm.phoneNumber
          })
        })

        if (!response.ok) {
          if (response.status === 401) {
            this.saveError = 'Session expired. Please login again.'
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            this.$emit('show-login')
          } else {
            const errorData = await response.json()
            this.saveError = errorData.error || 'Failed to update profile'
          }
          return
        }

        const updatedUser = await response.json()
        this.userProfile = updatedUser
        // Update localStorage
        localStorage.setItem('user', JSON.stringify(updatedUser))
        
        this.saveSuccess = true
        setTimeout(() => {
          this.saveSuccess = false
        }, 3000)
      } catch (error) {
        this.saveError = 'Error updating profile: ' + error.message
        console.error('Error updating profile:', error)
      } finally {
        this.isSaving = false
      }
    },
    getUserInitial() {
      if (this.userProfile) {
        const name = this.userProfile.username || this.userProfile.email || 'User'
        return name.charAt(0).toUpperCase()
      }
      if (this.currentUser) {
        const name = this.currentUser.username || this.currentUser.email || 'User'
        return name.charAt(0).toUpperCase()
      }
      return 'U'
    },
    formatProfileDate(dateString) {
      if (!dateString) return 'N/A'
      try {
        const date = new Date(dateString)
        return date.toLocaleDateString('en-US', { 
          year: 'numeric', 
          month: 'long', 
          day: 'numeric' 
        })
      } catch (e) {
        return dateString
      }
    },
    checkLoginStatus() {
      const userStr = localStorage.getItem('user')
      const token = localStorage.getItem('token')
      if (userStr && token) {
        try {
          this.currentUser = JSON.parse(userStr)
        } catch (e) {
          console.error('Error parsing user data:', e)
          this.currentUser = null
        }
      }
    },
    toggleUserMenu() {
      this.isUserMenuOpen = !this.isUserMenuOpen
    },
    handleProfile() {
      this.isUserMenuOpen = false
      // Already on profile page
    },
    handleHistoryOrder() {
      this.isUserMenuOpen = false
      this.$emit('show-order-history')
    },
    handleLogout() {
      this.isUserMenuOpen = false
      if (confirm('Are you sure you want to logout?')) {
        localStorage.removeItem('token')
        localStorage.removeItem('user')
        this.currentUser = null
        // Refresh the page or emit event to update UI
        window.location.reload()
      }
    },
    toggleMobileMenu() {
      this.isMobileMenuOpen = !this.isMobileMenuOpen
    },
    closeMobileMenu() {
      this.isMobileMenuOpen = false
    },
    handleHomeClick() {
      this.closeMobileMenu()
      this.$emit('show-home')
    },
    handleLoginClick() {
      this.closeMobileMenu()
      this.$emit('show-login')
    },
    handleSignupClick() {
      this.closeMobileMenu()
      this.$emit('show-signup')
    },
    handleContactUsClick() {
      this.closeMobileMenu()
      alert('Contact Us: Please reach out to us at info@sajiannusantara.com')
    },
    handleClickOutside(event) {
      const userMenuContainer = event.target.closest('.user-menu-container')
      if (!userMenuContainer && this.isUserMenuOpen) {
        this.isUserMenuOpen = false
      }
    }
  },
  computed: {
    isLoggedIn() {
      return !!this.currentUser && !!localStorage.getItem('token')
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

.profile-page {
  min-height: 100vh;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 20% 80%, rgba(59, 130, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(139, 92, 246, 0.03) 0%, transparent 50%);
  background-attachment: fixed;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  color: #ffffff;
}

/* Header Navigation */
.header {
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  padding: 20px 0;
  width: 100%;
  position: sticky;
  top: 0;
  z-index: 1000;
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  transform: translateZ(0);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 1600px;
  padding: 0 20px;
  margin: 0 auto;
}

.logo {
  display: flex;
  align-items: center;
  gap: 15px;
}

.logo-icon {
  width: 50px;
  height: 50px;
  object-fit: contain;
  display: block;
}

.logo-text {
  display: flex;
  flex-direction: column;
}

.logo-main {
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
  letter-spacing: 1px;
}

.logo-sub {
  font-size: 12px;
  font-weight: 400;
  color: white;
  letter-spacing: 0.5px;
}

.header-right-section {
  display: flex;
  align-items: center;
  gap: 20px;
}

.header-auth {
  display: flex;
  align-items: center;
  gap: 8px;
}

.auth-link {
  color: #ffffff;
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  padding: 5px 10px;
  transition: color 0.3s;
  letter-spacing: 0.3px;
}

.auth-link:hover {
  color: #8B5CF6;
}

.auth-separator {
  color: #555;
  font-size: 14px;
}

.header-buttons {
  display: flex;
  gap: 10px;
  align-items: center;
}

.btn-yellow {
  background: linear-gradient(135deg, #FF9F1C 0%, #FF8C00 100%);
  color: #ffffff;
  border: none;
  padding: 14px 28px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  transition: all 0.3s ease;
  letter-spacing: 0.5px;
  box-shadow: 
    0 4px 15px rgba(255, 159, 28, 0.3),
    0 2px 8px rgba(0, 0, 0, 0.3);
}

.btn-yellow:hover {
  background: linear-gradient(135deg, #FFB84D 0%, #FF9F1C 100%);
  transform: translateY(-2px);
  box-shadow: 
    0 10px 25px rgba(255, 159, 28, 0.4),
    0 4px 12px rgba(0, 0, 0, 0.4);
}

.btn-red {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: white;
  border: none;
  padding: 14px 28px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  transition: all 0.3s ease;
  letter-spacing: 0.5px;
  box-shadow: 
    0 4px 15px rgba(139, 92, 246, 0.3),
    0 2px 8px rgba(0, 0, 0, 0.3);
}

.btn-red:hover {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px);
  box-shadow: 
    0 10px 25px rgba(139, 92, 246, 0.4),
    0 4px 12px rgba(0, 0, 0, 0.4);
}

.user-menu-container {
  position: relative;
  display: inline-block;
}

.user-menu-btn {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: white;
  border: none;
  width: 44px;
  height: 44px;
  border-radius: 50%;
  cursor: pointer;
  font-size: 18px;
  font-weight: 600;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 
    0 4px 15px rgba(139, 92, 246, 0.4),
    0 2px 8px rgba(0, 0, 0, 0.3);
  position: relative;
  overflow: hidden;
}

.user-menu-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.user-menu-btn:hover {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px);
  box-shadow: 
    0 8px 25px rgba(139, 92, 246, 0.5),
    0 4px 12px rgba(0, 0, 0, 0.4);
}

.user-menu-btn:hover::before {
  left: 100%;
}

.user-menu-btn:active {
  transform: translateY(0);
  box-shadow: 
    0 2px 8px rgba(139, 92, 246, 0.4),
    inset 0 2px 4px rgba(0, 0, 0, 0.2);
}

.user-initial {
  color: white;
  font-weight: 600;
  font-size: 18px;
  line-height: 1;
  z-index: 1;
  position: relative;
}

.user-menu-dropdown {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  background: rgba(18, 18, 18, 0.95);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  box-shadow: 
    0 10px 40px rgba(0, 0, 0, 0.5),
    0 0 0 1px rgba(255, 255, 255, 0.05);
  min-width: 200px;
  padding: 8px;
  z-index: 1000;
  animation: dropdownFadeIn 0.2s ease;
}

@keyframes dropdownFadeIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.user-menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  color: rgba(255, 255, 255, 0.9);
  text-decoration: none;
  border-radius: 8px;
  transition: all 0.2s ease;
  font-size: 14px;
  font-weight: 500;
}

.user-menu-item:hover {
  background: rgba(139, 92, 246, 0.2);
  color: #ffffff;
}

.user-menu-item svg {
  color: rgba(255, 255, 255, 0.7);
}

/* Mobile Menu Toggle */
.mobile-menu-toggle {
  display: none;
  flex-direction: column;
  gap: 5px;
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 5px;
}

.mobile-menu-toggle span {
  width: 25px;
  height: 3px;
  background-color: white;
  transition: all 0.3s;
  border-radius: 2px;
}

.mobile-menu-toggle.active span:nth-child(1) {
  transform: rotate(45deg) translate(8px, 8px);
}

.mobile-menu-toggle.active span:nth-child(2) {
  opacity: 0;
}

.mobile-menu-toggle.active span:nth-child(3) {
  transform: rotate(-45deg) translate(7px, -7px);
}

/* MODIFIED FOR FIXED HEIGHT LAYOUT */
.profile-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  height: calc(100vh - 90px);
  display: flex;
  align-items: center;
  justify-content: center;
}

.profile-card {
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 16px;
  padding: 3rem;
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  width: 100%;
  max-width: 900px;
  max-height: 80vh;
  display: flex;
  flex-direction: column;
}

.profile-avatar-section {
  text-align: center;
  margin-bottom: 3rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  flex-shrink: 0;
}

.profile-avatar-circle {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 1.5rem;
  box-shadow: 
    0 8px 25px rgba(139, 92, 246, 0.4),
    0 4px 12px rgba(0, 0, 0, 0.3);
}

.profile-initial {
  color: white;
  font-weight: 600;
  font-size: 2.5rem;
  line-height: 1;
}

.profile-name {
  font-size: 1.5rem;
  font-weight: 600;
  color: #ffffff;
  margin: 0;
}

.profile-loading,
.profile-error {
  text-align: center;
  padding: 2rem;
  color: rgba(255, 255, 255, 0.7);
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.profile-loading .spinner {
  width: 50px;
  height: 50px;
  border: 4px solid rgba(255, 255, 255, 0.1);
  border-top-color: #8B5CF6;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
  margin: 0 auto 1rem;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.profile-error {
  color: #FF6B6B;
}

.btn-retry {
  background: rgba(139, 92, 246, 0.2);
  color: #8B5CF6;
  border: 1px solid rgba(139, 92, 246, 0.3);
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 1rem;
  transition: all 0.3s ease;
  font-weight: 500;
}

.btn-retry:hover {
  background: rgba(139, 92, 246, 0.3);
  border-color: rgba(139, 92, 246, 0.5);
}

/* NEW GRID LAYOUT FOR FORMS */
.profile-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  flex-grow: 1;
  overflow-y: auto;
  padding-right: 0.5rem;
}

.profile-form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  margin-bottom: 1rem;
}

.profile-form-column {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.profile-form-bottom {
  margin-top: auto;
  padding-top: 1.5rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group.placeholder {
  opacity: 0;
  pointer-events: none;
  height: 3.5rem;
}

.form-label {
  font-size: 0.875rem;
  font-weight: 500;
  color: rgba(255, 255, 255, 0.8);
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.form-input {
  width: 100%;
  padding: 0.875rem 1rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  font-size: 1rem;
  font-family: inherit;
  background: rgba(255, 255, 255, 0.05);
  color: #ffffff;
  transition: all 0.3s ease;
  height: 48px;
  box-sizing: border-box;
}

.form-input:focus {
  outline: none;
  border-color: rgba(139, 92, 246, 0.5);
  background: rgba(255, 255, 255, 0.08);
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.form-input:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  background: rgba(255, 255, 255, 0.03);
}

.form-input.disabled {
  opacity: 0.7;
  background: rgba(255, 255, 255, 0.03);
  cursor: default;
  display: flex;
  align-items: center;
  min-height: 48px;
}

.form-hint {
  font-size: 0.75rem;
  color: rgba(255, 255, 255, 0.5);
  font-style: italic;
}

.profile-role-badge {
  display: inline-block;
  padding: 0.375rem 0.875rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.profile-role-badge.admin {
  background: rgba(139, 92, 246, 0.15);
  color: #8B5CF6;
  border: 1px solid rgba(139, 92, 246, 0.3);
}

.profile-role-badge.user {
  background: rgba(59, 130, 246, 0.15);
  color: #3B82F6;
  border: 1px solid rgba(59, 130, 246, 0.3);
}

.error-message {
  background: rgba(255, 107, 107, 0.1);
  color: #FF6B6B;
  padding: 1rem;
  border-radius: 8px;
  border: 1px solid rgba(255, 107, 107, 0.3);
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.success-message {
  background: rgba(6, 182, 212, 0.1);
  color: #06B6D4;
  padding: 1rem;
  border-radius: 8px;
  border: 1px solid rgba(6, 182, 212, 0.3);
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
}

.btn-cancel {
  background: rgba(255, 255, 255, 0.05);
  color: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 0.875rem 2rem;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
  min-width: 120px;
}

.btn-cancel:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(255, 255, 255, 0.2);
  color: #ffffff;
}

.btn-save {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: white;
  border: none;
  padding: 0.875rem 2rem;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.3);
  min-width: 140px;
}

.btn-save:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(139, 92, 246, 0.4);
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
}

.btn-save:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

/* Custom scrollbar for the form */
.profile-form::-webkit-scrollbar {
  width: 6px;
}

.profile-form::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
}

.profile-form::-webkit-scrollbar-thumb {
  background: rgba(139, 92, 246, 0.3);
  border-radius: 3px;
}

.profile-form::-webkit-scrollbar-thumb:hover {
  background: rgba(139, 92, 246, 0.5);
}

/* ====== UPDATED LAPTOP LAYOUT - MORE COMPACT ====== */

/* Medium screens (tablet) */
@media (min-width: 769px) and (max-width: 1024px) {
  .profile-container {
    max-width: 900px;
    padding: 1.5rem;
  }

  .profile-card {
    padding: 2rem;
  }

  .profile-form-grid {
    gap: 1.5rem;
  }

  .profile-avatar-circle {
    width: 90px;
    height: 90px;
  }

  .profile-initial {
    font-size: 2rem;
  }
}

/* Large screens (laptop) - COMPACT VERSION */
@media (min-width: 1025px) {
  .profile-container {
    padding: 1rem 2rem;
    height: calc(100vh - 80px);
  }
  
  .profile-card {
    padding: 2rem;
    max-height: 550px;
  }
  
  .profile-avatar-section {
    margin-bottom: 1.5rem;
    padding-bottom: 1rem;
  }
  
  .profile-avatar-circle {
    width: 70px;
    height: 70px;
    margin-bottom: 0.75rem;
  }
  
  .profile-initial {
    font-size: 1.75rem;
  }
  
  .profile-name {
    font-size: 1.25rem;
  }
  
  .profile-form {
    gap: 1rem;
  }
  
  .profile-form-grid {
    gap: 1.5rem;
    margin-bottom: 0.5rem;
  }
  
  .profile-form-column {
    gap: 1rem;
  }
  
  .profile-form-bottom {
    padding-top: 1rem;
  }
  
  .form-group {
    gap: 0.375rem;
  }
  
  .form-label {
    font-size: 0.8rem;
  }
  
  .form-input {
    padding: 0.75rem 1rem;
    height: 44px;
    font-size: 0.95rem;
  }
  
  .form-input.disabled {
    min-height: 44px;
  }
  
  .btn-cancel,
  .btn-save {
    padding: 0.75rem 1.5rem;
  }
  
  .profile-role-badge {
    font-size: 0.8rem;
    padding: 0.25rem 0.75rem;
  }
  
  .form-hint {
    font-size: 0.7rem;
  }
  
  .error-message,
  .success-message {
    padding: 0.75rem;
    font-size: 0.85rem;
    margin-bottom: 0.75rem;
  }
  
  .profile-form::-webkit-scrollbar {
    width: 4px;
  }
}

/* ====== RESPONSIVE STYLES - MOBILE ====== */

@media (max-width: 768px) {
  .header {
    padding: 12px 0;
  }

  .header-content {
    padding: 0 12px;
    flex-wrap: wrap;
  }

  .logo {
    gap: 10px;
  }

  .logo-icon {
    width: 42px;
    height: 42px;
  }

  .logo-main {
    font-size: 16px;
    line-height: 1.2;
  }

  .logo-sub {
    font-size: 9px;
    line-height: 1.2;
  }

  .mobile-menu-toggle {
    display: flex;
    order: 2;
    margin-left: auto;
  }

  .header-right-section {
    order: 3;
    width: 100%;
    flex-direction: column;
    gap: 0;
  }

  .header-auth {
    display: none;
    width: 100%;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    margin: 0;
    padding: 0;
    border-top: 1px solid rgba(255, 255, 255, 0.1);
    gap: 8px;
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.3s ease, padding 0.3s ease;
    background-color: rgba(18, 18, 18, 0.95);
    flex-wrap: wrap;
  }

  .header-auth.mobile-open {
    display: flex;
    max-height: 80px;
    padding: 12px 15px;
  }

  .header-auth .auth-separator {
    display: inline;
    color: rgba(255, 255, 255, 0.3);
    font-size: 14px;
  }

  .header-auth .auth-link {
    font-size: 13px;
    padding: 8px 12px;
    text-align: center;
    border-bottom: none;
    transition: color 0.2s ease;
    white-space: nowrap;
  }

  .header-auth .auth-link:hover {
    background-color: transparent;
    color: #8B5CF6;
  }

  .header-buttons {
    width: 100%;
    margin-top: 12px;
    gap: 8px;
  }

  .header-buttons button {
    flex: 1;
    padding: 12px 16px;
    font-size: 13px;
    min-height: 44px;
  }

  .header-buttons .user-menu-btn {
    width: 40px;
    height: 40px;
    flex: 0 0 auto;
  }

  .user-initial {
    font-size: 16px;
  }

  .user-menu-dropdown {
    right: 0;
    left: auto;
    min-width: 180px;
  }

  /* Mobile layout overrides for fixed height */
  .profile-container {
    height: auto;
    min-height: calc(100vh - 90px);
    padding: 1rem;
    display: block;
  }

  .profile-card {
    max-height: none;
    padding: 1.5rem;
    margin: 0;
  }

  .profile-form-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }

  .profile-form-column {
    gap: 1.5rem;
  }

  .form-group.placeholder {
    display: none;
  }

  .form-actions {
    flex-direction: column;
  }

  .btn-cancel,
  .btn-save {
    width: 100%;
    min-width: 0;
  }
}
</style>