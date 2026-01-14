<template>
  <div class="admin-dashboard">
    <!-- Mobile Sidebar Toggle Button -->
    <button class="mobile-sidebar-toggle" @click="toggleSidebar">
      <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
        <line x1="4" y1="6" x2="20" y2="6" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
        <line x1="4" y1="12" x2="20" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
        <line x1="4" y1="18" x2="20" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      </svg>
    </button>

    <!-- Mobile Sidebar Overlay -->
    <div v-if="isSidebarOpen" class="sidebar-overlay" @click="closeSidebar"></div>

    <header class="admin-header">
      <div class="admin-header-content">
        <div class="admin-logo">
          <img src="./logo_restaurant.png" alt="Sajian Nusantara Logo" class="logo-img" />
          <h1 class="admin-title">Admin Dashboard</h1>
        </div>
      </div>
    </header>

    <main class="admin-main">
      <div class="admin-container">
        <!-- Sidebar -->
        <aside :class="['admin-sidebar', { 'open': isSidebarOpen }]">
          <div class="sidebar-header">
            <h3 class="sidebar-title">Navigation</h3>
            <button class="sidebar-close" @click="closeSidebar">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <line x1="18" y1="6" x2="6" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                <line x1="6" y1="6" x2="18" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
              </svg>
            </button>
          </div>
          <nav class="admin-nav">
            <a href="#" class="nav-item" :class="{ 'active': activeTab === 'overview' }" @click.prevent="setActiveTab('overview')">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <rect x="3" y="3" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <rect x="14" y="3" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <rect x="3" y="14" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <rect x="14" y="14" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span class="nav-text">Dashboard</span>
            </a>
            <a href="#" class="nav-item" :class="{ 'active': activeTab === 'users' }" @click.prevent="setActiveTab('users')">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <circle cx="9" cy="7" r="4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M23 21v-2a4 4 0 0 0-3-3.87" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M16 3.13a4 4 0 0 1 0 7.75" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span class="nav-text">Users</span>
            </a>
            <a href="#" class="nav-item" :class="{ 'active': activeTab === 'reservations' }" @click.prevent="setActiveTab('reservations')">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <rect x="3" y="4" width="18" height="18" rx="2" ry="2" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <line x1="16" y1="2" x2="16" y2="6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <line x1="8" y1="2" x2="8" y2="6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <line x1="3" y1="10" x2="21" y2="10" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span class="nav-text">Reservations</span>
            </a>
            <a href="#" class="nav-item" :class="{ 'active': activeTab === 'menu' }" @click.prevent="setActiveTab('menu')">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <line x1="3" y1="6" x2="21" y2="6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M16 10a4 4 0 0 1-8 0" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span class="nav-text">Menu</span>
            </a>
            <a href="#" class="nav-item" :class="{ 'active': activeTab === 'orders' }" @click.prevent="setActiveTab('orders')">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9 11l3 3L22 4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span class="nav-text">Orders</span>
            </a>
            <a href="#" class="nav-item" @click.prevent="handleLogout">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <polyline points="16 17 21 12 16 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <line x1="21" y1="12" x2="9" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span class="nav-text">Logout</span>
            </a>
          </nav>
          <div class="sidebar-footer">
            <div class="current-user">
              <div class="user-avatar">
                {{ getUserInitials() }}
              </div>
              <div class="user-details">
                <p class="user-email">{{ userInfo?.email || 'admin@admin.com' }}</p>
                <p class="user-role">Administrator</p>
              </div>
            </div>
          </div>
        </aside>

        <!-- Main Content -->
        <div class="admin-content">
          <div class="content-header">
            <h2 class="content-title">
              <template v-if="activeTab === 'overview'">Dashboard Overview</template>
              <template v-else-if="activeTab === 'users'">User Management</template>
              <template v-else-if="activeTab === 'reservations'">Reservation Management</template>
              <template v-else-if="activeTab === 'menu'">Menu Management</template>
              <template v-else-if="activeTab === 'orders'">Order Management</template>
            </h2>
            <div class="breadcrumb">
              <span class="breadcrumb-item">Admin</span>
              <span class="breadcrumb-separator">/</span>
              <span class="breadcrumb-item active">
                <template v-if="activeTab === 'overview'">Dashboard</template>
                <template v-else-if="activeTab === 'users'">Users</template>
                <template v-else-if="activeTab === 'reservations'">Reservations</template>
                <template v-else-if="activeTab === 'menu'">Menu</template>
                <template v-else-if="activeTab === 'orders'">Orders</template>
              </span>
            </div>
          </div>

          <!-- Tab Content -->
          <div class="tab-content-wrapper">
            <div v-if="activeTab === 'overview'" class="tab-content">
              <div class="stats-grid">
                <div class="stat-card">
                  <div class="stat-icon">👥</div>
                  <div class="stat-info">
                    <h3>Total Users</h3>
                    <p class="stat-value">{{ stats.totalUsers || 0 }}</p>
                  </div>
                </div>
                <div class="stat-card">
                  <div class="stat-icon">📅</div>
                  <div class="stat-info">
                    <h3>Reservations</h3>
                    <p class="stat-value">{{ stats.totalReservations || 0 }}</p>
                  </div>
                </div>
                <div class="stat-card">
                  <div class="stat-icon">🍽️</div>
                  <div class="stat-info">
                    <h3>Menu Items</h3>
                    <p class="stat-value">{{ stats.totalMenuItems || 0 }}</p>
                  </div>
                </div>
                <div class="stat-card">
                  <div class="stat-icon">💰</div>
                  <div class="stat-info">
                    <h3>Today Revenue</h3>
                    <p class="stat-value">RM {{ (stats.todayRevenue || 0).toFixed(2) }}</p>
                  </div>
                </div>
                <div class="stat-card">
                  <div class="stat-icon">📋</div>
                  <div class="stat-info">
                    <h3>Today Order</h3>
                    <p class="stat-value">{{ stats.todayOrderCount || 0 }}</p>
                  </div>
                </div>
              </div>
            </div>

            <div v-else-if="activeTab === 'users'" class="tab-content">
              <div class="tab-content-inner">
                <div v-if="loadingUsers" class="loading">Loading users...</div>
                <div v-else-if="users.length === 0" class="empty-state">
                  <p>No users found.</p>
                </div>
                <div v-else class="users-table-container">
                  <table class="users-table">
                    <thead>
                      <tr>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Created At</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="user in users" :key="user.id">
                        <td>{{ user.username }}</td>
                        <td>{{ user.email }}</td>
                        <td>
                          <span :class="['role-badge', user.role.toLowerCase()]">
                            {{ user.role }}
                          </span>
                        </td>
                        <td>{{ formatDate(user.createdAt) }}</td>
                        <td>
                          <button 
                            @click="deleteUser(user.id)" 
                            class="btn-delete-small"
                            :disabled="isDeletingUser"
                          >
                            {{ isDeletingUser ? 'Deleting...' : 'Delete' }}
                          </button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div v-if="userError" class="error-message">{{ userError }}</div>
              </div>
            </div>

            <div v-else-if="activeTab === 'reservations'" class="tab-content">
              <div class="tab-content-inner">
                <div v-if="loadingReservations" class="loading">Loading reservations...</div>
                <div v-else-if="reservations.length === 0" class="empty-state">
                  <p>No reservations found.</p>
                </div>
                <div v-else class="reservations-table-container">
                  <table class="reservations-table">
                    <thead>
                      <tr>
                        <th>Name</th>
                        <th>Phone</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Guests</th>
                        <th>Table</th>
                        <th>Status</th>
                        <th>Actions</th>
                        <th>Confirm</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="reservation in reservations" :key="reservation.id">
                        <td>{{ reservation.name }}</td>
                        <td>{{ reservation.phone }}</td>
                        <td>{{ formatDate(reservation.date) }}</td>
                        <td>{{ formatTime(reservation.time) }}</td>
                        <td>{{ reservation.numberOfGuests }}</td>
                        <td>{{ reservation.tableNumber || 'N/A' }}</td>
                        <td>
                          <span :class="['reservation-status-badge', reservation.status.toLowerCase()]">
                            {{ reservation.status }}
                          </span>
                        </td>
                        <td>
                          <button 
                            @click="deleteReservation(reservation.id)" 
                            class="btn-delete-small"
                            :disabled="isDeletingReservation"
                          >
                            {{ isDeletingReservation ? 'Deleting...' : 'Delete' }}
                          </button>
                        </td>
                        <td>
                          <div class="reservation-actions-group">
                            <button 
                              v-if="reservation.status === 'PENDING'"
                              @click="confirmReservation(reservation.id)"
                              class="btn-confirm"
                              :disabled="isConfirmingReservation || isCancellingReservation"
                              title="Confirm Reservation"
                            >
                              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                              </svg>
                              {{ isConfirmingReservation ? 'Confirming...' : '' }}
                            </button>
                            <span v-else-if="reservation.status === 'CONFIRMED'" class="confirmed-indicator">
                              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M20 6L9 17l-5-5" stroke="#06B6D4" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                              </svg>
                            </span>
                            <span v-else-if="reservation.status === 'CANCELLED'" class="cancelled-indicator">
                              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <line x1="18" y1="6" x2="6" y2="18" stroke="#FF6B6B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <line x1="6" y1="6" x2="18" y2="18" stroke="#FF6B6B" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                              </svg>
                            </span>
                            <button 
                              v-if="reservation.status === 'PENDING' || reservation.status === 'CONFIRMED'"
                              @click="cancelReservation(reservation.id)"
                              class="btn-cancel-reservation"
                              :disabled="isCancellingReservation || isConfirmingReservation"
                              title="Cancel Reservation"
                            >
                              <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <line x1="18" y1="6" x2="6" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <line x1="6" y1="6" x2="18" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                              </svg>
                              {{ isCancellingReservation ? 'Cancelling...' : '' }}
                            </button>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div v-if="reservationError" class="error-message">{{ reservationError }}</div>
              </div>
            </div>

            <div v-else-if="activeTab === 'menu'" class="tab-content">
              <div class="tab-content-inner">
                <div class="menu-header">
                  <div class="menu-header-actions">
                    <select v-model="selectedCategoryFilter" class="category-filter">
                      <option value="">All Categories</option>
                      <option value="nasi">Nasi</option>
                      <option value="western">Western</option>
                      <option value="noodles">Noodles</option>
                      <option value="appetizer">Appetizer</option>
                      <option value="drinks">Drinks</option>
                    </select>
                    <div class="view-toggle">
                      <button 
                        @click="menuViewMode = 'grid'" 
                        :class="['view-btn', { active: menuViewMode === 'grid' }]"
                        title="Grid View"
                      >
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                          <rect x="3" y="3" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <rect x="14" y="3" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <rect x="3" y="14" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <rect x="14" y="14" width="7" height="7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                      </button>
                      <button 
                        @click="menuViewMode = 'list'" 
                        :class="['view-btn', { active: menuViewMode === 'list' }]"
                        title="List View"
                      >
                        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                          <line x1="8" y1="6" x2="21" y2="6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <line x1="8" y1="12" x2="21" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <line x1="8" y1="18" x2="21" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <line x1="3" y1="6" x2="3.01" y2="6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <line x1="3" y1="12" x2="3.01" y2="12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                          <line x1="3" y1="18" x2="3.01" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                      </button>
                    </div>
                    <button @click="showMenuForm = true; editingMenu = null" class="btn-add-menu">
                      + Add Menu Item
                    </button>
                  </div>
                </div>

                <!-- Menu Form Modal -->
                <div v-if="showMenuForm" ref="modalOverlay" class="modal-overlay" @click.self="closeMenuForm">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h3>{{ editingMenu ? 'Edit Menu Item' : 'Add New Menu Item' }}</h3>
                      <button @click="closeMenuForm" class="btn-close">&times;</button>
                    </div>
                    <form @submit.prevent="saveMenu" class="menu-form">
                      <div class="form-group">
                        <label>Name *</label>
                        <input v-model="menuForm.name" type="text" required placeholder="Menu item name" />
                      </div>
                      <div class="form-group">
                        <label>Description *</label>
                        <textarea v-model="menuForm.description" required placeholder="Menu item description" rows="3"></textarea>
                      </div>
                      <div class="form-row">
                        <div class="form-group">
                          <label>Price *</label>
                          <input v-model="menuForm.price" type="number" step="0.01" required placeholder="0.00" />
                        </div>
                        <div class="form-group">
                          <label>Category *</label>
                          <select v-model="menuForm.category" required>
                            <option value="">Select category</option>
                            <option value="nasi">Nasi</option>
                            <option value="western">Western</option>
                            <option value="noodles">Noodles</option>
                            <option value="appetizer">Appetizer</option>
                            <option value="drinks">Drinks</option>
                          </select>
                        </div>
                      </div>
                      <div class="form-group">
                        <label>Image</label>
                        <input type="file" @change="handleImageSelect" accept="image/*" />
                        <div v-if="menuForm.imagePreview" class="image-preview">
                          <img :src="menuForm.imagePreview" alt="Preview" />
                        </div>
                        <div v-if="editingMenu && editingMenu.imageUrl && !menuForm.imagePreview" class="current-image">
                          <p>Current image:</p>
                          <img :src="'http://localhost:8080' + editingMenu.imageUrl" alt="Current" />
                        </div>
                      </div>
                      <div class="form-group">
                        <label>
                          <input type="checkbox" v-model="menuForm.available" />
                          Available
                        </label>
                      </div>
                      <div v-if="menuError" class="error-message">{{ menuError }}</div>
                      <div class="form-actions">
                        <button type="button" @click="closeMenuForm" class="btn-cancel">Cancel</button>
                        <button type="submit" class="btn-save" :disabled="isSavingMenu">
                          {{ isSavingMenu ? 'Saving...' : (editingMenu ? 'Update' : 'Create') }}
                        </button>
                      </div>
                    </form>
                  </div>
                </div>

                <!-- Menu Items Display -->
                <div v-if="loadingMenus" class="loading">Loading menu items...</div>
                <div v-else-if="filteredMenus.length === 0" class="empty-state">
                  <p v-if="selectedCategoryFilter">{{ selectedCategoryFilter }} category has no menu items yet.</p>
                  <p v-else>No menu items yet. Click "Add Menu Item" to create one.</p>
                </div>
                
                <!-- Grid View -->
                <div v-else-if="menuViewMode === 'grid'" class="menu-grid">
                  <div v-for="menu in filteredMenus" :key="menu.id" class="menu-card">
                    <div class="menu-image">
                      <img v-if="menu.imageUrl" :src="'http://localhost:8080' + menu.imageUrl" :alt="menu.name" />
                      <div v-else class="no-image">No Image</div>
                    </div>
                    <div class="menu-info">
                      <h3>{{ menu.name }}</h3>
                      <p class="menu-description">{{ menu.description }}</p>
                      <div class="menu-meta">
                        <span class="menu-category">{{ menu.category }}</span>
                        <span class="menu-price">RM {{ parseFloat(menu.price).toFixed(2) }}</span>
                      </div>
                      <div class="menu-status">
                        <span :class="['status-badge', menu.available ? 'available' : 'unavailable']">
                          {{ menu.available ? 'Available' : 'Unavailable' }}
                        </span>
                      </div>
                    </div>
                    <div class="menu-actions">
                      <button @click="editMenu(menu)" class="btn-edit">Edit</button>
                      <button @click="deleteMenu(menu.id)" class="btn-delete" :disabled="isDeletingMenu">
                        {{ isDeletingMenu ? 'Deleting...' : 'Delete' }}
                      </button>
                    </div>
                  </div>
                </div>

                <!-- List View -->
                <div v-else class="menu-list-container">
                  <table class="menu-list-table">
                    <thead>
                      <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="menu in filteredMenus" :key="menu.id">
                        <td class="menu-list-image">
                          <img v-if="menu.imageUrl" :src="'http://localhost:8080' + menu.imageUrl" :alt="menu.name" />
                          <div v-else class="no-image-small">No Image</div>
                        </td>
                        <td class="menu-list-name">{{ menu.name }}</td>
                        <td class="menu-list-description">{{ menu.description }}</td>
                        <td>
                          <span class="menu-category">{{ menu.category }}</span>
                        </td>
                        <td class="menu-list-price">RM {{ parseFloat(menu.price).toFixed(2) }}</td>
                        <td>
                          <span :class="['status-badge', menu.available ? 'available' : 'unavailable']">
                            {{ menu.available ? 'Available' : 'Unavailable' }}
                          </span>
                        </td>
                        <td class="menu-list-actions">
                          <button @click="editMenu(menu)" class="btn-edit-small">Edit</button>
                          <button @click="deleteMenu(menu.id)" class="btn-delete-small" :disabled="isDeletingMenu">
                            {{ isDeletingMenu ? 'Deleting...' : 'Delete' }}
                          </button>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>

            <div v-else-if="activeTab === 'orders'" class="tab-content">
              <div class="tab-content-inner">
                <!-- Order Management Header with Search -->
                <div class="order-management-header">
                  <div class="search-container">
                    <div class="search-input-wrapper">
                      <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <circle cx="11" cy="11" r="8" stroke="currentColor" stroke-width="2"/>
                        <line x1="16.5" y1="16.5" x2="21" y2="21" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                      </svg>
                      <input 
                        v-model="searchQuery" 
                        type="text" 
                        placeholder="Search by order number..." 
                        class="search-input"
                        @input="filterOrders"
                      />
                      <button 
                        v-if="searchQuery" 
                        @click="clearSearch" 
                        class="clear-search-btn"
                      >
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                          <line x1="18" y1="6" x2="6" y2="18" stroke="currentColor" stroke-width="2"/>
                          <line x1="6" y1="6" x2="18" y2="18" stroke="currentColor" stroke-width="2"/>
                        </svg>
                      </button>
                    </div>
                  </div>
                  <div class="order-stats">
                    <div class="order-stat-item">
                      <span class="order-stat-label">Total:</span>
                      <span class="order-stat-value">{{ orders.length }}</span>
                    </div>
                    <div class="order-stat-item">
                      <span class="order-stat-label">Pending:</span>
                      <span class="order-stat-value pending">{{ pendingOrdersCount }}</span>
                    </div>
                    <div class="order-stat-item">
                      <span class="order-stat-label">Completed:</span>
                      <span class="order-stat-value completed">{{ completedOrdersCount }}</span>
                    </div>
                  </div>
                </div>

                <div v-if="loadingOrders" class="loading">Loading orders...</div>
                <div v-else-if="filteredOrders.length === 0" class="empty-state">
                  <p v-if="searchQuery">No orders found for "{{ searchQuery }}"</p>
                  <p v-else>No orders found.</p>
                </div>
                <div v-else class="orders-table-container">
                  <table class="orders-table compact">
                    <thead>
                      <tr>
                        <th>Order #</th>
                        <th>Customer</th>
                        <th>Type</th>
                        <th>Table</th>
                        <th>Items</th>
                        <th>Total</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Complete</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr v-for="order in filteredOrders" :key="order.id">
                        <td class="order-number-cell">
                          <span class="order-number">#{{ order.orderNumber }}</span>
                        </td>
                        <td class="customer-cell">
                          <div class="customer-info">
                            <span class="customer-name">{{ getCustomerName(order.userId) }}</span>
                          </div>
                        </td>
                        <td>
                          <span :class="['order-type-badge', order.orderType === 'dine-in' ? 'dine-in' : 'pickup']">
                            {{ order.orderType === 'dine-in' ? 'Dine In' : 'Pickup' }}
                          </span>
                        </td>
                        <td class="table-cell">{{ order.tableNumber || 'N/A' }}</td>
                        <td>
                          <div class="order-items-preview">
                            <div v-for="(item, index) in order.items.slice(0, 2)" :key="index" class="order-item-preview">
                              {{ item.name }} × {{ item.quantity }}
                            </div>
                            <div v-if="order.items.length > 2" class="more-items">
                              +{{ order.items.length - 2 }} more
                            </div>
                          </div>
                        </td>
                        <td class="order-total-cell">RM {{ order.total?.toFixed(2) || '0.00' }}</td>
                        <td class="order-date-cell">
                          <div class="order-date-compact">
                            {{ formatCompactDate(order.createdAt) }}
                          </div>
                        </td>
                        <td>
                          <span :class="['order-status-badge', order.status?.toLowerCase()]">
                            {{ order.status || 'PENDING' }}
                          </span>
                        </td>
                        <td>
                          <button 
                            v-if="order.status !== 'COMPLETED'"
                            @click="completeOrder(order.id)"
                            class="btn-complete-order"
                            :disabled="isCompletingOrder"
                            title="Mark as Completed"
                          >
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                              <path d="M20 6L9 17l-5-5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                          </button>
                          <span v-else class="completed-indicator" title="Completed">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                              <path d="M20 6L9 17l-5-5" stroke="#10B981" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                          </span>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
                <div v-if="orderError" class="error-message">{{ orderError }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'AdminDashboard',
  data() {
    return {
      activeTab: 'overview',
      isSidebarOpen: false,
      userInfo: null,
      stats: {
        totalUsers: 0,
        totalReservations: 0,
        totalMenuItems: 0,
        todayRevenue: 0,
        todayOrderCount: 0
      },
      // Menu management
      menus: [],
      loadingMenus: false,
      showMenuForm: false,
      editingMenu: null,
      selectedCategoryFilter: '',
      menuViewMode: 'grid',
      menuForm: {
        name: '',
        description: '',
        price: '',
        category: '',
        available: true,
        image: null,
        imagePreview: null
      },
      isSavingMenu: false,
      isDeletingMenu: false,
      menuError: null,
      // User management
      users: [],
      loadingUsers: false,
      isUpdatingRole: false,
      isDeletingUser: false,
      userError: null,
      // Reservation management
      reservations: [],
      loadingReservations: false,
      isUpdatingStatus: false,
      isDeletingReservation: false,
      isConfirmingReservation: false,
      isCancellingReservation: false,
      reservationError: null,
      // Order management
      orders: [],
      filteredOrders: [],
      loadingOrders: false,
      isCompletingOrder: false,
      orderError: null,
      customerNames: {},
      searchQuery: ''
    }
  },
  mounted() {
    // Get user info from localStorage
    const userStr = localStorage.getItem('user')
    if (userStr) {
      this.userInfo = JSON.parse(userStr)
    }
    // Load dashboard stats
    this.loadDashboardStats()
    
    // Close sidebar when clicking outside on mobile
    document.addEventListener('click', (e) => {
      if (this.isSidebarOpen && !e.target.closest('.admin-sidebar') && !e.target.closest('.mobile-sidebar-toggle')) {
        this.closeSidebar()
      }
    })
    
    // Close sidebar on escape key
    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && this.isSidebarOpen) {
        this.closeSidebar()
      }
    })
  },
  computed: {
    filteredMenus() {
      if (!this.selectedCategoryFilter) {
        return this.menus
      }
      return this.menus.filter(menu => 
        menu.category && menu.category.toLowerCase() === this.selectedCategoryFilter.toLowerCase()
      )
    },
    pendingOrdersCount() {
      return this.orders.filter(order => order.status === 'PENDING').length
    },
    completedOrdersCount() {
      return this.orders.filter(order => order.status === 'COMPLETED').length
    }
  },
  watch: {
    activeTab(newTab) {
      if (newTab === 'menu') {
        this.loadMenus()
      } else if (newTab === 'overview') {
        this.loadDashboardStats()
      } else if (newTab === 'users') {
        this.loadUsers()
      } else if (newTab === 'reservations') {
        this.loadReservations()
      } else if (newTab === 'orders') {
        this.loadOrders()
      }
    },
    showMenuForm(newVal) {
      if (newVal) {
        this.$nextTick(() => {
          if (this.$refs.modalOverlay) {
            this.$refs.modalOverlay.scrollTop = 0
          }
        })
      }
    }
  },
  methods: {
    toggleSidebar() {
      this.isSidebarOpen = !this.isSidebarOpen
    },
    closeSidebar() {
      this.isSidebarOpen = false
    },
    getUserInitials() {
      if (!this.userInfo) return 'A'
      const name = this.userInfo.username || this.userInfo.email || 'Admin'
      return name.charAt(0).toUpperCase()
    },
    setActiveTab(tab) {
      this.activeTab = tab
      this.closeSidebar()
    },
    getAuthHeaders(includeContentType = true) {
      const token = localStorage.getItem('token')
      const headers = {
        'Authorization': `Bearer ${token}`
      }
      if (includeContentType) {
        headers['Content-Type'] = 'application/json'
      }
      return headers
    },
    async loadDashboardStats() {
      try {
        const response = await fetch('http://localhost:8080/api/admin/dashboard/stats', {
          headers: this.getAuthHeaders()
        })
        if (response.ok) {
          this.stats = await response.json()
        }
      } catch (error) {
        console.error('Error loading dashboard stats:', error)
      }
    },
    async loadMenus() {
      this.loadingMenus = true
      try {
        const response = await fetch('http://localhost:8080/api/admin/menus', {
          headers: this.getAuthHeaders()
        })
        if (response.ok) {
          this.menus = await response.json()
        } else if (response.status === 401) {
          alert('Session expired. Please login again.')
          this.handleLogout()
        }
      } catch (error) {
        console.error('Error loading menus:', error)
      } finally {
        this.loadingMenus = false
      }
    },
    handleImageSelect(event) {
      const file = event.target.files[0]
      if (file) {
        this.menuForm.image = file
        const reader = new FileReader()
        reader.onload = (e) => {
          this.menuForm.imagePreview = e.target.result
        }
        reader.readAsDataURL(file)
      }
    },
    editMenu(menu) {
      this.editingMenu = menu
      this.menuForm = {
        name: menu.name,
        description: menu.description,
        price: menu.price,
        category: menu.category,
        available: menu.available,
        image: null,
        imagePreview: null
      }
      this.showMenuForm = true
    },
    closeMenuForm() {
      this.showMenuForm = false
      this.editingMenu = null
      this.menuForm = {
        name: '',
        description: '',
        price: '',
        category: '',
        available: true,
        image: null,
        imagePreview: null
      }
      this.menuError = null
    },
    async saveMenu() {
      this.isSavingMenu = true
      this.menuError = null
      
      try {
        const formData = new FormData()
        formData.append('name', this.menuForm.name)
        formData.append('description', this.menuForm.description)
        formData.append('price', String(this.menuForm.price))
        formData.append('category', this.menuForm.category)
        formData.append('available', String(this.menuForm.available))
        
        if (this.menuForm.image) {
          formData.append('image', this.menuForm.image)
        }

        const url = this.editingMenu 
          ? `http://localhost:8080/api/admin/menus/${this.editingMenu.id}`
          : 'http://localhost:8080/api/admin/menus'
        
        const method = this.editingMenu ? 'PUT' : 'POST'

        const response = await fetch(url, {
          method: method,
          headers: this.getAuthHeaders(false),
          body: formData
        })

        if (response.ok) {
          this.closeMenuForm()
          this.loadMenus()
          this.loadDashboardStats()
        } else {
          let errorMessage = 'Failed to save menu item'
          try {
            const errorData = await response.json()
            errorMessage = errorData.error || errorMessage
          } catch (e) {
            errorMessage = `Error ${response.status}: ${response.statusText || errorMessage}`
          }
          this.menuError = errorMessage
        }
      } catch (error) {
        console.error('Error saving menu:', error)
        this.menuError = 'Connection error: ' + error.message + '. Make sure the backend server is running on http://localhost:8080'
      } finally {
        this.isSavingMenu = false
      }
    },
    async deleteMenu(menuId) {
      if (!confirm('Are you sure you want to delete this menu item?')) {
        return
      }

      this.isDeletingMenu = true
      try {
        const response = await fetch(`http://localhost:8080/api/admin/menus/${menuId}`, {
          method: 'DELETE',
          headers: this.getAuthHeaders()
        })

        if (response.ok) {
          this.loadMenus()
          this.loadDashboardStats()
        } else {
          alert('Failed to delete menu item')
        }
      } catch (error) {
        alert('Error deleting menu item: ' + error.message)
      } finally {
        this.isDeletingMenu = false
      }
    },
    async loadUsers() {
      this.loadingUsers = true
      this.userError = null
      try {
        const response = await fetch('http://localhost:8080/api/admin/users', {
          headers: this.getAuthHeaders()
        })
        if (response.ok) {
          this.users = await response.json()
        } else if (response.status === 401) {
          alert('Session expired. Please login again.')
          this.handleLogout()
        } else {
          this.userError = 'Failed to load users'
        }
      } catch (error) {
        this.userError = 'Error loading users: ' + error.message
      } finally {
        this.loadingUsers = false
      }
    },
    async deleteUser(userId) {
      if (!confirm('Are you sure you want to delete this user?')) {
        return
      }

      this.isDeletingUser = true
      this.userError = null
      try {
        const response = await fetch(`http://localhost:8080/api/admin/users/${userId}`, {
          method: 'DELETE',
          headers: this.getAuthHeaders()
        })

        if (response.ok) {
          this.loadUsers()
          this.loadDashboardStats()
        } else {
          this.userError = 'Failed to delete user'
        }
      } catch (error) {
        this.userError = 'Error deleting user: ' + error.message
      } finally {
        this.isDeletingUser = false
      }
    },
    async loadReservations() {
      this.loadingReservations = true
      this.reservationError = null
      try {
        const response = await fetch('http://localhost:8080/api/admin/reservations', {
          headers: this.getAuthHeaders()
        })
        if (response.ok) {
          this.reservations = await response.json()
        } else if (response.status === 401) {
          alert('Session expired. Please login again.')
          this.handleLogout()
        } else {
          this.reservationError = 'Failed to load reservations'
        }
      } catch (error) {
        this.reservationError = 'Error loading reservations: ' + error.message
      } finally {
        this.loadingReservations = false
      }
    },
    async confirmReservation(reservationId) {
      if (!confirm('Are you sure you want to confirm this reservation? An email will be sent to the customer.')) {
        return
      }

      this.isConfirmingReservation = true
      this.reservationError = null
      try {
        const response = await fetch(`http://localhost:8080/api/admin/reservations/${reservationId}/confirm`, {
          method: 'PUT',
          headers: {
            ...this.getAuthHeaders(),
            'Content-Type': 'application/json'
          }
        })

        if (response.ok) {
          this.loadReservations()
          this.loadDashboardStats()
          alert('Reservation confirmed successfully! Confirmation email has been sent to the customer.')
        } else {
          let errorMessage = 'Failed to confirm reservation'
          try {
            const errorData = await response.json()
            errorMessage = errorData.error || errorMessage
          } catch (e) {
            errorMessage = `Error ${response.status}: ${response.statusText || errorMessage}`
          }
          this.reservationError = errorMessage
        }
      } catch (error) {
        this.reservationError = 'Error confirming reservation: ' + error.message
      } finally {
        this.isConfirmingReservation = false
      }
    },
    async cancelReservation(reservationId) {
      if (!confirm('Are you sure you want to cancel this reservation? A cancellation email will be sent to the customer.')) {
        return
      }

      this.isCancellingReservation = true
      this.reservationError = null
      try {
        const response = await fetch(`http://localhost:8080/api/admin/reservations/${reservationId}/cancel`, {
          method: 'PUT',
          headers: {
            ...this.getAuthHeaders(),
            'Content-Type': 'application/json'
          }
        })

        if (response.ok) {
          this.loadReservations()
          this.loadDashboardStats()
          alert('Reservation cancelled successfully! Cancellation email has been sent to the customer.')
        } else {
          let errorMessage = 'Failed to cancel reservation'
          try {
            const errorData = await response.json()
            errorMessage = errorData.error || errorMessage
          } catch (e) {
            errorMessage = `Error ${response.status}: ${response.statusText || errorMessage}`
          }
          this.reservationError = errorMessage
        }
      } catch (error) {
        this.reservationError = 'Error cancelling reservation: ' + error.message
      } finally {
        this.isCancellingReservation = false
      }
    },
    async deleteReservation(reservationId) {
      if (!confirm('Are you sure you want to delete this reservation?')) {
        return
      }

      this.isDeletingReservation = true
      this.reservationError = null
      try {
        const response = await fetch(`http://localhost:8080/api/admin/reservations/${reservationId}`, {
          method: 'DELETE',
          headers: this.getAuthHeaders()
        })

        if (response.ok) {
          this.loadReservations()
          this.loadDashboardStats()
        } else {
          this.reservationError = 'Failed to delete reservation'
        }
      } catch (error) {
        this.reservationError = 'Error deleting reservation: ' + error.message
      } finally {
        this.isDeletingReservation = false
      }
    },
    formatDate(dateString) {
      if (!dateString) return 'N/A'
      try {
        const date = new Date(dateString)
        return date.toLocaleDateString('en-US', { 
          year: 'numeric', 
          month: 'short', 
          day: 'numeric' 
        })
      } catch (e) {
        return dateString
      }
    },
    formatTime(timeString) {
      if (!timeString) return 'N/A'
      try {
        const time = timeString.split(':')
        const hours = parseInt(time[0])
        const minutes = time[1]
        const ampm = hours >= 12 ? 'PM' : 'AM'
        const displayHours = hours % 12 || 12
        return `${displayHours}:${minutes} ${ampm}`
      } catch (e) {
        return timeString
      }
    },
    async loadOrders() {
      this.loadingOrders = true
      this.orderError = null
      try {
        const response = await fetch('http://localhost:8080/api/orders/admin/all', {
          headers: this.getAuthHeaders()
        })
        if (response.ok) {
          this.orders = await response.json()
          this.orders.sort((a, b) => {
            const dateA = new Date(a.createdAt)
            const dateB = new Date(b.createdAt)
            return dateB - dateA
          })
          this.filteredOrders = [...this.orders]
          await this.loadCustomerNames()
        } else if (response.status === 401) {
          alert('Session expired. Please login again.')
          this.handleLogout()
        } else {
          this.orderError = 'Failed to load orders'
        }
      } catch (error) {
        this.orderError = 'Error loading orders: ' + error.message
      } finally {
        this.loadingOrders = false
      }
    },
    async loadCustomerNames() {
      try {
        const response = await fetch('http://localhost:8080/api/admin/users', {
          headers: this.getAuthHeaders()
        })
        if (response.ok) {
          const users = await response.json()
          users.forEach(user => {
            this.customerNames[user.id] = user.username || user.email || 'Unknown'
          })
        }
      } catch (error) {
        console.error('Error loading customer names:', error)
      }
    },
    getCustomerName(userId) {
      return this.customerNames[userId] || 'Loading...'
    },
    filterOrders() {
      if (!this.searchQuery.trim()) {
        this.filteredOrders = [...this.orders]
        return
      }
      
      const query = this.searchQuery.toLowerCase().trim()
      this.filteredOrders = this.orders.filter(order => 
        order.orderNumber.toString().toLowerCase().includes(query)
      )
    },
    clearSearch() {
      this.searchQuery = ''
      this.filteredOrders = [...this.orders]
    },
    formatCompactDate(dateString) {
      if (!dateString) return 'N/A'
      try {
        const date = new Date(dateString)
        const now = new Date()
        const diffDays = Math.floor((now - date) / (1000 * 60 * 60 * 24))
        
        if (diffDays === 0) {
          // Today - show time only
          return date.toLocaleTimeString('en-US', { 
            hour: '2-digit', 
            minute: '2-digit',
            hour12: true 
          })
        } else if (diffDays === 1) {
          // Yesterday
          return 'Yesterday'
        } else if (diffDays < 7) {
          // Within a week - show day name
          return date.toLocaleDateString('en-US', { weekday: 'short' })
        } else {
          // More than a week ago - show date
          return date.toLocaleDateString('en-US', { 
            month: 'short', 
            day: 'numeric' 
          })
        }
      } catch (e) {
        return dateString
      }
    },
    async completeOrder(orderId) {
      if (!confirm('Are you sure you want to mark this order as completed?')) {
        return
      }

      this.isCompletingOrder = true
      this.orderError = null
      try {
        const response = await fetch(`http://localhost:8080/api/orders/admin/${orderId}/status`, {
          method: 'PUT',
          headers: {
            ...this.getAuthHeaders(),
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ status: 'COMPLETED' })
        })

        if (response.ok) {
          this.loadOrders()
          this.loadDashboardStats()
          alert('Order marked as completed successfully!')
        } else {
          let errorMessage = 'Failed to complete order'
          try {
            const errorData = await response.json()
            errorMessage = errorData.error || errorMessage
          } catch (e) {
            errorMessage = `Error ${response.status}: ${response.statusText || errorMessage}`
          }
          this.orderError = errorMessage
        }
      } catch (error) {
        this.orderError = 'Error completing order: ' + error.message
      } finally {
        this.isCompletingOrder = false
      }
    },
    formatOrderDate(dateString) {
      if (!dateString) return 'N/A'
      try {
        const date = new Date(dateString)
        return date.toLocaleDateString('en-US', { 
          year: 'numeric', 
          month: 'short', 
          day: 'numeric',
          hour: '2-digit',
          minute: '2-digit'
        })
      } catch (e) {
        return dateString
      }
    },
    handleLogout() {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      this.$emit('show-login')
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&display=swap');

.admin-dashboard {
  min-height: 100vh;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 20% 80%, rgba(59, 130, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(6, 182, 212, 0.03) 0%, transparent 50%);
  background-attachment: fixed;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  color: #ffffff;
  font-size: 15px;
  line-height: 1.6;
}

.admin-dashboard p,
.admin-dashboard td {
  color: rgba(255, 255, 255, 0.85);
}

/* Mobile Sidebar Toggle */
.mobile-sidebar-toggle {
  display: none;
  position: fixed;
  top: 1rem;
  left: 1rem;
  z-index: 1000;
  background: rgba(59, 130, 246, 0.2);
  border: 1px solid rgba(59, 130, 246, 0.3);
  color: #3B82F6;
  border-radius: 8px;
  padding: 0.75rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.mobile-sidebar-toggle:hover {
  background: rgba(59, 130, 246, 0.3);
  transform: translateY(-2px);
}

/* Sidebar Overlay */
.sidebar-overlay {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  z-index: 999;
}

/* Admin Header */
.admin-header {
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  padding: 1.25rem 2rem;
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  position: sticky;
  top: 0;
  z-index: 100;
}

.admin-header-content {
  max-width: 1400px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
}

.admin-logo {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.logo-img {
  width: 50px;
  height: 50px;
  object-fit: contain;
}

.admin-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0;
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.admin-user-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-name {
  font-weight: 500;
  color: rgba(255, 255, 255, 0.8);
}

.btn-logout {
  background: rgba(255, 107, 107, 0.1);
  border: 1px solid rgba(255, 107, 107, 0.3);
  color: #FF6B6B;
  padding: 0.5rem 1.5rem;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn-logout:hover {
  background: rgba(255, 107, 107, 0.2);
  border-color: rgba(255, 107, 107, 0.5);
  transform: translateY(-2px);
}

/* Main Layout */
.admin-main {
  padding: 2rem;
  min-height: calc(100vh - 80px);
}

.admin-container {
  max-width: 1400px;
  margin: 0 auto;
  display: flex;
  gap: 2rem;
  min-height: calc(100vh - 160px);
}

/* Sidebar */
.admin-sidebar {
  width: 220px;
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  display: flex;
  flex-direction: column;
  height: fit-content;
  position: sticky;
  top: 100px;
  transition: transform 0.3s ease;
  z-index: 100;
}

.admin-sidebar.open {
  transform: translateX(0);
}

.sidebar-header {
  display: none;
  padding: 1.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  justify-content: space-between;
  align-items: center;
}

.sidebar-title {
  margin: 0;
  font-size: 1.25rem;
  font-weight: 600;
  color: #ffffff;
}

.sidebar-close {
  display: none;
  background: none;
  border: none;
  color: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 6px;
  transition: all 0.3s ease;
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.sidebar-close:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #ffffff;
}

.admin-nav {
  flex: 1;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.875rem;
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  transition: all 0.3s ease;
  font-weight: 500;
  position: relative;
}

.nav-item:hover {
  background: rgba(59, 130, 246, 0.1);
  color: #3B82F6;
}

.nav-item.active {
  background: rgba(59, 130, 246, 0.15);
  color: #3B82F6;
}

.nav-item.active::before {
  content: '';
  position: absolute;
  left: -1.5rem;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 60%;
  background: #3B82F6;
  border-radius: 0 2px 2px 0;
}

.nav-text {
  font-size: 0.9rem;
}

.sidebar-footer {
  padding: 1.5rem;
  border-top: 1px solid rgba(255, 255, 255, 0.08);
  margin-top: auto;
}

.current-user {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 0.9rem;
  color: white;
}

.user-details {
  flex: 1;
}

.user-email {
  margin: 0 0 0.25rem 0;
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.8);
  font-weight: 500;
}

.user-role {
  margin: 0;
  font-size: 0.75rem;
  color: rgba(255, 255, 255, 0.5);
}

/* Main Content */
.admin-content {
  flex: 1;
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
  border-radius: 16px;
  padding: 2.5rem;
  border: 1px solid rgba(255, 255, 255, 0.06);
  min-height: calc(100vh - 200px);
  min-width: 0;
  overflow: hidden;
}

.content-header {
  margin-bottom: 2.5rem;
}

.content-title {
  font-size: 1.75rem;
  font-weight: 700;
  margin: 0 0 0.75rem 0;
  color: #ffffff;
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.6);
}

.breadcrumb-item {
  transition: color 0.3s ease;
}

.breadcrumb-item.active {
  color: #3B82F6;
  font-weight: 500;
}

.breadcrumb-separator {
  color: rgba(255, 255, 255, 0.3);
}

/* Tab Content */
.tab-content-wrapper {
  background: transparent;
}

.tab-content {
  animation: fadeIn 0.3s ease;
}

.tab-content-inner {
  padding: 0;
  width: 100%;
  max-width: 100%;
  overflow: hidden;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Stats Grid */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
  margin-top: 1rem;
}

.stat-card {
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
  border-radius: 16px;
  padding: 1.5rem;
  display: flex;
  align-items: center;
  gap: 1.5rem;
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: none;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.stat-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #3B82F6, #06B6D4, #00D9FF);
}

.stat-card:hover {
  border-color: rgba(59, 130, 246, 0.2);
}

.stat-icon {
  font-size: 2.5rem;
  width: 70px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.2) 0%, rgba(6, 182, 212, 0.2) 100%);
  border-radius: 14px;
  border: 1px solid rgba(59, 130, 246, 0.3);
  flex-shrink: 0;
}

