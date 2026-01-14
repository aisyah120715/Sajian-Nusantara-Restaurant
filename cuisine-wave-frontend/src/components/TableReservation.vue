<template>
  <div class="table-reservation-page" @click="handleClickOutside">
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
    <div class="main-container">
      <!-- Left Side - Reservation Form -->
      <div class="reservation-section">
        <div class="section-card">
          <div class="section-title">
            <h2><span class="title-accent">01</span> Date & Time</h2>
            <p class="section-subtitle">Select your preferred reservation details</p>
          </div>

          <div class="form-container">
            <div class="form-group">
              <label class="form-label">Reservation Date</label>
              <div class="input-wrapper">
                <div class="input-icon">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <rect x="3" y="4" width="18" height="18" rx="2" stroke="currentColor" stroke-width="1.5"/>
                    <path d="M3 10h18" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
                    <path d="M8 2v4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
                    <path d="M16 2v4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
                  </svg>
                </div>
                <input 
                  type="text" 
                  :value="formattedDisplayDate || 'Select date'"
                  class="form-input"
                  @click="showCalendar = !showCalendar"
                  readonly
                  placeholder="Choose a date"
                />
                <button type="button" class="calendar-toggle" @click="showCalendar = !showCalendar">
                  <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M6 9l6 6 6-6" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  </svg>
                </button>
              </div>
              
              <!-- Calendar Popup -->
              <div v-if="showCalendar" class="calendar-popup" @click.stop>
                <div class="calendar-header">
                  <button type="button" class="calendar-nav-btn" @click="previousMonth">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M15 6l-6 6 6 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                  <span class="calendar-month-year">{{ currentMonthName }} {{ currentYear }}</span>
                  <button type="button" class="calendar-nav-btn" @click="nextMonth">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M9 6l6 6-6 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                </div>
                <div class="calendar-weekdays">
                  <div v-for="day in weekDays" :key="day" class="calendar-weekday">{{ day }}</div>
                </div>
                <div class="calendar-days">
                  <div 
                    v-for="day in calendarDays" 
                    :key="day.date"
                    class="calendar-day"
                    :class="{
                      'other-month': day.otherMonth,
                      'today': day.isToday,
                      'selected': day.isSelected,
                      'disabled': day.disabled
                    }"
                    @click="selectCalendarDate(day)"
                  >
                    {{ day.day }}
                  </div>
                </div>
              </div>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label class="form-label">Time</label>
                <div class="input-wrapper">
                  <div class="input-icon">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <circle cx="12" cy="12" r="9" stroke="currentColor" stroke-width="1.5"/>
                      <path d="M12 7v5l3 2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
                    </svg>
                  </div>
                  <input 
                    type="text" 
                    v-model="displayTime" 
                    class="form-input"
                    @click="showTimePicker = !showTimePicker"
                    readonly
                    placeholder="Select time"
                  />
                  <button type="button" class="calendar-toggle" @click="showTimePicker = !showTimePicker">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M6 9l6 6 6-6" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </button>
                </div>
                
                <!-- Time Picker Popup -->
                <div v-if="showTimePicker" class="time-picker-popup" @click.stop>
                  <div class="time-slots">
                    <button
                      v-for="timeSlot in timeSlots"
                      :key="timeSlot.value"
                      type="button"
                      class="time-slot-btn"
                      :class="{ 'selected': selectedTime === timeSlot.value }"
                      @click="selectTime(timeSlot.value)"
                    >
                      {{ timeSlot.label }}
                    </button>
                  </div>
                </div>
              </div>

              <div class="form-group">
                <label class="form-label">Guests</label>
                <div class="people-counter">
                  <button 
                    type="button" 
                    class="counter-btn" 
                    @click="decreasePeople"
                    :disabled="numberOfPeople <= 1"
                  >
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M5 12h14" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                  </button>
                  <div class="people-value">
                    <span>{{ numberOfPeople }}</span>
                    <small>person{{ numberOfPeople > 1 ? 's' : '' }}</small>
                  </div>
                  <button 
                    type="button" 
                    class="counter-btn" 
                    @click="increasePeople"
                    :disabled="numberOfPeople >= 10"
                  >
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M12 5v14M5 12h14" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                  </button>
                </div>
              </div>
            </div>

            <button 
              class="btn-check-availability" 
              @click="checkAvailability"
              :disabled="!selectedDate || !selectedTime || isChecking"
            >
              <span v-if="isChecking" class="spinner"></span>
              <span>{{ isChecking ? 'Checking Availability...' : 'Check Available Tables' }}</span>
              <svg v-if="!isChecking" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M5 12h14M12 5l7 7-7 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </button>
          </div>
        </div>

        <!-- Status Messages Container -->
        <div class="status-container">
          <div v-if="availabilityChecked && availableTables.length > 0" class="status-card success">
            <div class="status-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="12" r="9" stroke="#10B981" stroke-width="2"/>
                <path d="M8 12l3 3 5-5" stroke="#10B981" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </div>
            <div class="status-content">
              <h3>{{ availableTables.length }} Table{{ availableTables.length > 1 ? 's' : '' }} Available</h3>
              <p>For {{ numberOfPeople }} guest{{ numberOfPeople > 1 ? 's' : '' }} on {{ formattedDate }} at {{ formattedTime }}</p>
              <p class="instruction-text">Select a table below to confirm your reservation</p>
            </div>
          </div>

          <div v-if="availabilityChecked && availableTables.length === 0" class="status-card error">
            <div class="status-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="12" r="9" stroke="#EF4444" stroke-width="2"/>
                <path d="M8 8l8 8M8 16l8-8" stroke="#EF4444" stroke-width="2" stroke-linecap="round"/>
              </svg>
            </div>
            <div class="status-content">
              <h3>No Tables Available</h3>
              <p>Please try a different date, time, or party size</p>
            </div>
          </div>

          <div v-if="errorMessage" class="status-card error">
            <div class="status-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="12" r="10" stroke="#EF4444" stroke-width="2"/>
                <path d="M12 8v4M12 16h.01" stroke="#EF4444" stroke-width="2" stroke-linecap="round"/>
              </svg>
            </div>
            <div class="status-content">
              <h3>Something Went Wrong</h3>
              <p>{{ errorMessage }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Success Popup Modal -->
      <div v-if="successMessage" class="success-popup-overlay" @click="closeSuccessPopup">
        <div class="success-popup" @click.stop>
          <button class="success-popup-close" @click="closeSuccessPopup">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M18 6L6 18M6 6l12 12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
            </svg>
          </button>
          <div class="success-popup-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" stroke="#10B981" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M22 4L12 14.01l-3-3" stroke="#10B981" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </div>
          <div class="success-popup-content">
            <h3>Reservation Pending!</h3>
            <p>{{ successMessage }}</p>
          </div>
        </div>
      </div>

      <!-- Right Side - Tables Visualization -->
      <div class="tables-section">
        <div class="section-card">
          <div class="section-title">
            <h2><span class="title-accent">02</span> Select Table</h2>
            <p class="section-subtitle">Choose your preferred dining table</p>
          </div>

          <div class="restaurant-layout">
            <!-- Top Row - Tables 1-5 -->
            <div class="tables-row top-row">
              <div 
                v-for="table in tables.slice(0, 5)" 
                :key="table.number"
                class="table-item"
                :class="{
                  'available': table.available && availabilityChecked && allowedTables.includes(table.number),
                  'occupied': (!table.available || !allowedTables.includes(table.number)) && availabilityChecked,
                  'selected': selectedTable === table.number,
                  'circular': true
                }"
                @click="selectTable(table)"
              >
                <div 
                  class="table-surface"
                  :class="{
                    'available': table.available && availabilityChecked && allowedTables.includes(table.number),
                    'occupied': (!table.available || !allowedTables.includes(table.number)) && availabilityChecked
                  }"
                >
                  <span class="table-number">{{ table.number }}</span>
                  <div v-if="selectedTable === table.number" class="selected-indicator">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </div>
                </div>
                <div class="table-legs">
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                </div>
                <div class="table-status">
                  <span v-if="(!table.available || !allowedTables.includes(table.number)) && availabilityChecked" class="status-occupied">
                    {{ !allowedTables.includes(table.number) ? 'Not Available' : 'Occupied' }}
                  </span>
                  <span v-if="table.available && availabilityChecked && allowedTables.includes(table.number)" class="status-available">Available</span>
                </div>
              </div>
            </div>

            <!-- Middle Row - Tables 6-9 -->
            <div class="tables-row middle-row">
              <div class="table-item table-6">
                <div 
                  class="table-surface"
                  :class="{
                    'available': tables[5]?.available && availabilityChecked && allowedTables.includes(6),
                    'occupied': (!tables[5]?.available || !allowedTables.includes(6)) && availabilityChecked,
                    'selected': selectedTable === 6
                  }"
                  @click="selectTable(tables[5])"
                >
                  <span class="table-number">6</span>
                  <div v-if="selectedTable === 6" class="selected-indicator">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </div>
                </div>
                <div class="table-legs">
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                </div>
                <div class="table-status">
                  <span v-if="(!tables[5]?.available || !allowedTables.includes(6)) && availabilityChecked" class="status-occupied">
                    {{ !allowedTables.includes(6) ? 'Not Available' : 'Occupied' }}
                  </span>
                  <span v-if="tables[5]?.available && availabilityChecked && allowedTables.includes(6)" class="status-available">Available</span>
                </div>
              </div>

              <!-- Larger tables 7 & 8 -->
              <div class="large-tables-container">
                <div class="table-item large-table">
                  <div 
                    class="table-surface rectangular"
                    :class="{
                      'available': tables[6]?.available && availabilityChecked && allowedTables.includes(7),
                      'occupied': (!tables[6]?.available || !allowedTables.includes(7)) && availabilityChecked,
                      'selected': selectedTable === 7
                    }"
                    @click="selectTable(tables[6])"
                  >
                    <span class="table-number">7</span>
                    <div v-if="selectedTable === 7" class="selected-indicator">
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                      </svg>
                    </div>
                  </div>
                  <div class="table-legs">
                    <div class="table-leg"></div>
                    <div class="table-leg"></div>
                    <div class="table-leg"></div>
                    <div class="table-leg"></div>
                  </div>
                  <div class="table-status">
                    <span v-if="(!tables[6]?.available || !allowedTables.includes(7)) && availabilityChecked" class="status-occupied">
                      {{ !allowedTables.includes(7) ? 'Not Available' : 'Occupied' }}
                    </span>
                    <span v-if="tables[6]?.available && availabilityChecked && allowedTables.includes(7)" class="status-available">Available</span>
                  </div>
                </div>

                <div class="table-item large-table">
                  <div 
                    class="table-surface rectangular"
                    :class="{
                      'available': tables[7]?.available && availabilityChecked && allowedTables.includes(8),
                      'occupied': (!tables[7]?.available || !allowedTables.includes(8)) && availabilityChecked,
                      'selected': selectedTable === 8
                    }"
                    @click="selectTable(tables[7])"
                  >
                    <span class="table-number">8</span>
                    <div v-if="selectedTable === 8" class="selected-indicator">
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                      </svg>
                    </div>
                  </div>
                  <div class="table-legs">
                    <div class="table-leg"></div>
                    <div class="table-leg"></div>
                    <div class="table-leg"></div>
                    <div class="table-leg"></div>
                  </div>
                  <div class="table-status">
                    <span v-if="(!tables[7]?.available || !allowedTables.includes(8)) && availabilityChecked" class="status-occupied">
                      {{ !allowedTables.includes(8) ? 'Not Available' : 'Occupied' }}
                    </span>
                    <span v-if="tables[7]?.available && availabilityChecked && allowedTables.includes(8)" class="status-available">Available</span>
                  </div>
                </div>
              </div>

              <div class="table-item table-9">
                <div 
                  class="table-surface"
                  :class="{
                    'available': tables[8]?.available && availabilityChecked && allowedTables.includes(9),
                    'occupied': (!tables[8]?.available || !allowedTables.includes(9)) && availabilityChecked,
                    'selected': selectedTable === 9
                  }"
                  @click="selectTable(tables[8])"
                >
                  <span class="table-number">9</span>
                  <div v-if="selectedTable === 9" class="selected-indicator">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </div>
                </div>
                <div class="table-legs">
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                </div>
                <div class="table-status">
                  <span v-if="(!tables[8]?.available || !allowedTables.includes(9)) && availabilityChecked" class="status-occupied">
                    {{ !allowedTables.includes(9) ? 'Not Available' : 'Occupied' }}
                  </span>
                  <span v-if="tables[8]?.available && availabilityChecked && allowedTables.includes(9)" class="status-available">Available</span>
                </div>
              </div>
            </div>

            <!-- Bottom Row - Tables 10-15 -->
            <div class="tables-row bottom-row">
              <div 
                v-for="table in tables.slice(9, 15)" 
                :key="table.number"
                class="table-item"
                :class="{
                  'available': table.available && availabilityChecked && allowedTables.includes(table.number),
                  'occupied': (!table.available || !allowedTables.includes(table.number)) && availabilityChecked,
                  'selected': selectedTable === table.number
                }"
                @click="selectTable(table)"
              >
                <div 
                  class="table-surface"
                  :class="{
                    'available': table.available && availabilityChecked && allowedTables.includes(table.number),
                    'occupied': (!table.available || !allowedTables.includes(table.number)) && availabilityChecked
                  }"
                >
                  <span class="table-number">{{ table.number }}</span>
                  <div v-if="selectedTable === table.number" class="selected-indicator">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                      <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                  </div>
                </div>
                <div class="table-legs">
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                  <div class="table-leg"></div>
                </div>
                <div class="table-status">
                  <span v-if="(!table.available || !allowedTables.includes(table.number)) && availabilityChecked" class="status-occupied">
                    {{ !allowedTables.includes(table.number) ? 'Not Available' : 'Occupied' }}
                  </span>
                  <span v-if="table.available && availabilityChecked && allowedTables.includes(table.number)" class="status-available">Available</span>
                </div>
              </div>
            </div>

            <!-- Legend -->
            <div class="table-legend">
              <div class="legend-item">
                <div class="legend-color available"></div>
                <span>Available</span>
              </div>
              <div class="legend-item">
                <div class="legend-color occupied"></div>
                <span>Occupied</span>
              </div>
              <div class="legend-item">
                <div class="legend-color selected"></div>
                <span>Selected</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TableReservation',
  data() {
    const today = new Date()
    const year = today.getFullYear()
    const month = String(today.getMonth() + 1).padStart(2, '0')
    const day = String(today.getDate()).padStart(2, '0')
    const minDate = `${year}-${month}-${day}`

    return {
      selectedDate: '',
      selectedTime: '10:00',
      displayTime: '10:00 AM',
      formattedDisplayDate: '',
      numberOfPeople: 2,
      minDate: minDate,
      isChecking: false,
      availabilityChecked: false,
      availableTables: [],
      selectedTable: null,
      errorMessage: '',
      successMessage: '',
      tables: [],
      showCalendar: false,
      showTimePicker: false,
      currentMonth: today.getMonth(),
      currentYear: today.getFullYear(),
      weekDays: ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'],
      timeSlots: [
        { value: '10:00', label: '10:00 AM' },
        { value: '11:30', label: '11:30 AM' },
        { value: '13:00', label: '1:00 PM' },
        { value: '14:30', label: '2:30 PM' },
        { value: '16:00', label: '4:00 PM' },
        { value: '17:30', label: '5:30 PM' },
        { value: '19:00', label: '7:00 PM' },
        { value: '20:30', label: '8:30 PM' },
        { value: '22:00', label: '10:00 PM' }
      ],
      isMobileMenuOpen: false,
      isUserMenuOpen: false,
      currentUser: null
    }
  },
  computed: {
    formattedDate() {
      if (!this.selectedDate) return ''
      const [year, month, dayOfMonth] = this.selectedDate.split('-').map(Number)
      const date = new Date(year, month - 1, dayOfMonth)
      const options = { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' }
      return date.toLocaleDateString('en-US', options)
    },
    formattedTime() {
      if (!this.selectedTime) return ''
      const [hours, minutes] = this.selectedTime.split(':')
      const hour = parseInt(hours)
      const suffix = hour >= 12 ? 'PM' : 'AM'
      const displayHour = hour % 12 || 12
      return `${displayHour}:${minutes} ${suffix}`
    },
    currentMonthName() {
      return new Date(this.currentYear, this.currentMonth).toLocaleDateString('en-US', { month: 'long' })
    },
    allowedTables() {
      // If numberOfPeople > 6, only allow tables 6, 7, 8, 9
      // If numberOfPeople <= 6, allow tables 1-5, 10-15
      if (this.numberOfPeople > 6) {
        return [6, 7, 8, 9]
      } else {
        return [1, 2, 3, 4, 5, 10, 11, 12, 13, 14, 15]
      }
    },
    isTableAllowed() {
      return (tableNumber) => {
        return this.allowedTables.includes(tableNumber)
      }
    },
    calendarDays() {
      const days = []
      const firstDay = new Date(this.currentYear, this.currentMonth, 1)
      const startDate = new Date(firstDay)
      startDate.setDate(startDate.getDate() - firstDay.getDay())

      const today = new Date()
      today.setHours(0, 0, 0, 0)

      for (let i = 0; i < 42; i++) {
        const date = new Date(startDate)
        date.setDate(startDate.getDate() + i)
        const isOtherMonth = date.getMonth() !== this.currentMonth
        const year = date.getFullYear()
        const month = String(date.getMonth() + 1).padStart(2, '0')
        const dayOfMonth = String(date.getDate()).padStart(2, '0')
        const dateStr = `${year}-${month}-${dayOfMonth}`
        const dateOnly = new Date(date.getFullYear(), date.getMonth(), date.getDate())
        const todayOnly = new Date(today.getFullYear(), today.getMonth(), today.getDate())
        const isToday = dateOnly.getTime() === todayOnly.getTime()
        const isSelected = this.selectedDate && dateStr === this.selectedDate
        const disabled = dateOnly < todayOnly

        days.push({
          date: dateStr,
          day: date.getDate(),
          otherMonth: isOtherMonth,
          isToday: isToday,
          isSelected: isSelected,
          disabled: disabled
        })
      }
      return days
    }
  },
  mounted() {
    this.initializeTables()
    this.checkLoginStatus()
    document.addEventListener('click', this.handleClickOutside)
    document.addEventListener('click', this.handleClickOutsideForMenu)
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside)
    document.removeEventListener('click', this.handleClickOutsideForMenu)
  },
  methods: {
    handleClickOutside(event) {
      if (!event.target.closest('.form-group') && 
          !event.target.closest('.calendar-popup') && 
          !event.target.closest('.time-picker-popup')) {
        this.showCalendar = false
        this.showTimePicker = false
      }
    },
    previousMonth() {
      if (this.currentMonth === 0) {
        this.currentMonth = 11
        this.currentYear--
      } else {
        this.currentMonth--
      }
    },
    nextMonth() {
      if (this.currentMonth === 11) {
        this.currentMonth = 0
        this.currentYear++
      } else {
        this.currentMonth++
      }
    },
    selectCalendarDate(day) {
      if (day.disabled || day.otherMonth) return
      this.selectedDate = day.date
      const [year, month, dayOfMonth] = day.date.split('-')
      this.formattedDisplayDate = `${dayOfMonth}-${month}-${year}`
      this.showCalendar = false
      this.errorMessage = ''
      this.availabilityChecked = false
    },
    selectTime(time) {
      this.selectedTime = time
      const [hours, minutes] = time.split(':')
      const hour = parseInt(hours)
      const suffix = hour >= 12 ? 'PM' : 'AM'
      const displayHour = hour % 12 || 12
      this.displayTime = `${displayHour}:${minutes} ${suffix}`
      this.showTimePicker = false
      this.errorMessage = ''
      this.availabilityChecked = false
    },
    initializeTables() {
      this.tables = []
      for (let i = 1; i <= 15; i++) {
        this.tables.push({
          number: i,
          available: false
        })
      }
    },
    increasePeople() {
      if (this.numberOfPeople < 10) {
        this.numberOfPeople++
        // Reset availability when number of people changes
        if (this.availabilityChecked) {
          this.availabilityChecked = false
          this.selectedTable = null
          this.tables.forEach(table => {
            table.available = false
          })
        }
      }
    },
    decreasePeople() {
      if (this.numberOfPeople > 1) {
        this.numberOfPeople--
        // Reset availability when number of people changes
        if (this.availabilityChecked) {
          this.availabilityChecked = false
          this.selectedTable = null
          this.tables.forEach(table => {
            table.available = false
          })
        }
      }
    },
    async checkAvailability() {
      if (!this.selectedDate || !this.selectedTime) {
        this.errorMessage = 'Please select both date and time'
        return
      }

      this.isChecking = true
      this.errorMessage = ''
      this.successMessage = ''
      this.selectedTable = null

      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.errorMessage = 'Please login to check availability'
          this.$emit('show-login')
          return
        }

        const response = await fetch('http://localhost:8080/api/reservations/check-availability', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          },
          body: JSON.stringify({
            date: this.selectedDate,
            time: this.selectedTime,
            numberOfPax: this.numberOfPeople
          })
        })

        if (!response.ok) {
          const errorData = await response.json()
          throw new Error(errorData.error || 'Failed to check availability')
        }

        const data = await response.json()
        const allAvailableTables = data.availableTables || []
        
        // Filter available tables based on number of guests
        this.availableTables = allAvailableTables.filter(tableNum => 
          this.allowedTables.includes(tableNum)
        )

        this.tables.forEach(table => {
          // Table is available if:
          // 1. It's in the allowed list for this number of guests
          // 2. It's in the available tables from the API
          const isAllowed = this.allowedTables.includes(table.number)
          const isAvailableFromAPI = allAvailableTables.includes(table.number)
          table.available = isAllowed && isAvailableFromAPI
        })

        this.availabilityChecked = true
      } catch (error) {
        this.errorMessage = error.message || 'Failed to check availability'
        console.error('Error checking availability:', error)
      } finally {
        this.isChecking = false
      }
    },
    async selectTable(table) {
      if (!this.availabilityChecked) {
        this.errorMessage = 'Please check availability first'
        return
      }

      if (!this.allowedTables.includes(table.number)) {
        this.errorMessage = `Table ${table.number} is not available for ${this.numberOfPeople} guest${this.numberOfPeople > 1 ? 's' : ''}. ${this.numberOfPeople > 6 ? 'Please select tables 6, 7, 8, or 9.' : 'Please select tables 1-5 or 10-15.'}`
        return
      }

      if (!table.available) {
        this.errorMessage = 'This table is not available for the selected date and time'
        return
      }

      this.selectedTable = table.number
      this.errorMessage = ''
      this.successMessage = ''

      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.errorMessage = 'Please login to book a table'
          this.$emit('show-login')
          return
        }

        const user = JSON.parse(localStorage.getItem('user') || '{}')

        const response = await fetch('http://localhost:8080/api/reservations/book', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          },
          body: JSON.stringify({
            tableNumber: table.number,
            date: this.selectedDate,
            time: this.selectedTime,
            numberOfPax: this.numberOfPeople,
            customerName: user.username || user.email || '',
            phoneNumber: user.phoneNumber || ''
          })
        })

        if (!response.ok) {
          const errorData = await response.json()
          throw new Error(errorData.error || 'Failed to book table')
        }

        const reservation = await response.json()
        this.successMessage = `Reservation pending! Table ${table.number} booked for ${this.formattedDate} at ${this.formattedTime}. You will receive a confirmation email once the admin confirms your reservation.`
        
        this.availableTables = this.availableTables.filter(t => t !== table.number)
        table.available = false

        // Don't auto-close, let user close manually
      } catch (error) {
        this.errorMessage = error.message || 'Failed to book table'
        this.selectedTable = null
        console.error('Error booking table:', error)
      }
    },
    closeSuccessPopup() {
      this.successMessage = ''
      this.selectedTable = null
      this.availabilityChecked = false
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
    handleClickOutsideForMenu(event) {
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
.table-reservation-page {
  min-height: 100vh;
  overflow: hidden;
  background: linear-gradient(135deg, 
    #0a0a0a 0%, 
    #1a1a1a 30%, 
    #1f1f1f 70%,
    #0a0a0a 100%);
  color: #ffffff;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  position: relative;
  display: flex;
  flex-direction: column;
}

/* Subtle background pattern */
.table-reservation-page::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: 
    radial-gradient(circle at 15% 20%, rgba(139, 92, 246, 0.05) 0%, transparent 50%),
    radial-gradient(circle at 85% 80%, rgba(255, 159, 28, 0.05) 0%, transparent 50%),
    linear-gradient(45deg, transparent 49.5%, rgba(255, 255, 255, 0.02) 49.5%, rgba(255, 255, 255, 0.02) 50.5%, transparent 50.5%);
  background-size: 100% 100%, 100% 100%, 60px 60px;
  pointer-events: none;
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

/* Main Container */
.main-container {
  max-width: 1600px;
  margin: 0 auto;
  padding: 20px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  flex: 1;
  min-height: 0;
  width: 100%;
}

/* Section Cards */
.reservation-section,
.tables-section {
  display: flex;
  flex-direction: column;
  gap: 24px;
  height: 100%;
  min-height: 0;
}

.section-card {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 20px;
  padding: 24px;
  backdrop-filter: blur(20px);
  height: 100%;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

/* Section Titles */
.section-title {
  margin-bottom: 24px;
}

.section-title h2 {
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 8px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.title-accent {
  font-size: 14px;
  font-weight: 700;
  color: #8B5CF6;
  background: rgba(139, 92, 246, 0.1);
  padding: 4px 12px;
  border-radius: 20px;
  border: 1px solid rgba(139, 92, 246, 0.3);
}

.section-subtitle {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
  margin-left: 52px;
  line-height: 1.5;
}

/* Form Container */
.form-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  flex: 1;
}

.form-group {
  margin-bottom: 0;
  position: relative;
}

.form-label {
  display: block;
  font-size: 14px;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.9);
  margin-bottom: 10px;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 16px;
  color: rgba(255, 255, 255, 0.5);
  z-index: 1;
  width: 20px;
  height: 20px;
}

.form-input {
  width: 100%;
  padding: 14px 16px 14px 48px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  font-size: 15px;
  color: #ffffff;
  transition: all 0.3s ease;
  cursor: pointer;
}

.form-input:hover {
  border-color: rgba(255, 255, 255, 0.2);
  background: rgba(255, 255, 255, 0.07);
}

.form-input:focus {
  outline: none;
  border-color: #8B5CF6;
  box-shadow: 0 0 0 4px rgba(139, 92, 246, 0.15);
}

.calendar-toggle {
  position: absolute;
  right: 16px;
  background: none;
  border: none;
  color: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  padding: 6px;
  transition: color 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.calendar-toggle:hover {
  color: #8B5CF6;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

/* People Counter */
.people-counter {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  overflow: hidden;
  height: 52px;
}

.counter-btn {
  padding: 0 20px;
  background: rgba(255, 255, 255, 0.05);
  border: none;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.counter-btn:hover:not(:disabled) {
  background: rgba(139, 92, 246, 0.2);
  color: #ffffff;
}

.counter-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.people-value {
  flex: 1;
  text-align: center;
  padding: 0 20px;
  border-left: 1px solid rgba(255, 255, 255, 0.1);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.people-value span {
  display: block;
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
  line-height: 1;
}

.people-value small {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.5);
  margin-top: 2px;
}

/* Calendar Popup */
.calendar-popup {
  position: absolute;
  top: 100%;
  left: 0;
  width: 320px;
  margin-top: 8px;
  background: rgba(18, 18, 18, 0.98);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 12px;
  padding: 12px;
  z-index: 1000;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.6);
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.calendar-nav-btn {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 6px;
  padding: 6px;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
}

.calendar-nav-btn:hover {
  background: rgba(139, 92, 246, 0.2);
  border-color: rgba(139, 92, 246, 0.5);
}

.calendar-nav-btn svg {
  width: 12px;
  height: 12px;
}

.calendar-month-year {
  font-size: 13px;
  font-weight: 600;
  color: #ffffff;
}

.calendar-weekdays {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 2px;
  margin-bottom: 8px;
}

.calendar-weekday {
  text-align: center;
  font-size: 10px;
  font-weight: 500;
  color: rgba(255, 255, 255, 0.5);
  padding: 4px 0;
}

.calendar-days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 2px;
}

.calendar-day {
  aspect-ratio: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.2s ease;
  color: rgba(255, 255, 255, 0.8);
  min-height: 32px;
}

.calendar-day:hover:not(.disabled):not(.other-month) {
  background: rgba(139, 92, 246, 0.2);
  color: #ffffff;
}

.calendar-day.other-month {
  color: rgba(255, 255, 255, 0.3);
  cursor: default;
}

.calendar-day.today {
  background: rgba(139, 92, 246, 0.3);
  font-weight: 600;
  color: #ffffff;
}

.calendar-day.selected {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: #ffffff;
  font-weight: 700;
  box-shadow: 0 4px 12px rgba(139, 92, 246, 0.4);
}

.calendar-day.disabled {
  color: rgba(255, 255, 255, 0.2);
  cursor: not-allowed;
  opacity: 0.5;
}

/* Time Picker */
.time-picker-popup {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  margin-top: 8px;
  background: rgba(18, 18, 18, 0.98);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 16px;
  padding: 16px;
  z-index: 1000;
  max-height: 240px;
  overflow-y: auto;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.6);
}

.time-slots {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 8px;
}

.time-slot-btn {
  padding: 12px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  text-align: center;
}

.time-slot-btn:hover {
  background: rgba(139, 92, 246, 0.2);
  border-color: rgba(139, 92, 246, 0.5);
}

.time-slot-btn.selected {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: #ffffff;
  border-color: #8B5CF6;
  font-weight: 600;
}

/* Check Availability Button */
.btn-check-availability {
  width: 100%;
  padding: 16px 24px;
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  border: none;
  border-radius: 14px;
  color: #ffffff;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  margin-top: 8px;
}

.btn-check-availability:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 16px 32px rgba(139, 92, 246, 0.4);
}

.btn-check-availability:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none;
}

.spinner {
  width: 24px;
  height: 24px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* Status Messages Container */
.status-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 0;
}

.status-card {
  padding: 20px;
  border-radius: 16px;
  display: flex;
  align-items: flex-start;
  gap: 16px;
  animation: slideIn 0.3s ease;
  border: 1px solid transparent;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.status-card.success {
  background: rgba(16, 185, 129, 0.1);
  border-color: rgba(16, 185, 129, 0.2);
}

.status-card.error {
  background: rgba(239, 68, 68, 0.1);
  border-color: rgba(239, 68, 68, 0.2);
}

.status-icon {
  flex-shrink: 0;
  margin-top: 2px;
}

.status-content h3 {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 700;
}

.status-card.success h3 {
  color: #10B981;
}

.status-card.error h3 {
  color: #EF4444;
}

.status-content p {
  margin: 0;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
  line-height: 1.5;
}

.instruction-text {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.6);
  margin-top: 8px;
}

/* Restaurant Layout */
.restaurant-layout {
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  padding: 24px;
  position: relative;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 0;
}

.tables-row {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 24px;
  margin-bottom: 24px;
  position: relative;
  flex-shrink: 0;
}

.top-row {
  margin-top: 8px;
}

.middle-row {
  justify-content: space-between;
  margin-bottom: 24px;
}

.bottom-row {
  margin-bottom: 24px;
}

.large-tables-container {
  display: flex;
  gap: 40px;
  margin: 0 40px;
}

/* Table Items */
.table-item {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.table-item:hover:not(.table-surface.occupied) {
  transform: translateY(-6px);
}

.table-surface {
  width: 70px;
  height: 70px;
  background: linear-gradient(135deg, #3a3529 0%, #2a251f 100%);
  border: 3px solid #5a4a3a;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 2;
  box-shadow: 
    0 6px 12px rgba(0, 0, 0, 0.4),
    inset 0 2px 4px rgba(255, 255, 255, 0.1);
  transition: all 0.3s ease;
}

.table-surface.rectangular {
  width: 100px;
  height: 70px;
  border-radius: 14px;
}

.table-surface.available {
  background: linear-gradient(135deg, #3a3529 0%, #2a251f 100%);
  border-color: #10B981;
  box-shadow: 
    0 8px 16px rgba(0, 0, 0, 0.4),
    inset 0 2px 4px rgba(255, 255, 255, 0.1),
    0 0 24px rgba(16, 185, 129, 0.3);
}

.table-surface.occupied {
  background: linear-gradient(135deg, #4a4a4a 0%, #3a3a3a 100%);
  border-color: #6b7280;
  opacity: 0.6;
  cursor: not-allowed;
}

.table-surface.selected {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  border-color: #8B5CF6;
  transform: scale(1.15);
  box-shadow: 
    0 16px 32px rgba(139, 92, 246, 0.4),
    inset 0 2px 4px rgba(255, 255, 255, 0.2);
}

.table-surface.occupied:hover {
  transform: none;
  cursor: not-allowed;
}

.table-number {
  font-size: 18px;
  font-weight: 700;
  color: #ffffff;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.selected-indicator {
  position: absolute;
  top: -8px;
  right: -8px;
  background: #8B5CF6;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 4px 12px rgba(139, 92, 246, 0.4);
  z-index: 3;
}

.selected-indicator svg {
  width: 14px;
  height: 14px;
}

.table-legs {
  display: flex;
  gap: 14px;
  margin-top: 6px;
}

.table-leg {
  width: 8px;
  height: 16px;
  background: linear-gradient(to bottom, #5a4a3a, #4a3d2f);
  border-radius: 3px;
}

.table-surface.available .table-leg {
  background: linear-gradient(to bottom, #5a4a3a, #4a3d2f);
}

.table-surface.selected .table-leg {
  background: linear-gradient(to bottom, #7C3AED, #4F46E5);
}

.table-status {
  margin-top: 6px;
  font-size: 10px;
  font-weight: 600;
  letter-spacing: 0.3px;
  text-transform: uppercase;
  height: 16px;
}

.status-available {
  color: #10B981;
}

.status-occupied {
  color: #6b7280;
}

/* Table Legend */
.table-legend {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  flex-shrink: 0;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
}

.legend-color {
  width: 16px;
  height: 16px;
  border-radius: 4px;
}

.legend-color.available {
  background: #10B981;
}

.legend-color.occupied {
  background: #6b7280;
}

.legend-color.selected {
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
}

/* Responsive */
@media (max-width: 1200px) {
  .main-container {
    grid-template-columns: 1fr;
    gap: 24px;
    padding: 24px;
  }
  
  .tables-row {
    gap: 20px;
  }
  
  .large-tables-container {
    gap: 30px;
    margin: 0 30px;
  }
  
  .table-surface {
    width: 60px;
    height: 60px;
  }
  
  .table-surface.rectangular {
    width: 90px;
    height: 60px;
  }
}

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
  
  .main-container {
    padding: 20px;
  }
  
  .section-card {
    padding: 20px;
  }
  
  .form-row {
    grid-template-columns: 1fr;
    gap: 16px;
  }
  
  .tables-row {
    flex-wrap: wrap;
    gap: 16px;
    margin-bottom: 16px;
  }
  
  .middle-row {
    flex-direction: column;
    align-items: center;
    gap: 16px;
  }
  
  .large-tables-container {
    flex-direction: column;
    gap: 16px;
    margin: 16px 0;
  }
  
  .table-6, .table-9 {
    order: -1;
  }
  
  .section-title h2 {
    font-size: 18px;
  }
  
  .section-subtitle {
    font-size: 13px;
    margin-left: 0;
  }
}

@media (max-width: 480px) {
  .page-header {
    padding: 16px;
  }
  
  .page-title {
    font-size: 18px;
  }
  
  .btn-back {
    padding: 8px 16px;
    font-size: 12px;
  }
  
  .main-container {
    padding: 16px;
  }
  
  .section-card {
    padding: 16px;
  }
  
  .table-surface {
    width: 50px;
    height: 50px;
  }
  
  .table-surface.rectangular {
    width: 80px;
    height: 50px;
  }
  
  .table-number {
    font-size: 16px;
  }
  
  .legend-item {
    font-size: 11px;
  }
  
  .legend-color {
    width: 12px;
    height: 12px;
  }
}

/* Success Popup Modal */
.success-popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(8px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.success-popup {
  background: rgba(18, 18, 18, 0.98);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(16, 185, 129, 0.3);
  border-radius: 20px;
  padding: 40px;
  max-width: 500px;
  width: 90%;
  position: relative;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.8);
  animation: slideUp 0.3s ease;
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

.success-popup-close {
  position: absolute;
  top: 16px;
  right: 16px;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.7);
  transition: all 0.3s ease;
}

.success-popup-close:hover {
  background: rgba(255, 255, 255, 0.2);
  color: #ffffff;
  transform: rotate(90deg);
}

.success-popup-icon {
  display: flex;
  justify-content: center;
  margin-bottom: 24px;
}

.success-popup-content {
  text-align: center;
}

.success-popup-content h3 {
  font-size: 24px;
  font-weight: 700;
  color: #10B981;
  margin: 0 0 16px 0;
}

.success-popup-content p {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.9);
  line-height: 1.6;
  margin: 0;
}
</style>