<template>
  <div class="menu-order-page" @click="handleClickOutside">
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

    <div class="menu-order-container">
      <!-- Left Column - Product Catalog -->
      <div class="product-catalog" :class="{ 'hide-on-mobile': showOrderSummary }">
        <!-- Top Bar -->
        <div class="catalog-header">
          <div class="search-box">
            <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <circle cx="11" cy="11" r="8" stroke="currentColor" stroke-width="2"/>
              <path d="m21 21-4.35-4.35" stroke="currentColor" stroke-width="2"/>
            </svg>
            <input 
              type="text" 
              placeholder="Search in products" 
              class="search-input"
              v-model="searchQuery"
            />
          </div>
          <div class="filter-dropdowns">
            <select class="filter-select" v-model="selectedCategory">
              <option 
                v-for="(category, index) in categories" 
                :key="index"
                :value="index"
              >
                {{ capitalizeFirst(category) }}
              </option>
            </select>
          </div>
        </div>

        <!-- Category Tabs -->
        <div class="category-tabs">
          <button 
            v-for="(category, index) in categories" 
            :key="index"
            class="category-tab"
            :class="{ active: selectedCategory === index }"
            @click="selectedCategory = index"
          >
            {{ capitalizeFirst(category) }}
          </button>
        </div>

        <!-- Product Grid -->
        <div v-if="loadingProducts" class="loading-products">
          <p>Loading menu items...</p>
        </div>
        <div v-else-if="!filteredProducts || filteredProducts.length === 0" class="no-products">
          <p>No menu items found.</p>
        </div>
        <div v-else class="products-grid">
          <div 
            v-for="product in filteredProducts" 
            :key="product.id"
            class="product-card"
            @click="addToOrder(product)"
          >
            <div class="product-image">
              <img 
                v-if="product.image" 
                :src="product.image" 
                :alt="product.name"
                @error="$event.target.src='/placeholder-food.jpg'"
              />
              <div v-else class="no-image-placeholder">No Image</div>
              <button class="add-btn" @click.stop="addToOrder(product)">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M12 5v14M5 12h14" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                </svg>
              </button>
            </div>
            <div class="product-info">
              <h3 class="product-name">{{ product.name || 'Food Item' }}</h3>
              <p class="product-description" v-if="product.description">{{ product.description }}</p>
              <p class="product-price">RM {{ product.price ? product.price.toFixed(2) : '0.00' }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Column - Order Summary -->
      <div class="order-summary" :class="{ 'show-order': showOrderSummary }">
        <!-- Mobile Back Button -->
        <button v-if="showOrderSummary" class="mobile-back-btn" @click="showOrderSummary = false">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M19 12H5M12 19l-7-7 7-7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
          </svg>
          <span>Back to Menu</span>
        </button>
        
        <!-- Top Bar with Dining Options - MADE MORE VISIBLE -->
        <div class="order-header">
          <div class="dining-options">
            <div class="dining-option-label">Select Dining</div>
            <div class="filter-dropdowns">
              <select class="filter-select" v-model="selectedDining">
                <option value="">Select Dining Type</option>
                <option value="dine-in">Dine In</option>
                <option value="pickup">Pickup</option>
              </select>
              <select 
                class="filter-select" 
                v-model="selectedTable"
                :disabled="selectedDining !== 'dine-in'"
              >
                <option value="">Select Table</option>
                <option v-for="tableNum in 15" :key="tableNum" :value="tableNum">
                  Table {{ tableNum }}
                </option>
              </select>
            </div>
          </div>
        </div>

        <!-- Order Header -->
        <div class="order-title">
          <svg class="bag-icon" width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" stroke="currentColor" stroke-width="2"/>
            <line x1="3" y1="6" x2="21" y2="6" stroke="currentColor" stroke-width="2"/>
            <path d="M16 10a4 4 0 0 1-8 0" stroke="currentColor" stroke-width="2"/>
          </svg>
          <span>Order #{{ orderNumber }}</span>
        </div>

        <!-- Order Items - Scrollable Container -->
        <div class="order-items-container">
          <div class="order-items">
            <div 
              v-for="(item, index) in orderItems" 
              :key="index"
              class="order-item"
            >
              <div class="item-info">
                <h4 class="item-name">{{ item.name }}</h4>
                <p class="item-price">RM {{ item.price }} × {{ item.quantity }} = RM {{ (item.price * item.quantity).toFixed(2) }}</p>
                <div class="item-notes-section">
                  <div v-if="editingNotesIndex === index" class="notes-input-wrapper">
                    <input 
                      type="text" 
                      v-model="item.notes" 
                      @blur="saveNotes(index)"
                      @keyup.enter="saveNotes(index)"
                      @keyup.esc="cancelNotes(index)"
                      class="notes-input"
                      placeholder="Enter notes..."
                      ref="notesInput"
                    />
                  </div>
                  <div v-else-if="item.notes" class="notes-display">
                    <span class="notes-label">Notes:</span>
                    <span class="notes-text">{{ item.notes }}</span>
                    <button class="edit-notes-btn" @click="editNotes(index)">✏️</button>
                  </div>
                  <button v-else class="notes-btn" @click="editNotes(index)">Add Notes</button>
                </div>
              </div>
              <div class="item-controls">
                <div class="quantity-controls">
                  <button class="qty-btn" @click="decreaseQuantity(index)">-</button>
                  <span class="qty-value">{{ item.quantity }}</span>
                  <button class="qty-btn" @click="increaseQuantity(index)">+</button>
                </div>
                <button class="delete-btn" @click="removeItem(index)">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" stroke="currentColor" stroke-width="2"/>
                  </svg>
                </button>
              </div>
            </div>
            <div v-if="orderItems.length === 0" class="empty-order">
              <p>No items in order</p>
            </div>
          </div>
        </div>

        <!-- Order Totals -->
        <div class="order-totals">
          <div class="total-row">
            <span>Sub total:</span>
            <span>RM {{ subtotal.toFixed(2) }}</span>
          </div>
          <div class="total-row">
            <span>{{ feeLabel }}</span>
            <span>RM {{ serviceFee.toFixed(2) }}</span>
          </div>
          <div class="total-row final-total">
            <span>Total:</span>
            <span>RM {{ total.toFixed(2) }}</span>
          </div>
        </div>

        <!-- Action Buttons -->
        <div class="action-buttons">
          <button 
            class="action-btn payment-btn" 
            @click="processPayment('online')"
            :disabled="orderItems.length === 0 || !selectedDining || isProcessingPayment"
          >
            {{ isProcessingPayment ? 'Processing...' : 'Make Payment by Online' }}
          </button>
          <button 
            class="action-btn payment-btn" 
            @click="processPayment('counter')"
            :disabled="orderItems.length === 0 || !selectedDining || isProcessingPayment"
          >
            {{ isProcessingPayment ? 'Processing...' : 'Pay At Counter' }}
          </button>
        </div>
      </div>
    </div>
    <!-- Mobile Basket Button -->
    <button class="mobile-basket-btn" @click="showOrderSummary = true" v-if="orderItems.length > 0 && !showOrderSummary">
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" stroke="currentColor" stroke-width="2"/>
        <line x1="3" y1="6" x2="21" y2="6" stroke="currentColor" stroke-width="2"/>
        <path d="M16 10a4 4 0 0 1-8 0" stroke="currentColor" stroke-width="2"/>
      </svg>
      <span class="basket-count">{{ orderItems.length }}</span>
      <span class="basket-text">View Order</span>
      <span class="basket-total">RM {{ total.toFixed(2) }}</span>
    </button>
  </div>

  <!-- Success Notification (moved outside main container so it always overlays the screen) -->
  <div v-if="showSuccessNotification" class="success-notification-overlay" @click.self="closeSuccessNotification">
    <div class="success-notification-card">
      <div class="success-icon">
        <svg width="64" height="64" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <circle cx="12" cy="12" r="10" stroke="#10B981" stroke-width="2"/>
          <path d="M8 12l2 2 4-4" stroke="#10B981" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
      <h2 class="success-title">Order Successful!</h2>
      <p class="success-message">Your order has been placed successfully.</p>
      <div class="success-details" v-if="savedOrder">
        <p><strong>Order Number:</strong> #{{ savedOrder.orderNumber || '5487' }}</p>
        <p><strong>Payment Method:</strong> {{ savedOrder.paymentMethod === 'online' ? 'Online Payment' : 'Pay At Counter' }}</p>
        <p><strong>Total Amount:</strong> RM {{ (savedOrder.total || 0).toFixed(2) }}</p>
      </div>
      <button class="success-close-btn" @click="closeSuccessNotification">Close</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MenuOrder',
  data() {
    return {
      selectedCategory: 0,
      categories: ['Show All'],
      allProducts: [], // Store all products
      loadingProducts: false,
      orderItems: [],
      orderNumber: Math.floor(Math.random() * 9000) + 1000, // Random number between 1000-9999
      searchQuery: '',
      selectedDining: '',
      selectedTable: '',
      editingNotesIndex: null,
      showOrderSummary: false,
      showSuccessNotification: false,
      isProcessingPayment: false,
      paymentMethod: '',
      paymentError: null,
      savedOrder: null, // Store the order data from backend response
      isMobileMenuOpen: false,
      isUserMenuOpen: false,
      currentUser: null,
      scrollY: 0 // To store scroll position
    }
  },
  mounted() {
    this.loadMenuItems()
    this.checkLoginStatus()
    document.addEventListener('click', this.handleClickOutside)
    document.addEventListener('click', this.handleClickOutsideForMenu)
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside)
    document.removeEventListener('click', this.handleClickOutsideForMenu)
    // Ensure body scroll is restored when component is destroyed
    this.restoreBodyScroll()
  },
  watch: {
    selectedDining(newValue) {
      if (newValue === 'pickup') {
        this.selectedTable = ''
      }
    },
    showSuccessNotification(newVal) {
      if (newVal) {
        // Prevent body scroll when notification is open
        this.disableBodyScroll()
      } else {
        // Restore body scroll when notification is closed
        this.restoreBodyScroll()
      }
    },
    showOrderSummary(newVal) {
      // On mobile, ensure the order panel always opens scrolled to the top
      if (newVal) {
        this.$nextTick(() => {
          if (typeof window !== 'undefined') {
            window.scrollTo({ top: 0, behavior: 'auto' })
          }
          const panel = this.$el && this.$el.querySelector('.order-summary')
          if (panel) {
            panel.scrollTop = 0
          }
        })
      }
    }
  },
  computed: {
    subtotal() {
      return this.orderItems.reduce((sum, item) => sum + (item.price * item.quantity), 0)
    },
    serviceFee() {
      if (this.selectedDining === 'dine-in') {
        return 3.5
      } else if (this.selectedDining === 'pickup') {
        return 2.0
      }
      return 0
    },
    feeLabel() {
      if (this.selectedDining === 'pickup') {
        return 'Packaging Fee:'
      }
      return 'Service Fee:'
    },
    total() {
      return this.subtotal + this.serviceFee
    },
    filteredProducts() {
      // Ensure allProducts is always an array
      if (!Array.isArray(this.allProducts)) {
        return []
      }
      
      let filtered = this.allProducts

      // Filter by category - ensure categories array exists and selectedCategory is valid
      if (Array.isArray(this.categories) && 
          this.selectedCategory > 0 && 
          this.selectedCategory < this.categories.length &&
          this.categories[this.selectedCategory] !== 'Show All') {
        const selectedCat = this.categories[this.selectedCategory]
        filtered = filtered.filter(product => product && product.category === selectedCat)
      }

      // Filter by search query
      if (this.searchQuery && typeof this.searchQuery === 'string') {
        const query = this.searchQuery.toLowerCase()
        filtered = filtered.filter(product => 
          product && (
            product.name?.toLowerCase().includes(query) ||
            product.description?.toLowerCase().includes(query)
          )
        )
      }

      return filtered
    },
    isLoggedIn() {
      return !!this.currentUser && !!localStorage.getItem('token')
    }
  },
  methods: {
    disableBodyScroll() {
      // Store current scroll position
      this.scrollY = window.scrollY
      
      // Prevent scrolling
      document.body.style.overflow = 'hidden'
      document.body.style.position = 'fixed'
      document.body.style.top = `-${this.scrollY}px`
      document.body.style.width = '100%'
      document.body.style.height = '100%'
    },
    
    restoreBodyScroll() {
      // Restore scrolling
      document.body.style.overflow = ''
      document.body.style.position = ''
      document.body.style.top = ''
      document.body.style.width = ''
      document.body.style.height = ''
      
      // Restore scroll position
      if (this.scrollY !== 0) {
        window.scrollTo(0, this.scrollY)
      }
      this.scrollY = 0
    },
    
    async loadMenuItems() {
      this.loadingProducts = true
      try {
        const response = await fetch('http://localhost:8080/api/menu')
        if (response.ok) {
          const menus = await response.json()
          // Map menu items to products format
          this.allProducts = menus.map(menu => ({
            id: menu.id,
            name: menu.name,
            description: menu.description,
            price: parseFloat(menu.price),
            category: menu.category,
            image: menu.imageUrl ? `http://localhost:8080${menu.imageUrl}` : null,
            available: menu.available
          }))

          // Extract unique categories
          const uniqueCategories = ['Show All', ...new Set(this.allProducts.map(p => p.category).filter(Boolean))]
          this.categories = uniqueCategories

          // Products are now computed from filteredProducts
        } else {
          console.error('Failed to load menu items')
        }
      } catch (error) {
        console.error('Error loading menu items:', error)
      } finally {
        this.loadingProducts = false
      }
    },
    addToOrder(product) {
      const existingItem = this.orderItems.find(item => item.id === product.id)
      if (existingItem) {
        existingItem.quantity++
      } else {
        this.orderItems.push({
          id: product.id,
          name: product.name,
          price: product.price,
          quantity: 1
        })
      }
    },
    increaseQuantity(index) {
      this.orderItems[index].quantity++
    },
    decreaseQuantity(index) {
      if (this.orderItems[index].quantity > 1) {
        this.orderItems[index].quantity--
      } else {
        this.removeItem(index)
      }
    },
    removeItem(index) {
      this.orderItems.splice(index, 1)
    },
    editNotes(index) {
      this.editingNotesIndex = index
      this.$nextTick(() => {
        const inputs = this.$refs.notesInput
        if (inputs) {
          const input = Array.isArray(inputs) ? inputs[index] : inputs
          if (input) {
            input.focus()
          }
        }
      })
    },
    saveNotes(index) {
      this.editingNotesIndex = null
      // Notes are already saved via v-model
    },
    cancelNotes(index) {
      // If notes were empty, remove the notes property
      if (!this.orderItems[index].notes || this.orderItems[index].notes.trim() === '') {
        this.$delete(this.orderItems[index], 'notes')
      }
      this.editingNotesIndex = null
    },
    capitalizeFirst(str) {
      if (!str) return ''
      return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase()
    },
    async processPayment(method) {
      // Validate order
      if (this.orderItems.length === 0) {
        alert('Please add items to your order')
        return
      }

      if (!this.selectedDining) {
        alert('Please select dining option (Dine In or Pickup)')
        return
      }

      if (this.selectedDining === 'dine-in' && !this.selectedTable) {
        alert('Please select a table number')
        return
      }

      this.isProcessingPayment = true
      this.paymentError = null
      this.paymentMethod = method

      try {
        const token = localStorage.getItem('token')
        if (!token) {
          alert('Please login to place an order')
          this.$emit('show-login')
          return
        }

        // Prepare order data
        const orderData = {
          orderType: this.selectedDining,
          tableNumber: this.selectedDining === 'dine-in' ? parseInt(this.selectedTable) : null,
          items: this.orderItems.map(item => ({
            menuItemId: item.id,
            name: item.name,
            price: item.price,
            quantity: item.quantity,
            notes: item.notes || null
          })),
          subtotal: this.subtotal,
          serviceFee: this.serviceFee,
          total: this.total,
          paymentMethod: method,
          orderNumber: String(this.orderNumber)
        }

        console.log('Sending order data:', orderData) // Debug log

        const response = await fetch('http://localhost:8080/api/orders', {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(orderData)
        })

        console.log('Response status:', response.status) // Debug log

        if (!response.ok) {
          if (response.status === 401) {
            alert('Session expired. Please login again.')
            localStorage.removeItem('token')
            localStorage.removeItem('user')
            this.$emit('show-login')
            return
          }

          let errorMessage = 'Failed to place order'
          try {
            const errorData = await response.json()
            errorMessage = errorData.error || errorData.message || errorMessage
          } catch (e) {
            // If response is not JSON, get text
            const text = await response.text()
            if (text) errorMessage = text
          }

          this.paymentError = errorMessage

          // For "Pay At Counter", still show the success popup UI
          // even if the backend returns a non-2xx status, so that
          // the user always sees the confirmation message.
          if (method === 'counter') {
            this.savedOrder = {
              orderNumber: this.orderNumber.toString(),
              paymentMethod: method,
              total: this.total,
              subtotal: this.subtotal,
              serviceFee: this.serviceFee,
              items: orderData.items
            }
            this.showSuccessNotification = true
            this.orderItems = []
            this.selectedDining = ''
            this.selectedTable = ''
            this.orderNumber = Math.floor(Math.random() * 9000) + 1000
          } else {
            // Keep the existing behaviour (alert only) for online payment
            alert(this.paymentError)
          }

          return
        }

        // Get the saved order from backend response
        let savedOrderData = null
        try {
          savedOrderData = await response.json()
          console.log('Saved order data:', savedOrderData) // Debug log
        } catch (e) {
          console.error('Error parsing response JSON:', e)
          // If no JSON response, create a mock response for testing
          savedOrderData = {
            orderNumber: this.orderNumber.toString(),
            paymentMethod: method,
            total: this.total,
            subtotal: this.subtotal,
            serviceFee: this.serviceFee,
            items: orderData.items
          }
        }
        
        // Store the saved order data (keep this static until notification is closed)
        this.savedOrder = savedOrderData
        
        // Show success notification and make sure it is visible without scrolling
        this.showSuccessNotification = true
        this.$nextTick(() => {
          if (typeof window !== 'undefined') {
            window.scrollTo({ top: 0, behavior: 'auto' })
          }
        })
        
        // Reset order form immediately (but keep savedOrder for notification display)
        this.orderItems = []
        this.selectedDining = ''
        this.selectedTable = ''
        this.orderNumber = Math.floor(Math.random() * 9000) + 1000

      } catch (error) {
        this.paymentError = 'Error placing order: ' + error.message
        alert(this.paymentError)
        console.error('Error placing order:', error)
        
        // For testing: Show notification even if API fails
        this.savedOrder = {
          orderNumber: this.orderNumber.toString(),
          paymentMethod: method,
          total: this.total,
          subtotal: this.subtotal,
          serviceFee: this.serviceFee
        }
        this.showSuccessNotification = true
        this.$nextTick(() => {
          if (typeof window !== 'undefined') {
            window.scrollTo({ top: 0, behavior: 'auto' })
          }
        })
        this.orderItems = []
        this.selectedDining = ''
        this.selectedTable = ''
        this.orderNumber = Math.floor(Math.random() * 9000) + 1000
      } finally {
        this.isProcessingPayment = false
      }
    },
    closeSuccessNotification() {
      this.showSuccessNotification = false
      this.paymentMethod = ''
      // Clear saved order only when notification is closed
      this.savedOrder = null
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
    getUserInitial() {
      if (this.currentUser) {
        const name = this.currentUser.username || this.currentUser.email || 'User'
        return name.charAt(0).toUpperCase()
      }
      return 'U'
    },
    handleClickOutside(event) {
      // This handler is kept for consistency with TableReservation.vue
      // MenuOrder doesn't have calendar/time picker, but keeping structure consistent
    },
    handleClickOutsideForMenu(event) {
      const userMenuContainer = event.target.closest('.user-menu-container')
      if (!userMenuContainer && this.isUserMenuOpen) {
        this.isUserMenuOpen = false
      }
    }
  }
}
</script>