.stat-info {
  flex: 1;
}

.stat-info h3 {
  margin: 0 0 0.5rem 0;
  font-size: 0.875rem;
  opacity: 0.8;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: rgba(255, 255, 255, 0.7);
}

.stat-value {
  margin: 0;
  font-size: 2.25rem;
  font-weight: 700;
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* Menu Management Styles */
.menu-header {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 2rem;
}

.menu-header-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.category-filter {
  padding: 0.75rem 1rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  font-size: 14px;
  font-family: inherit;
  background: rgba(255, 255, 255, 0.04);
  color: #ffffff;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 160px;
}

.category-filter:focus {
  outline: none;
  border-color: rgba(59, 130, 246, 0.5);
  background: rgba(255, 255, 255, 0.06);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.category-filter option {
  background: #1a1a1a;
  color: #ffffff;
  padding: 0.5rem;
}

.view-toggle {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  padding: 0.25rem;
}

.view-btn {
  background: transparent;
  border: none;
  color: rgba(255, 255, 255, 0.5);
  padding: 0.5rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
}

.view-btn:hover {
  background: rgba(255, 255, 255, 0.08);
  color: rgba(255, 255, 255, 0.8);
}

.view-btn.active {
  background: rgba(59, 130, 246, 0.2);
  color: #3B82F6;
  border: 1px solid rgba(59, 130, 246, 0.3);
}

.view-btn.active:hover {
  background: rgba(59, 130, 246, 0.25);
  color: #60A5FA;
}

.btn-add-menu {
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(59, 130, 246, 0.3);
}

.btn-add-menu:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(59, 130, 246, 0.4);
  background: linear-gradient(135deg, #2563EB 0%, #0891B2 100%);
}

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  display: flex;
  align-items: flex-start;
  justify-content: center;
  z-index: 2000;
  padding: 1rem;
  overflow-y: auto;
}

.modal-content {
  background: rgba(18, 18, 18, 0.95);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 16px;
  width: 90%;
  max-width: 600px;
  margin-top: 0;
  box-shadow: 
    0 25px 50px -12px rgba(0, 0, 0, 0.5),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  position: relative;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.modal-header h3 {
  margin: 0;
  font-size: 1.5rem;
  color: #ffffff;
  font-weight: 600;
}

.btn-close {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  font-size: 1.5rem;
  color: rgba(255, 255, 255, 0.7);
  cursor: pointer;
  padding: 0;
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.btn-close:hover {
  background: rgba(255, 107, 107, 0.2);
  border-color: rgba(255, 107, 107, 0.5);
  color: #FF6B6B;
}

.menu-form {
  padding: 1.5rem;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
}

.form-group input,
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  font-size: 1rem;
  font-family: inherit;
  background: rgba(255, 255, 255, 0.04);
  color: #ffffff;
  transition: all 0.3s ease;
  cursor: pointer;
}

.form-group select option {
  background: #1a1a1a;
  color: #ffffff;
  padding: 0.5rem;
}

.form-group input:focus,
.form-group textarea:focus,
.form-group select:focus {
  outline: none;
  border-color: rgba(59, 130, 246, 0.5);
  background: rgba(255, 255, 255, 0.06);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.form-group input::placeholder,
.form-group textarea::placeholder {
  color: rgba(255, 255, 255, 0.3);
}

.form-group input[type="file"] {
  padding: 0.5rem;
}

.form-group input[type="checkbox"] {
  width: auto;
  margin-right: 0.5rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.image-preview,
.current-image {
  margin-top: 1rem;
}

.image-preview img,
.current-image img {
  max-width: 200px;
  max-height: 200px;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.current-image p {
  margin-bottom: 0.5rem;
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.6);
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 2rem;
}

.btn-cancel,
.btn-save {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  border: none;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s;
}

.btn-cancel {
  background: rgba(255, 255, 255, 0.05);
  color: rgba(255, 255, 255, 0.7);
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.btn-cancel:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(255, 255, 255, 0.2);
  color: #ffffff;
}

.btn-save {
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  color: white;
  box-shadow: 0 4px 15px rgba(59, 130, 246, 0.3);
}

.btn-save:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(59, 130, 246, 0.4);
  background: linear-gradient(135deg, #2563EB 0%, #0891B2 100%);
}

.btn-save:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.error-message {
  background: rgba(255, 107, 107, 0.1);
  color: #FF6B6B;
  padding: 0.75rem;
  border-radius: 8px;
  margin-bottom: 1rem;
  border: 1px solid rgba(255, 107, 107, 0.3);
}

/* Menu Grid */
.loading,
.empty-state {
  text-align: center;
  padding: 3rem;
  color: rgba(255, 255, 255, 0.6);
  font-size: 16px;
}

.menu-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  align-items: stretch;
}

.menu-card {
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.3s ease;
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
  box-shadow: none;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.menu-card:hover {
  border-color: rgba(59, 130, 246, 0.2);
}

.menu-image {
  width: 100%;
  height: 200px;
  background: rgba(26, 26, 26, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.menu-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.no-image {
  color: rgba(255, 255, 255, 0.4);
  font-size: 0.9rem;
}

.menu-info {
  padding: 1.5rem;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.menu-info h3 {
  margin: 0 0 0.5rem 0;
  font-size: 1.25rem;
  color: #ffffff;
  font-weight: 600;
}

.menu-description {
  color: rgba(255, 255, 255, 0.6);
  font-size: 0.95rem;
  margin: 0.5rem 0;
  line-height: 1.6;
  flex: 1;
  min-height: 60px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.menu-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 1rem 0;
}

.menu-category {
  background: rgba(59, 130, 246, 0.15);
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.85rem;
  color: #3B82F6;
  border: 1px solid rgba(59, 130, 246, 0.3);
}

.menu-price {
  font-size: 1.25rem;
  font-weight: 700;
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.menu-status {
  margin: 0.5rem 0;
}

.status-badge {
  display: inline-block;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 500;
}

.status-badge.available {
  background: rgba(6, 182, 212, 0.15);
  color: #06B6D4;
  border: 1px solid rgba(6, 182, 212, 0.3);
}

.status-badge.unavailable {
  background: rgba(255, 107, 107, 0.15);
  color: #FF6B6B;
  border: 1px solid rgba(255, 107, 107, 0.3);
}

.menu-actions {
  padding: 1rem 1.5rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  display: flex;
  gap: 0.5rem;
}

.btn-edit,
.btn-delete {
  flex: 1;
  padding: 0.625rem 1rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  font-size: 14px;
}

.btn-edit {
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  color: white;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
}

.btn-edit:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
  background: linear-gradient(135deg, #2563EB 0%, #0891B2 100%);
}

.btn-delete {
  background: rgba(255, 107, 107, 0.2);
  color: #FF6B6B;
  border: 1px solid rgba(255, 107, 107, 0.3);
}

.btn-delete:hover:not(:disabled) {
  background: rgba(255, 107, 107, 0.3);
  border-color: rgba(255, 107, 107, 0.5);
  transform: translateY(-2px);
}

.btn-delete:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* List View Styles */
.menu-list-container {
  overflow-x: auto;
  margin-top: 1.5rem;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
}

.menu-list-table {
  width: 100%;
  border-collapse: collapse;
  background: transparent;
}

.menu-list-table thead {
  background: rgba(59, 130, 246, 0.15);
  border-bottom: 2px solid rgba(59, 130, 246, 0.2);
}

.menu-list-table th {
  padding: 1.25rem;
  text-align: left;
  font-weight: 600;
  color: #ffffff;
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.menu-list-table td {
  padding: 1.25rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  color: rgba(255, 255, 255, 0.85);
  vertical-align: middle;
}

.menu-list-table tbody tr:nth-child(even) {
  background: rgba(255, 255, 255, 0.03);
}

.menu-list-table tbody tr:hover {
  background: rgba(59, 130, 246, 0.05);
}

.menu-list-image {
  width: 80px;
  padding: 0.75rem !important;
}

.menu-list-image img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.no-image-small {
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(26, 26, 26, 0.8);
  border-radius: 8px;
  color: rgba(255, 255, 255, 0.4);
  font-size: 0.75rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.menu-list-name {
  font-weight: 600;
  color: #ffffff !important;
  font-size: 1rem;
  min-width: 150px;
}

.menu-list-description {
  max-width: 300px;
  color: rgba(255, 255, 255, 0.6) !important;
  font-size: 0.9rem;
  line-height: 1.5;
}

.menu-list-price {
  font-weight: 700;
  font-size: 1.1rem;
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  white-space: nowrap;
}

.menu-list-actions {
  white-space: nowrap;
}

.menu-list-actions .btn-edit-small {
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  font-size: 0.85rem;
  transition: all 0.3s ease;
  margin-right: 0.5rem;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.3);
}

.menu-list-actions .btn-edit-small:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.4);
  background: linear-gradient(135deg, #2563EB 0%, #0891B2 100%);
}

.menu-list-actions .btn-delete-small {
  background: rgba(255, 107, 107, 0.2);
  color: #FF6B6B;
  border: 1px solid rgba(255, 107, 107, 0.3);
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.menu-list-actions .btn-delete-small:hover:not(:disabled) {
  background: rgba(255, 107, 107, 0.3);
  border-color: rgba(255, 107, 107, 0.5);
  transform: translateY(-2px);
}

.menu-list-actions .btn-delete-small:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* Order Management Styles */
.order-management-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  padding: 1rem;
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(10px);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  flex-wrap: wrap;
  gap: 1rem;
}

.search-container {
  flex: 1;
  min-width: 300px;
  max-width: 400px;
}

.search-input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.search-icon {
  position: absolute;
  left: 1rem;
  color: rgba(255, 255, 255, 0.5);
  pointer-events: none;
}

.search-input {
  width: 100%;
  padding: 0.75rem 1rem 0.75rem 3rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  color: white;
  font-size: 0.95rem;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: rgba(59, 130, 246, 0.5);
  background: rgba(255, 255, 255, 0.08);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.search-input::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.clear-search-btn {
  position: absolute;
  right: 1rem;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 6px;
  color: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  padding: 0.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
}

.clear-search-btn:hover {
  background: rgba(255, 107, 107, 0.2);
  color: #FF6B6B;
}

.order-stats {
  display: flex;
  gap: 1.5rem;
}

.order-stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
}

.order-stat-label {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.6);
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.order-stat-value {
  font-size: 1.25rem;
  font-weight: 700;
  color: #3B82F6;
}

.order-stat-value.pending {
  color: #FBBF24;
}

.order-stat-value.completed {
  color: #10B981;
}

.orders-table-container {
  overflow-x: auto;
  overflow-y: visible;
  margin-top: 1.5rem;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
  -webkit-overflow-scrolling: touch;
  width: 100%;
  max-width: 100%;
}

.orders-table {
  width: 100%;
  min-width: 1000px;
  border-collapse: collapse;
  background: transparent;
  table-layout: auto;
}

.orders-table thead {
  background: rgba(59, 130, 246, 0.15);
  border-bottom: 2px solid rgba(59, 130, 246, 0.2);
}

.orders-table th {
  padding: 1.25rem;
  text-align: left;
  font-weight: 600;
  color: #ffffff;
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.orders-table td {
  padding: 1.25rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  color: rgba(255, 255, 255, 0.85);
  vertical-align: middle;
}

.orders-table tbody tr:nth-child(even) {
  background: rgba(255, 255, 255, 0.03);
}

.orders-table tbody tr:hover {
  background: rgba(59, 130, 246, 0.05);
}

/* Compact Table Styles */
.orders-table.compact {
  font-size: 0.9rem;
}

.orders-table.compact th {
  padding: 0.875rem;
  font-size: 0.75rem;
  white-space: nowrap;
}

.orders-table.compact td {
  padding: 0.75rem 0.875rem;
}

.order-number-cell {
  min-width: 90px;
  max-width: 110px;
  vertical-align: middle;
}

.order-number {
  font-weight: 700;
  color: #ffffff;
  font-size: 0.9rem;
  white-space: nowrap;
}

.search-highlight {
  font-size: 0.7rem;
  background: rgba(59, 130, 246, 0.2);
  color: #3B82F6;
  padding: 0.125rem 0.5rem;
  border-radius: 12px;
  display: inline-block;
}

.customer-cell {
  max-width: 150px;
  min-width: 120px;
}

.customer-info {
  display: flex;
  flex-direction: column;
  gap: 0.125rem;
}

.customer-name {
  font-weight: 500;
  color: #ffffff;
  font-size: 0.875rem;
  word-break: break-word;
  overflow-wrap: break-word;
}

.order-type-badge {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  white-space: nowrap;
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

.table-cell {
  font-size: 0.85rem;
  color: rgba(255, 255, 255, 0.8);
  text-align: center;
  min-width: 60px;
  max-width: 80px;
}

.order-items-preview {
  max-width: 200px;
  min-width: 140px;
  width: 180px;
}

.order-item-preview {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.7);
  line-height: 1.4;
  white-space: normal;
  word-break: break-word;
  overflow-wrap: break-word;
}

.more-items {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.5);
  font-style: italic;
}

.order-total-cell {
  font-weight: 700;
  font-size: 1rem;
  background: linear-gradient(135deg, #3B82F6 0%, #06B6D4 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  white-space: nowrap;
  min-width: 90px;
}

.payment-method-badge {
  display: inline-block;
  padding: 0.25rem 0.65rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 500;
  background: rgba(139, 92, 246, 0.15);
  color: #8B5CF6;
  border: 1px solid rgba(139, 92, 246, 0.3);
  white-space: nowrap;
}

.order-date-cell {
  min-width: 90px;
  max-width: 110px;
}

.order-date-compact {
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.8);
  text-align: center;
  white-space: nowrap;
}

.order-status-badge {
  display: inline-block;
  padding: 0.3rem 0.7rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  white-space: nowrap;
}

.order-status-badge.pending {
  background: rgba(251, 191, 36, 0.15);
  color: #FBBF24;
  border: 1px solid rgba(251, 191, 36, 0.3);
}

.order-status-badge.confirmed {
  background: rgba(6, 182, 212, 0.15);
  color: #06B6D4;
  border: 1px solid rgba(6, 182, 212, 0.3);
}

.order-status-badge.completed {
  background: rgba(16, 185, 129, 0.15);
  color: #10B981;
  border: 1px solid rgba(16, 185, 129, 0.3);
}

.order-status-badge.cancelled {
  background: rgba(255, 107, 107, 0.15);
  color: #FF6B6B;
  border: 1px solid rgba(255, 107, 107, 0.3);
}

.btn-complete-order {
  background: rgba(16, 185, 129, 0.2);
  color: #10B981;
  border: 1px solid rgba(16, 185, 129, 0.3);
  padding: 0.5rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  min-width: 36px;
}

.btn-complete-order:hover:not(:disabled) {
  background: rgba(16, 185, 129, 0.3);
  border-color: rgba(16, 185, 129, 0.5);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

.btn-complete-order:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.orders-table .completed-indicator {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #10B981;
  width: 36px;
  height: 36px;
}

/* User Management Styles */
.users-table-container,
.reservations-table-container {
  overflow-x: auto;
  margin-top: 1.5rem;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(18, 18, 18, 0.6);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
}

.users-table,
.reservations-table {
  width: 100%;
  border-collapse: collapse;
  background: transparent;
}

.users-table thead,
.reservations-table thead {
  background: rgba(59, 130, 246, 0.15);
  border-bottom: 2px solid rgba(59, 130, 246, 0.2);
}

.users-table th,
.reservations-table th {
  padding: 1.25rem;
  text-align: left;
  font-weight: 600;
  color: #ffffff;
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  border-bottom: 2px solid rgba(255, 255, 255, 0.1);
}

.users-table td,
.reservations-table td {
  padding: 1.25rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  color: rgba(255, 255, 255, 0.85);
}

.users-table tbody tr:nth-child(even),
.reservations-table tbody tr:nth-child(even) {
  background: rgba(255, 255, 255, 0.03);
}

.users-table tr:hover,
.reservations-table tr:hover {
  background: rgba(59, 130, 246, 0.05);
}

.role-badge {
  display: inline-block;
  padding: 0.375rem 0.875rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.role-badge.admin {
  background: rgba(139, 92, 246, 0.15);
  color: #8B5CF6;
  border: 1px solid rgba(139, 92, 246, 0.3);
}

.role-badge.user {
  background: rgba(59, 130, 246, 0.15);
  color: #3B82F6;
  border: 1px solid rgba(59, 130, 246, 0.3);
}

/* Reservation Status Badge */
.reservation-status-badge {
  display: inline-block;
  padding: 0.375rem 0.875rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.reservation-status-badge.pending {
  background: rgba(251, 191, 36, 0.15);
  color: #FBBF24;
  border: 1px solid rgba(251, 191, 36, 0.3);
}

.reservation-status-badge.confirmed {
  background: rgba(6, 182, 212, 0.15);
  color: #06B6D4;
  border: 1px solid rgba(6, 182, 212, 0.3);
}

.reservation-status-badge.cancelled {
  background: rgba(255, 107, 107, 0.15);
  color: #FF6B6B;
  border: 1px solid rgba(255, 107, 107, 0.3);
}

/* Confirm Button */
.btn-confirm {
  background: rgba(6, 182, 212, 0.2);
  color: #06B6D4;
  border: 1px solid rgba(6, 182, 212, 0.3);
  padding: 0.5rem;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  min-width: 40px;
  height: 36px;
}

.btn-confirm:hover:not(:disabled) {
  background: rgba(6, 182, 212, 0.3);
  border-color: rgba(6, 182, 212, 0.5);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(6, 182, 212, 0.3);
}

.btn-confirm:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.confirmed-indicator {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #06B6D4;
  padding: 0.5rem;
}

.cancelled-indicator {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #FF6B6B;
  padding: 0.5rem;
}

.reservation-actions-group {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.btn-cancel-reservation {
  background: rgba(255, 107, 107, 0.2);
  color: #FF6B6B;
  border: 1px solid rgba(255, 107, 107, 0.3);
  padding: 0.5rem;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  min-width: 40px;
  height: 36px;
}

.btn-cancel-reservation:hover:not(:disabled) {
  background: rgba(255, 107, 107, 0.3);
  border-color: rgba(255, 107, 107, 0.5);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 107, 107, 0.3);
}

.btn-cancel-reservation:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.btn-delete-small {
  background: rgba(255, 107, 107, 0.2);
  color: #FF6B6B;
  border: 1px solid rgba(255, 107, 107, 0.3);
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.9rem;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn-delete-small:hover:not(:disabled) {
  background: rgba(255, 107, 107, 0.3);
  border-color: rgba(255, 107, 107, 0.5);
  transform: translateY(-2px);
}

.btn-delete-small:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* Responsive Design */
@media (max-width: 1024px) {
  .admin-container {
    gap: 1.5rem;
  }
  
  .admin-sidebar {
    width: 200px;
  }
  
  .admin-content {
    padding: 2rem;
  }
  
  .stats-grid {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }
  
  .order-management-header {
    flex-direction: column;
    align-items: stretch;
  }
  
  .search-container {
    min-width: 100%;
    max-width: 100%;
  }
  
  .order-stats {
    justify-content: space-between;
  }
  
  .orders-table.compact {
    font-size: 0.85rem;
  }
  
  .orders-table.compact th,
  .orders-table.compact td {
    padding: 0.75rem 0.5rem;
  }
}

/* MOBILE LAYOUT FIXES - Menu Section */
@media (max-width: 768px) {
  .mobile-sidebar-toggle {
    display: block;
  }
  
  .sidebar-overlay {
    display: block;
  }
  
  .admin-header {
    padding: 1rem;
  }
  
  .admin-main {
    padding: 1rem;
  }
  
  .admin-container {
    flex-direction: column;
    gap: 1rem;
  }
  
  .admin-sidebar {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    z-index: 1000;
    width: 280px;
    transform: translateX(-100%);
    border-radius: 0;
    border-right: 1px solid rgba(255, 255, 255, 0.08);
    height: 100vh;
    top: 0;
  }
  
  .admin-sidebar.open {
    transform: translateX(0);
  }
  
  .sidebar-header {
    display: flex;
  }
  
  .sidebar-close {
    display: flex;
  }
  
  .admin-content {
    padding: 1.5rem;
    min-height: auto;
    border-radius: 12px;
  }
  
  .nav-item.active::before {
    left: 0;
  }
  
  .admin-title {
    font-size: 1.25rem;
  }
  
  .content-title {
    font-size: 1.5rem;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  /* MENU HEADER MOBILE LAYOUT FIX */
  .menu-header {
    display: block;
    margin-bottom: 1.5rem;
    width: 100%;
  }
  
  .menu-header-actions {
    display: flex;
    flex-direction: row;
    align-items: center;
    flex-wrap: nowrap;
    gap: 0.75rem;
    width: 100%;
    overflow-x: auto;
    padding-bottom: 0.5rem;
    -webkit-overflow-scrolling: touch;
    scrollbar-width: thin;
    scrollbar-color: rgba(255, 255, 255, 0.1) transparent;
  }
  
  .menu-header-actions::-webkit-scrollbar {
    height: 4px;
  }
  
  .menu-header-actions::-webkit-scrollbar-track {
    background: rgba(255, 255, 255, 0.05);
    border-radius: 2px;
  }
  
  .menu-header-actions::-webkit-scrollbar-thumb {
    background: rgba(255, 255, 255, 0.1);
    border-radius: 2px;
  }
  
  .category-filter {
    min-width: 140px;
    max-width: 140px;
    flex-shrink: 0;
    padding: 0.625rem 0.75rem;
    font-size: 0.9rem;
    margin: 0;
  }
  
  .view-toggle {
    flex-shrink: 0;
    margin: 0;
  }
  
  .btn-add-menu {
    flex-shrink: 0;
    white-space: nowrap;
    padding: 0.625rem 1rem;
    font-size: 0.9rem;
    margin: 0;
    min-width: 130px;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
  
  .users-table-container,
  .reservations-table-container,
  .orders-table-container,
  .menu-list-container {
    overflow-x: scroll;
  }
  
  .users-table,
  .reservations-table,
  .orders-table,
  .menu-list-table {
    min-width: 1000px;
  }
  
  .orders-table-container {
    overflow-x: scroll;
  }
  
  .orders-table.compact {
    min-width: 950px;
  }
  
  .orders-table {
    min-width: 950px;
  }
  
  .order-stat-item {
    flex: 1;
  }
  
  /* Menu Grid Mobile Fix */
  .menu-grid {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
  
  .menu-list-container {
    margin-top: 1rem;
  }
}

/* For very small screens */
@media (max-width: 480px) {
  .admin-header-content {
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .admin-user-info {
    width: 100%;
    justify-content: space-between;
  }
  
  .admin-content {
    padding: 1rem;
  }
  
  .content-title {
    font-size: 1.25rem;
  }
  
  .breadcrumb {
    font-size: 0.8rem;
  }
  
  /* Menu Header Extra Small Screen Fix */
  .menu-header-actions {
    gap: 0.5rem;
  }
  
  .category-filter {
    min-width: 120px;
    max-width: 120px;
    font-size: 0.85rem;
    padding: 0.5rem 0.625rem;
  }
  
  .btn-add-menu {
    min-width: 110px;
    font-size: 0.85rem;
    padding: 0.5rem 0.75rem;
  }
  
  .view-btn {
    width: 32px;
    height: 32px;
    padding: 0.375rem;
  }
  
  .view-btn svg {
    width: 16px;
    height: 16px;
  }
  
  .order-stats {
    gap: 0.75rem;
  }
  
  .order-stat-label {
    font-size: 0.7rem;
  }
  
  .order-stat-value {
    font-size: 1rem;
  }
}

/* Dark scrollbar for sidebar */
.admin-sidebar::-webkit-scrollbar {
  width: 6px;
}

.admin-sidebar::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
}

.admin-sidebar::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 3px;
}

.admin-sidebar::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.2);
}
</style>