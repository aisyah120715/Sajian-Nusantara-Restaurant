<template>
  <div class="order-history-page">
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

    <!-- Main Container -->
    <div class="order-history-container">
      <div v-if="loadingOrders" class="loading-state">
        <div class="spinner"></div>
        <p>Loading your orders...</p>
      </div>

      <div v-else-if="orderError" class="error-state">
        <p>{{ orderError }}</p>
        <button @click="loadOrders" class="btn-retry">Retry</button>
      </div>

      <div v-else-if="orders.length === 0" class="empty-state">
        <div class="empty-icon">
          <svg width="64" height="64" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" stroke="currentColor" stroke-width="2"/>
            <line x1="3" y1="6" x2="21" y2="6" stroke="currentColor" stroke-width="2"/>
            <path d="M16 10a4 4 0 0 1-8 0" stroke="currentColor" stroke-width="2"/>
          </svg>
        </div>
        <h2>No Orders Yet</h2>
        <p>You haven't placed any orders yet. Start ordering from our menu!</p>
        <button @click="$emit('show-home')" class="btn-browse-menu">Browse Menu</button>
      </div>

      <div v-else class="orders-list">
        <div 
          v-for="order in orders" 
          :key="order.id" 
          class="order-card"
        >
          <div class="order-header">
            <div class="order-number-section">
              <svg class="order-icon" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" stroke="currentColor" stroke-width="2"/>
                <line x1="3" y1="6" x2="21" y2="6" stroke="currentColor" stroke-width="2"/>
                <path d="M16 10a4 4 0 0 1-8 0" stroke="currentColor" stroke-width="2"/>
              </svg>
              <div>
                <h3 class="order-number">Order #{{ order.orderNumber }}</h3>
                <p class="order-date">{{ formatDate(order.createdAt) }}</p>
              </div>
            </div>
            <div class="order-type-badge" :class="order.orderType">
              {{ order.orderType === 'dine-in' ? 'Dine In' : 'Pickup' }}
            </div>
          </div>

          <div class="order-details">
            <div v-if="order.orderType === 'dine-in' && order.tableNumber" class="detail-row">
              <span class="detail-label">Table:</span>
              <span class="detail-value">Table {{ order.tableNumber }}</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">Payment Method:</span>
              <span class="detail-value">{{ formatPaymentMethod(order.paymentMethod) }}</span>
            </div>
          </div>

          <div class="order-items">
            <h4 class="items-title">Items:</h4>
            <div 
              v-for="(item, index) in order.items" 
              :key="index"
              class="order-item-row"
            >
              <div class="item-info">
                <span class="item-name">{{ item.name }}</span>
                <span class="item-quantity">× {{ item.quantity }}</span>
              </div>
              <div class="item-price">
                RM {{ (item.price * item.quantity).toFixed(2) }}
              </div>
              <div v-if="item.notes" class="item-notes">
                <small>Note: {{ item.notes }}</small>
              </div>
            </div>
          </div>

          <div class="order-summary">
            <div class="summary-row">
              <span>Subtotal:</span>
              <span>RM {{ order.subtotal?.toFixed(2) || '0.00' }}</span>
            </div>
            <div class="summary-row">
              <span>{{ order.orderType === 'pickup' ? 'Packaging Fee:' : 'Service Fee:' }}</span>
              <span>RM {{ order.serviceFee?.toFixed(2) || '0.00' }}</span>
            </div>
            <div class="summary-row total-row">
              <span>Total:</span>
              <span>RM {{ order.total?.toFixed(2) || '0.00' }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OrderHistory',
  data() {
    return {
      orders: [],
      loadingOrders: false,
      orderError: null,
      isMobileMenuOpen: false,
      isUserMenuOpen: false,
      currentUser: null
    }
  },
  mounted() {
    this.loadOrders()
    this.checkLoginStatus()
    document.addEventListener('click', this.handleClickOutside)
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside)
  },
  methods: {
    async loadOrders() {
      this.loadingOrders = true
      this.orderError = null
      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.orderError = 'You must be logged in to view order history'
          return
        }

        const response = await fetch('http://localhost:8080/api/orders/my-orders', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        })

        if (!response.ok) {
          if (response.status === 401) {
            this.orderError = 'Session expired. Please login again.'
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            this.$emit('show-login')
          } else {
            const errorData = await response.json()
            this.orderError = errorData.error || 'Failed to load orders'
          }
          return
        }

        const ordersData = await response.json()
        // Sort orders by date (newest first)
        this.orders = ordersData.sort((a, b) => {
          const dateA = new Date(a.createdAt)
          const dateB = new Date(b.createdAt)
          return dateB - dateA
        })
      } catch (error) {
        this.orderError = 'Error loading orders: ' + error.message
        console.error('Error loading orders:', error)
      } finally {
        this.loadingOrders = false
      }
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A'
      try {
        const date = new Date(dateString)
        return date.toLocaleDateString('en-US', { 
          year: 'numeric', 
          month: 'long', 
          day: 'numeric',
          hour: '2-digit',
          minute: '2-digit'
        })
      } catch (e) {
        return dateString
      }
    },
    formatPaymentMethod(method) {
      if (!method) return 'N/A'
      if (method === 'online') return 'Online Payment'
      if (method === 'counter') return 'Pay At Counter'
      return method
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
      this.$emit('show-profile')
    },
    handleHistoryOrder() {
      this.isUserMenuOpen = false
      // Already on order history page
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
    getUserInitial() {
      if (this.currentUser) {
        const name = this.currentUser.username || this.currentUser.email || 'User'
        return name.charAt(0).toUpperCase()
      }
      return 'U'
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

.order-history-page {
  min-height: 100vh;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 20% 80%, rgba(59, 130, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(139, 92, 246, 0.03) 0%, transparent 50%);
  background-attachment: fixed;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  color: #ffffff;
  padding-bottom: 2rem;
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

/* Updated Container - Made wider */
.order-history-container {
  max-width: 1200px; /* Increased from 1000px */
  margin: 3rem auto;
  padding: 0 2rem;
  width: 100%;
}

.loading-state,
.error-state,
.empty-state {
  text-align: center;
  padding: 4rem 2rem;
  color: rgba(255, 255, 255, 0.7);
}

.loading-state .spinner {
  width: 50px;
  height: 50px;
  border: 4px solid rgba(255, 255, 255, 0.1);
  border-top-color: #8B5CF6;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
  margin: 0 auto 1.5rem;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.error-state {
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

.empty-state {
  padding: 6rem 2rem;
}

.empty-icon {
  color: rgba(255, 255, 255, 0.3);
  margin-bottom: 1.5rem;
}

.empty-state h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: #ffffff;
  margin: 0 0 1rem 0;
}

.empty-state p {
  color: rgba(255, 255, 255, 0.6);
  margin: 0 0 2rem 0;
}

.btn-browse-menu {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: white;
  border: none;
  padding: 0.875rem 2rem;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.3);
}

.btn-browse-menu:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(139, 92, 246, 0.4);
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.order-card {
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 16px;
  padding: 2.5rem; /* Increased padding */
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  transition: all 0.3s ease;
  margin: 0 auto;
  width: 100%;
}

.order-card:hover {
  border-color: rgba(139, 92, 246, 0.3);
  transform: translateY(-2px);
  box-shadow: 
    0 30px 60px -12px rgba(0, 0, 0, 0.6),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 1.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.order-number-section {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.order-icon {
  color: #8B5CF6;
  flex-shrink: 0;
}

.order-number {
  font-size: 1.4rem; /* Slightly larger */
  font-weight: 600;
  color: #ffffff;
  margin: 0 0 0.25rem 0;
}

.order-date {
  font-size: 0.95rem; /* Slightly larger */
  color: rgba(255, 255, 255, 0.6);
  margin: 0;
}

.order-type-badge {
  display: inline-block;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.875rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.order-type-badge.dine-in {
  background: rgba(6, 182, 212, 0.15);
  color: #06B6D4;
  border: 1px solid rgba(6, 182, 212, 0.3);
}

.order-type-badge.pickup {
  background: rgba(255, 159, 28, 0.15);
  color: #FF9F1C;
  border: 1px solid rgba(255, 159, 28, 0.3);
}

.order-details {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  margin-bottom: 1.5rem;
  padding: 1.25rem; /* Increased padding */
  background: rgba(255, 255, 255, 0.03);
  border-radius: 12px; /* Slightly larger radius */
}

.detail-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-label {
  font-size: 0.95rem; /* Slightly larger */
  color: rgba(255, 255, 255, 0.6);
  font-weight: 500;
}

.detail-value {
  font-size: 0.95rem; /* Slightly larger */
  color: #ffffff;
  font-weight: 600;
}

.order-items {
  margin-bottom: 1.5rem;
}

.items-title {
  font-size: 1.1rem; /* Slightly larger */
  font-weight: 600;
  color: #ffffff;
  margin: 0 0 1rem 0;
}

.order-item-row {
  padding: 0.85rem 0; /* Increased padding */
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.order-item-row:last-child {
  border-bottom: none;
}

.item-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.25rem;
}

.item-name {
  font-size: 1rem; /* Slightly larger */
  color: #ffffff;
  font-weight: 500;
}

.item-quantity {
  font-size: 0.95rem; /* Slightly larger */
  color: rgba(255, 255, 255, 0.6);
}

.item-price {
  font-size: 1rem; /* Slightly larger */
  color: #8B5CF6;
  font-weight: 600;
}

.item-notes {
  margin-top: 0.25rem;
  padding-left: 0.5rem;
}

.item-notes small {
  font-size: 0.8rem; /* Slightly larger */
  color: rgba(255, 255, 255, 0.5);
  font-style: italic;
}

.order-summary {
  padding-top: 1.5rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 1rem; /* Slightly larger */
  color: rgba(255, 255, 255, 0.8);
}

.summary-row.total-row {
  font-size: 1.4rem; /* Slightly larger */
  font-weight: 700;
  color: #ffffff;
  padding-top: 0.75rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  margin-top: 0.5rem;
}

.summary-row.total-row span:last-child {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* Mobile layout remains exactly the same */
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

  .order-history-container {
    margin: 2rem auto;
    padding: 0 1rem;
    max-width: 100%; /* Full width on mobile */
  }

  .order-card {
    padding: 1.5rem; /* Original mobile padding */
  }

  .order-header {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }

  .order-type-badge {
    align-self: flex-start;
  }

  /* Reset mobile sizes to original */
  .order-number {
    font-size: 1.25rem;
  }

  .order-date {
    font-size: 0.875rem;
  }

  .detail-label,
  .detail-value {
    font-size: 0.875rem;
  }

  .items-title {
    font-size: 1rem;
  }

  .item-name,
  .item-price {
    font-size: 0.95rem;
  }

  .item-quantity {
    font-size: 0.875rem;
  }

  .item-notes small {
    font-size: 0.75rem;
  }

  .summary-row {
    font-size: 0.95rem;
  }

  .summary-row.total-row {
    font-size: 1.25rem;
  }
}

/* Tablet responsive adjustments */
@media (min-width: 769px) and (max-width: 1024px) {
  .order-history-container {
    max-width: 900px;
    padding: 0 1.5rem;
  }
}

/* For extra large screens */
@media (min-width: 1400px) {
  .order-history-container {
    max-width: 1400px;
  }
}
</style>