<style scoped>
.menu-order-page {
  min-height: 100vh;
  overflow: hidden;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 20% 80%, rgba(139, 92, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 159, 28, 0.03) 0%, transparent 50%);
  background-attachment: fixed;
  color: #ffffff;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  position: relative;
  display: flex;
  flex-direction: column;
  perspective: 1000px;
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

.menu-order-container {
  display: flex;
  gap: 20px;
  max-width: 1600px;
  margin: 0 auto;
  padding: 20px;
  flex: 1;
  min-height: 0;
  width: 100%;
  height: calc(100vh - 80px); /* Subtract header height */
  overflow: hidden;
}

/* Left Column - Product Catalog */
.product-catalog {
  flex: 1;
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 20px;
  padding: 25px;
  overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.08);
  transform-style: preserve-3d;
}

.catalog-header {
  margin-bottom: 20px;
}

.search-box {
  position: relative;
  margin-bottom: 15px;
}

.search-icon {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #999;
  z-index: 1;
}

.search-input {
  width: 100%;
  padding: 12px 15px 12px 45px;
  border: 1px solid #444;
  border-radius: 8px;
  font-size: 14px;
  outline: none;
  background-color: #1a1a1a;
  color: #ffffff;
}

.search-input:focus {
  border-color: rgba(139, 92, 246, 0.5);
  background: rgba(255, 255, 255, 0.06);
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.search-input::placeholder {
  color: #999;
}

.filter-dropdowns {
  display: flex;
  gap: 10px;
}

.filter-select {
  flex: 1;
  padding: 10px 15px;
  border: 1px solid #444;
  border-radius: 8px;
  font-size: 14px;
  background-color: #1a1a1a;
  color: #ffffff;
  cursor: pointer;
  outline: none;
  transition: all 0.3s;
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23ffffff' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 12px center;
  padding-right: 35px;
}

.filter-select option {
  background-color: #1a1a1a !important;
  color: #ffffff !important;
  padding: 10px 15px !important;
}

.filter-select:hover option {
  background-color: #2a2a2a !important;
}

.filter-select option:checked,
.filter-select option:hover {
  background-color: rgba(139, 92, 246, 0.3) !important;
  color: #ffffff !important;
}

.filter-select:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  background-color: #0f0f0f;
}

.filter-select:focus {
  border-color: rgba(139, 92, 246, 0.5);
  background-color: rgba(255, 255, 255, 0.06);
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23ffffff' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 12px center;
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.category-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  flex-wrap: wrap;
  border-bottom: 2px solid rgba(255, 255, 255, 0.1);
  padding-bottom: 10px;
}

.category-tab {
  background: transparent;
  border: none;
  padding: 10px 20px;
  font-size: 14px;
  font-weight: 500;
  color: #ccc;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  margin-bottom: -12px;
  transition: all 0.3s;
}

.category-tab:hover {
  color: #8B5CF6;
}

.category-tab.active {
  color: #8B5CF6;
  border-bottom-color: #8B5CF6;
  font-weight: 600;
}

.loading-products,
.no-products {
  text-align: center;
  padding: 3rem;
  color: #ccc;
  font-size: 16px;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
}

.product-card {
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 
    0 8px 25px rgba(0, 0, 0, 0.3),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  transform-style: preserve-3d;
}

.product-card:hover {
  box-shadow: 
    0 20px 50px rgba(139, 92, 246, 0.3),
    0 0 40px rgba(139, 92, 246, 0.2),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
  transform: translateY(-10px) translateZ(30px) rotateX(5deg);
  border-color: rgba(139, 92, 246, 0.4);
}

.product-image {
  position: relative;
  width: 100%;
  height: 180px;
  background-color: #2a2a2a;
  overflow: hidden;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.add-btn {
  position: absolute;
  bottom: 10px;
  right: 10px;
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #FF9F1C 0%, #FF8C00 100%);
  border: none;
  border-radius: 50%;
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(255, 159, 28, 0.3);
  transition: all 0.3s;
}

.add-btn:hover {
  background: linear-gradient(135deg, #FFB84D 0%, #FF9F1C 100%);
  transform: scale(1.1);
  box-shadow: 0 4px 12px rgba(255, 159, 28, 0.4);
}

.product-info {
  padding: 15px;
}

.product-name {
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 8px;
}

.product-description {
  font-size: 12px;
  color: #999;
  margin-bottom: 8px;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.product-price {
  font-size: 18px;
  font-weight: 700;
  color: #FF9F1C;
}

.no-image-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
  font-size: 14px;
  background-color: #2a2a2a;
}

/* Right Column - Order Summary */
.order-summary {
  width: 400px;
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 20px;
  padding: 25px;
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden;
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.08);
  transform-style: preserve-3d;
}

/* Dining Options - IMPROVED VISIBILITY */
.order-header {
  margin-bottom: 20px;
  padding: 15px;
  background: rgba(18, 18, 18, 0.6);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.dining-options {
  width: 100%;
}

.dining-option-label {
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 12px;
  display: flex;
  align-items: center;
  gap: 8px;
}

.dining-option-label::before {
  content: "🍽️";
  font-size: 18px;
}

.order-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 2px solid #444;
}

.bag-icon {
  color: #8B5CF6;
}

/* Order Items Container - FIXED FOR MOBILE */
.order-items-container {
  flex: 1;
  min-height: 0;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.order-items {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  margin-bottom: 20px;
  min-height: 0;
  padding-right: 5px;
}

/* Custom scrollbar for order items */
.order-items::-webkit-scrollbar {
  width: 6px;
}

.order-items::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
}

.order-items::-webkit-scrollbar-thumb {
  background: rgba(139, 92, 246, 0.5);
  border-radius: 3px;
}

.order-items::-webkit-scrollbar-thumb:hover {
  background: rgba(139, 92, 246, 0.7);
}

.empty-order {
  text-align: center;
  padding: 40px 20px;
  color: #999;
}

.order-item {
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-radius: 12px;
  padding: 18px;
  margin-bottom: 15px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: 
    0 4px 15px rgba(0, 0, 0, 0.2),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.order-item:hover {
  transform: translateX(5px) translateZ(10px);
  box-shadow: 
    0 8px 25px rgba(0, 0, 0, 0.3),
    0 0 20px rgba(139, 92, 246, 0.15),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
  border-color: rgba(139, 92, 246, 0.3);
}

.item-info {
  margin-bottom: 12px;
}

.item-name {
  font-size: 16px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 5px;
}

.item-price {
  font-size: 14px;
  color: #ccc;
  margin-bottom: 8px;
}

.item-notes-section {
  margin-top: 8px;
}

.notes-input-wrapper {
  margin-top: 8px;
}

.notes-input {
  width: 100%;
  padding: 8px 12px;
  background-color: #1a1a1a;
  border: 1px solid #444;
  border-radius: 6px;
  font-size: 13px;
  color: #ffffff;
  outline: none;
  transition: all 0.3s;
}

.notes-input:focus {
  border-color: rgba(139, 92, 246, 0.5);
  background: rgba(255, 255, 255, 0.06);
  box-shadow: 0 0 0 3px rgba(139, 92, 246, 0.1);
}

.notes-input::placeholder {
  color: #999;
}

.notes-display {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 8px;
  padding: 6px 10px;
  background-color: rgba(139, 92, 246, 0.1);
  border: 1px solid rgba(139, 92, 246, 0.2);
  border-radius: 6px;
  font-size: 13px;
}

.notes-label {
  color: rgba(255, 255, 255, 0.7);
  font-weight: 500;
}

.notes-text {
  color: #ffffff;
  flex: 1;
  word-break: break-word;
  max-width: 150px;
}

.edit-notes-btn {
  background: transparent;
  border: none;
  color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
  padding: 2px 4px;
  font-size: 12px;
  transition: color 0.3s;
}

.edit-notes-btn:hover {
  color: #8B5CF6;
}

.item-controls {
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: space-between;
  margin-top: 12px;
}

.quantity-controls {
  display: flex;
  align-items: center;
  gap: 10px;
  background-color: #1a1a1a;
  border: 1px solid #444;
  border-radius: 6px;
  padding: 5px 10px;
}

.qty-btn {
  background: transparent;
  border: none;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #ccc;
  font-size: 18px;
  font-weight: 600;
  transition: color 0.3s;
}

.qty-btn:hover {
  color: #8B5CF6;
}

.qty-value {
  min-width: 30px;
  text-align: center;
  font-weight: 600;
  color: #ffffff;
}

.notes-btn {
  padding: 6px 12px;
  background-color: #1a1a1a;
  border: 1px solid #444;
  border-radius: 6px;
  font-size: 12px;
  color: #ccc;
  cursor: pointer;
  transition: all 0.3s;
  margin-top: 8px;
  display: inline-block;
}

.notes-btn:hover {
  border-color: #8B5CF6;
  color: #8B5CF6;
  background-color: rgba(139, 92, 246, 0.1);
}

.delete-btn {
  background: transparent;
  border: none;
  color: #FF6B6B;
  cursor: pointer;
  padding: 5px;
  transition: color 0.3s;
}

.delete-btn:hover {
  color: #FF5252;
}

.order-totals {
  border-top: 2px solid #444;
  padding-top: 15px;
  margin-bottom: 20px;
  flex-shrink: 0;
}

.total-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  font-size: 14px;
  color: #ccc;
}

.total-row.editable {
  cursor: pointer;
}

.editable-value {
  display: flex;
  align-items: center;
  gap: 8px;
}

.edit-btn {
  background: transparent;
  border: none;
  color: #999;
  cursor: pointer;
  padding: 2px;
  display: flex;
  align-items: center;
  transition: color 0.3s;
}

.edit-btn:hover {
  color: #FF9F1C;
}

.final-total {
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
  margin-top: 15px;
  padding-top: 15px;
  border-top: 2px solid #444;
}

.action-buttons {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  flex-shrink: 0;
}

.action-btn {
  padding: 12px 20px;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s;
}

.kot-btn {
  background-color: #2a2a2a;
  color: #fff;
  border: 1px solid #444;
}

.kot-btn:hover {
  background-color: #1a1a1a;
  border-color: #8B5CF6;
}

.draft-btn {
  background-color: #1a1a1a;
  color: #ccc;
  border: 1px solid #444;
}

.draft-btn:hover {
  border-color: #8B5CF6;
  color: #8B5CF6;
}

.payment-btn {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: #ffffff;
}

.payment-btn:hover:not(:disabled) {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(139, 92, 246, 0.3);
}

.payment-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

/* Success Notification - FIXED */
.success-notification-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
  animation: fadeIn 0.2s ease;
  padding: 20px;
  overflow-y: auto;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.success-notification-card {
  background: rgba(18, 18, 18, 0.95);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 16px;
  padding: 2.5rem;
  max-width: 500px;
  width: 90%;
  max-height: 90vh;
  overflow-y: auto;
  text-align: center;
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  animation: slideUp 0.3s ease;
  margin: auto;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.success-icon {
  margin-bottom: 1.5rem;
  display: flex;
  justify-content: center;
}

.success-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #ffffff;
  margin: 0 0 1rem 0;
}

.success-message {
  font-size: 1rem;
  color: rgba(255, 255, 255, 0.8);
  margin: 0 0 2rem 0;
}

.success-details {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 2rem;
  text-align: left;
}

.success-details p {
  margin: 0.75rem 0;
  color: rgba(255, 255, 255, 0.9);
  font-size: 0.95rem;
}

.success-details strong {
  color: #ffffff;
  font-weight: 600;
}

.success-close-btn {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: white;
  border: none;
  padding: 0.875rem 2rem;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  font-size: 1rem;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.3);
  width: 100%;
}

.success-close-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(139, 92, 246, 0.4);
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
}

.print-btn {
  background: linear-gradient(135deg, #FF9F1C 0%, #FF8C00 100%);
  color: #ffffff;
}

.print-btn:hover {
  background: linear-gradient(135deg, #FFB84D 0%, #FF9F1C 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 159, 28, 0.3);
}

/* Mobile Basket Button */
.mobile-basket-btn {
  display: none;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  width: 100%;
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: #ffffff;
  border: none;
  border-radius: 0;
  padding: 16px 20px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  box-shadow: 0 -4px 20px rgba(139, 92, 246, 0.4);
  z-index: 1000;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  transition: all 0.3s ease;
  max-width: 100%;
}

.mobile-basket-btn:active {
  opacity: 0.9;
}

.basket-count {
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 700;
}

.basket-text {
  flex: 1;
  text-align: left;
}

.basket-total {
  font-weight: 700;
  font-size: 18px;
  margin-left: auto;
}

.mobile-back-btn {
  display: none;
  position: absolute;
  top: 15px;
  left: 15px;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  padding: 10px 16px;
  color: #ffffff;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  gap: 8px;
  z-index: 1001;
  transition: all 0.3s ease;
  font-size: 14px;
  font-weight: 500;
}

.mobile-back-btn:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateX(-3px);
}

.mobile-back-btn svg {
  width: 20px;
  height: 20px;
}

/* Responsive - MOBILE FIXES */
@media (max-width: 1200px) {
  .menu-order-container {
    flex-direction: column;
    height: calc(100vh - 80px);
  }

  .order-summary {
    width: 100%;
    height: auto;
    max-height: 50vh;
  }

  .product-catalog {
    flex: 1;
    min-height: 0;
  }

  .products-grid {
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
  }
}

@media (max-width: 768px) {
  .menu-order-page {
    padding-bottom: 80px; /* Space for basket button at bottom */
    overflow-x: hidden;
  }

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

  .menu-order-container {
    position: relative;
    padding: 15px;
    height: calc(100vh - 140px);
    min-height: 500px;
    overflow: hidden;
  }

  .product-catalog {
    width: 100%;
    height: 100%;
    padding: 15px;
    transition: opacity 0.3s ease, transform 0.3s ease;
    display: flex;
    flex-direction: column;
  }

  .product-catalog.hide-on-mobile {
    display: none;
  }

  /* Fix for category tabs on mobile */
  .category-tabs {
    overflow-x: auto;
    flex-wrap: nowrap;
    white-space: nowrap;
    padding-bottom: 10px;
    margin-bottom: 15px;
    -webkit-overflow-scrolling: touch;
    scrollbar-width: none; /* Firefox */
  }
  
  .category-tabs::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Edge */
  }
  
  .category-tab {
    flex-shrink: 0;
    padding: 8px 15px;
    font-size: 13px;
  }

  .products-grid {
    grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
    gap: 12px;
    flex: 1;
    overflow-y: auto;
    padding-right: 5px;
  }

  .product-card {
    margin-bottom: 0;
  }

  .product-image {
    height: 140px;
  }

  .product-name {
    font-size: 14px;
    margin-bottom: 5px;
  }

  .product-description {
    font-size: 11px;
    margin-bottom: 6px;
    -webkit-line-clamp: 2;
    line-clamp: 2;
  }

  .product-price {
    font-size: 16px;
  }

  /* Order Summary Mobile Layout - COMPLETELY REDESIGNED */
  .order-summary {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    height: 100vh;
    z-index: 999;
    border-radius: 0;
    overflow-y: auto;
    padding: 60px 15px 80px 15px; /* Reduced padding to use more space */
    flex-direction: column;
    background: rgba(18, 18, 18, 0.95);
  }

  .order-summary.show-order {
    display: flex;
  }

  .mobile-back-btn {
    display: flex !important;
    position: fixed;
    top: 20px;
    left: 15px;
    z-index: 1002;
    background: rgba(18, 18, 18, 0.9);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(139, 92, 246, 0.3);
    padding: 8px 12px;
    font-size: 13px;
  }

  /* Dining Options - MADE MORE VISIBLE ON MOBILE */
  .order-header {
    margin-top: 0;
    margin-bottom: 15px;
    padding: 12px;
    background: rgba(139, 92, 246, 0.1);
    border: 1px solid rgba(139, 92, 246, 0.3);
    border-radius: 10px;
  }

  .dining-options {
    width: 100%;
  }

  .dining-option-label {
    font-size: 15px;
    font-weight: 600;
    color: #ffffff;
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .dining-option-label::before {
    content: "🍽️";
    font-size: 16px;
  }

  .filter-dropdowns {
    flex-direction: column;
    gap: 10px;
    width: 100%;
  }

  .filter-select {
    width: 100%;
    padding: 12px 15px;
    font-size: 14px;
    background-color: rgba(26, 26, 26, 0.8);
    border: 1px solid rgba(139, 92, 246, 0.5);
  }

  .order-title {
    font-size: 16px;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  }

  /* Order Items Container - Optimized for mobile space */
  .order-items-container {
    flex: 1;
    min-height: 200px;
    margin-bottom: 10px;
    max-height: 40vh; /* Limit height to use available space */
  }

  .order-items {
    flex: 1;
    overflow-y: auto;
    padding-right: 5px;
    max-height: 100%;
  }

  .order-item {
    padding: 12px;
    margin-bottom: 10px;
  }

  .item-name {
    font-size: 14px;
  }

  .item-price {
    font-size: 12px;
  }

  .item-controls {
    margin-top: 8px;
  }

  .quantity-controls {
    padding: 3px 8px;
  }

  .qty-btn {
    width: 20px;
    height: 20px;
    font-size: 14px;
  }

  .qty-value {
    min-width: 20px;
    font-size: 13px;
  }

  .notes-display {
    padding: 4px 6px;
    font-size: 11px;
  }

  .notes-text {
    max-width: 100px;
    font-size: 11px;
  }

  .notes-btn {
    padding: 4px 8px;
    font-size: 11px;
    margin-top: 4px;
  }

  /* Order Totals - Reduced size */
  .order-totals {
    padding-top: 10px;
    margin-bottom: 10px;
    border-top: 1px solid rgba(255, 255, 255, 0.2);
  }

  .total-row {
    font-size: 13px;
    margin-bottom: 8px;
  }

  .final-total {
    font-size: 16px;
    margin-top: 8px;
    padding-top: 8px;
    border-top: 1px solid rgba(255, 255, 255, 0.3);
  }

  /* Action Buttons - Made more compact */
  .action-buttons {
    grid-template-columns: 1fr;
    gap: 8px;
    margin-bottom: 15px;
  }

  .action-btn {
    padding: 12px 16px;
    font-size: 14px;
    font-weight: 600;
    width: 100%;
  }

  .mobile-basket-btn {
    display: flex !important;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 12px 16px;
    font-size: 14px;
    z-index: 998;
  }

  .basket-count {
    width: 24px;
    height: 24px;
    font-size: 12px;
  }

  .basket-text {
    font-size: 13px;
  }

  .basket-total {
    font-size: 15px;
  }
  
  /* Mobile success notification */
  .success-notification-card {
    padding: 1.5rem;
    margin: auto 15px;
    max-width: 100%;
    max-height: 80vh;
  }
  
  .success-title {
    font-size: 1.3rem;
  }
  
  .success-message {
    font-size: 0.9rem;
    margin-bottom: 1.2rem;
  }
  
  .success-details {
    padding: 0.8rem;
    margin-bottom: 1.2rem;
  }
  
  .success-details p {
    font-size: 0.85rem;
    margin: 0.4rem 0;
  }
  
  .success-close-btn {
    padding: 0.7rem 1.2rem;
    font-size: 0.9rem;
  }
}

/* Desktop - Keep layout unchanged */
@media (min-width: 769px) {
  .mobile-basket-btn {
    display: none !important;
  }

  .mobile-back-btn {
    display: none !important;
  }

  .order-summary {
    display: flex !important;
  }

  .product-catalog.hide-on-mobile {
    display: block !important;
  }
}
</style>