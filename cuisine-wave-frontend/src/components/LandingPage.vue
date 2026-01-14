<template>
  <div class="app">

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
            <a href="#" class="auth-link" @click.prevent="closeMobileMenu">Home</a>
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

    <!-- Hero Section -->
    <section class="hero" @mouseenter="stopAutoSlide" @mouseleave="startAutoSlide">
      <button class="slider-arrow slider-arrow-left" @click="previousSlide" aria-label="Previous slide">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </button>
      <button class="slider-arrow slider-arrow-right" @click="nextSlide" aria-label="Next slide">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M9 18L15 12L9 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </button>
      <div class="hero-content" :key="currentSlide">
        <transition name="fade" mode="out-in">
          <div class="hero-slide" :key="currentSlide">
            <div class="hero-left">
              <h2 class="hero-subtitle">{{ slides[currentSlide].subtitle }}</h2>
              <h1 class="hero-title">{{ slides[currentSlide].title }}</h1>
              <div class="hero-buttons">
                <button class="btn-red" @click="$emit('show-menu')">View Menu</button>
                <button class="btn-yellow" @click="$emit('show-reservation')">Make Reservation</button>
              </div>
              <div class="slider-dots slider-dots-desktop">
                <span 
                  v-for="(slide, index) in slides" 
                  :key="index"
                  class="dot" 
                  :class="{ active: currentSlide === index }"
                  @click="changeSlide(index)"
                ></span>
              </div>
            </div>
            <div class="hero-right">
              <img 
                :src="getHeroImage(currentSlide)" 
                :alt="slides[currentSlide].title"
                class="hero-image"
              />
              <div class="slider-dots slider-dots-mobile">
                <span 
                  v-for="(slide, index) in slides" 
                  :key="index"
                  class="dot" 
                  :class="{ active: currentSlide === index }"
                  @click="changeSlide(index)"
                ></span>
              </div>
            </div>
          </div>
        </transition>
      </div>
    </section>

    <!-- About Section -->
    <section class="about" ref="aboutSection">
      <div class="about-content">
        <div class="about-left" :class="{ 'animate-in': aboutImageVisible }">
          <div class="orange-band">
            <img src="./about.png" alt="About Sajian Nusantara" class="about-image" />
          </div>
        </div>
        <div class="about-right" :class="{ 'animate-in': aboutTextVisible }">
          <h2 class="section-title">About Sajian Nusantara</h2>
          <div class="title-separator">
            <span class="dot-yellow"></span>
            <span class="diamond"></span>
            <span class="dot-yellow"></span>
          </div>
          <p class="section-text">
            Sajian Nusantara is a Malaysian restaurant that serves a wide variety of traditional and modern dishes inspired by local flavours. The restaurant showcases the richness of Nusantara cuisine, known for its aromatic spices and authentic taste, making it suitable for customers of all backgrounds.
          </p>
          <p class="section-text">
            The menu at Sajian Nusantara features traditional favourites such as nasi lemak, nasi kerabu, and classic kampung-style dishes, alongside modern and Western selections to cater to diverse preferences. Each dish is prepared using fresh ingredients and carefully selected recipes to ensure consistent quality and delicious taste.
          </p>
          <p class="section-text">
            With a comfortable dining atmosphere and friendly service, Sajian Nusantara is committed to delivering a satisfying dining experience while introducing customers to the unique heritage of traditional Malaysian cuisine.
          </p>
        </div>
      </div>
    </section>

    <!-- Enjoy Section -->
    <section class="enjoy" ref="enjoySection">
      <div class="enjoy-content">
        <div class="enjoy-left" :class="{ 'animate-in': enjoyTextVisible }">
          <h2 class="section-title">A Journey of Authentic Flavours</h2>
          <div class="title-separator">
            <span class="dot-yellow"></span>
            <span class="diamond"></span>
            <span class="dot-yellow"></span>
          </div>
          <p class="section-text">
            At Sajian Nusantara, we invite you to experience an exceptional journey of taste through the rich and diverse flavours of Malaysian cuisine. Our menu features a wide variety of traditional and contemporary dishes, carefully prepared to celebrate the authenticity of local recipes and time-honoured cooking methods.
          </p>
          <p class="section-text">
            From classic favourites such as nasi lemak and nasi kerabu to flavourful noodles, western selections, and refreshing beverages, every dish is crafted using fresh ingredients and quality spices. Each meal reflects our passion for delivering consistent taste and satisfaction.
          </p>
          <p class="section-text">
            With a warm and welcoming atmosphere, Sajian Nusantara is the perfect place to enjoy hearty meals with family and friends while discovering the true essence of Malaysian flavours.
          </p>
          <button class="btn-red" @click="$emit('show-menu')">View Menu</button>
        </div>
        <div class="enjoy-right" :class="{ 'animate-in': enjoyImageVisible }">
          <div class="restaurant-images">
            <img src="./journey1.png" alt="Journey" class="restaurant-img" />
            <img src="./menu.png" alt="Menu" class="chef-img" />
          </div>
        </div>
      </div>
    </section>

    <!-- Menu Section -->
    <section class="menu" ref="menuSection">
      <div class="menu-header" :class="{ 'animate-in': menuVisible }">
        <h2 class="menu-title">Our Menu</h2>
        <div class="menu-separator">
          <span class="diamond"></span>
          <span class="diamond"></span>
          <span class="diamond"></span>
        </div>
      </div>
      <div v-if="loadingMenuItems" class="menu-loading" :class="{ 'animate-in': menuVisible }">
        <p>Loading delicious menu items...</p>
        </div>
      <div v-else class="menu-carousel-container" :class="{ 'animate-in': menuVisible }" @mouseenter="pauseAutoScroll" @mouseleave="resumeAutoScroll">
        <div class="menu-carousel" :style="{ transform: `translateX(${carouselOffset}px)` }">
          <div 
            v-for="(item, index) in displayedMenuItems" 
            :key="item.id || index"
            class="menu-item-card"
          >
            <div class="menu-item-image">
              <img 
                v-if="item.imageUrl" 
                :src="`http://localhost:8080${item.imageUrl}`" 
                :alt="item.name"
                @error="handleImageError($event)"
              />
              <div v-else class="menu-item-placeholder">
                <span>🍽️</span>
        </div>
        </div>
            <div class="menu-item-info">
              <h3 class="menu-item-name">{{ item.name }}</h3>
              <p class="menu-item-description" v-if="item.description">{{ item.description }}</p>
              <p class="menu-item-price">RM {{ parseFloat(item.price || 0).toFixed(2) }}</p>
              <span class="menu-item-category">{{ item.category }}</span>
        </div>
      </div>
        </div>
      </div>
      <div v-if="!loadingMenuItems && displayedMenuItems.length > 0" class="menu-dots">
        <span 
          v-for="(item, index) in displayedMenuItems.slice(0, Math.min(5, displayedMenuItems.length))" 
          :key="index"
          class="dot" 
          :class="{ active: Math.floor(Math.abs(carouselOffset) / 320) % displayedMenuItems.length === index }"
        ></span>
      </div>
    </section>

    <!-- Pickup Service Section -->
    <section class="delivery" ref="deliverySection">
      <div class="delivery-content">
        <div class="delivery-left" :class="{ 'animate-in': deliveryVisible }">
          <h2 class="section-title">Pickup Service</h2>
          <div class="title-separator">
            <span class="dot-yellow"></span>
            <span class="diamond"></span>
            <span class="dot-yellow"></span>
          </div>
          <p class="section-text">
            Enjoy the convenience of ordering ahead and picking up your favourite Malaysian dishes at your preferred time. Our pickup service allows you to skip the wait and enjoy freshly prepared, hot meals on your own schedule. Simply place your order online or by phone, and we will have it ready for collection.
          </p>
          <div class="pickup-location" style="margin-top: 25px; margin-bottom: 25px;">
            <div class="contact-item" style="color: rgba(255, 255, 255, 0.9); font-size: 16px; line-height: 1.6;">
              <svg class="contact-icon" width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" style="color: #FF9F1C; flex-shrink: 0; margin-top: 3px;">
                <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <circle cx="12" cy="10" r="3" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <span>Sajian Nusantara Restaurant, Jalan Bukit Bintang, Kuala Lumpur</span>
            </div>
          </div>
          <div class="delivery-fee">
            <h3 class="fee-title">Packaging Charges</h3>
            <div class="fee-item">
              <span>Packaging Fee - RM 2.00</span>
            </div>
          </div>
        </div>
        <div class="delivery-right" :class="{ 'animate-in': deliveryVisible }">
          <div class="map-container">
            <iframe
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3983.855665803933!2d101.69361947475482!3d3.1392582968402416!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc49c701efeae7%3A0xf4d98e5b2f1c287d!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2sus!4v1698765432100!5m2!1sen!2sus"
              width="100%"
              height="100%"
              style="border:0; border-radius: 10px;"
              allowfullscreen=""
              loading="lazy"
              referrerpolicy="no-referrer-when-downgrade"
              class="map-iframe"
            ></iframe>
          </div>
        </div>
      </div>
    </section>

   

    <!-- Gallery Section -->
    <section class="gallery" ref="gallerySection">
      <div class="gallery-header" :class="{ 'animate-in': galleryVisible }">
        <h2 class="section-title">Our Gallery</h2>
        <div class="title-separator">
          <span class="dot-yellow"></span>
          <span class="diamond"></span>
          <span class="diamond"></span>
          <span class="dot-yellow"></span>
        </div>
      </div>
      <div class="gallery-filters" :class="{ 'animate-in': galleryVisible }">
        <span 
          class="filter-item" 
          :class="{ active: selectedGalleryCategory === 'All' }"
          @click="selectedGalleryCategory = 'All'"
        >
          All
        </span>
        <template v-for="(category, index) in availableCategories" :key="category">
        <span class="filter-separator">|</span>
          <span 
            class="filter-item" 
            :class="{ active: selectedGalleryCategory === category }"
            @click="selectedGalleryCategory = category"
          >
            {{ capitalizeCategory(category) }}
          </span>
        </template>
      </div>
      <div v-if="loadingGalleryItems" class="gallery-loading">
        <p>Loading gallery items...</p>
        </div>
      <div v-else-if="filteredGalleryItems.length === 0" class="gallery-empty">
        <p>No items found in this category.</p>
        </div>
      <div v-else class="gallery-grid" :class="{ 'animate-in': galleryVisible }">
        <div 
          v-for="(item, index) in filteredGalleryItems.slice(0, 6)" 
          :key="item.id"
          class="gallery-item"
          :style="{ animationDelay: `${index * 0.1}s` }"
        >
          <div class="gallery-img">
            <img 
              v-if="item.imageUrl" 
              :src="`http://localhost:8080${item.imageUrl}`" 
              :alt="item.name"
              @error="handleGalleryImageError($event)"
            />
            <div v-else class="gallery-placeholder">
              <span>🍽️</span>
          </div>
            <div class="gallery-overlay">
              <h3 class="gallery-item-name">{{ item.name }}</h3>
              <p class="gallery-item-price">RM {{ parseFloat(item.price || 0).toFixed(2) }}</p>
        </div>
        </div>
        </div>
      </div>
    </section>

    <!-- Banner Section -->
    <section class="banner" ref="bannerSection">
      <div class="banner-content" :class="{ 'animate-in': bannerVisible }">
        <h2 class="banner-title">Your Order Will Be Confirmed In Real Time</h2>
        <p class="banner-subtitle"> We offer Pick-up </p>
        <div class="banner-buttons">
          <button class="btn-red" @click="$emit('show-reservation')">Table Reservation</button>
          <button class="btn-orange" @click="$emit('show-menu')">See Menu & Order</button>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <div class="footer-content">
        <!-- Company Column -->
        <div class="footer-column">
          <h3 class="footer-title">Company</h3>
          <div class="footer-title-line"></div>
          <ul class="footer-links">
            <li>
              <svg class="link-arrow" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9 18l6-6-6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <a href="#">About Us</a>
            </li>
            <li>
              <svg class="link-arrow" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9 18l6-6-6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <a href="#">Contact Us</a>
            </li>
            <li>
              <svg class="link-arrow" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9 18l6-6-6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <a href="#" @click.prevent="$emit('show-reservation')">Reservation</a>
            </li>
            <li>
              <svg class="link-arrow" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9 18l6-6-6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <a href="#">Privacy Policy</a>
            </li>
            <li>
              <svg class="link-arrow" width="12" height="12" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M9 18l6-6-6-6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              <a href="#">Terms & Condition</a>
            </li>
          </ul>
        </div>

        <!-- Contact Column -->
        <div class="footer-column">
          <h3 class="footer-title">Contact</h3>
          <div class="footer-title-line"></div>
          <div class="contact-item">
            <svg class="contact-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <circle cx="12" cy="10" r="3" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>Sajian Nusantara Restaurant, Jalan Bukit Bintang, Kuala Lumpur</span>
          </div>
          <div class="contact-item">
            <svg class="contact-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>+6014 633 0286</span>
          </div>
          <div class="contact-item">
            <svg class="contact-icon" width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              <path d="M22 6l-10 7L2 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            <span>info@sajiannusantara.com</span>
          </div>
          <div class="social-icons">
            <a href="#" class="social-icon" aria-label="Twitter">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path d="M23 3a10.9 10.9 0 01-3.14 1.53 4.48 4.48 0 00-7.86 3v1A10.66 10.66 0 013 4s-4 9 5 13a11.64 11.64 0 01-7 2c9 5 20 0 20-11.5a4.5 4.5 0 00-.08-.83A7.72 7.72 0 0023 3z"/>
              </svg>
            </a>
            <a href="#" class="social-icon" aria-label="Facebook">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path d="M18 2h-3a5 5 0 00-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 011-1h3z"/>
              </svg>
            </a>
            <a href="#" class="social-icon" aria-label="YouTube">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path d="M22.54 6.42a2.78 2.78 0 00-1.94-2C18.88 4 12 4 12 4s-6.88 0-8.6.46a2.78 2.78 0 00-1.94 2A29 29 0 001 11.75a29 29 0 00.46 5.33A2.78 2.78 0 003.4 19c1.72.46 8.6.46 8.6.46s6.88 0 8.6-.46a2.78 2.78 0 001.94-2 29 29 0 00.46-5.25 29 29 0 00-.46-5.33z"/>
                <polygon points="9.75 15.02 15.5 11.75 9.75 8.48 9.75 15.02"/>
              </svg>
            </a>
            <a href="#" class="social-icon" aria-label="LinkedIn">
              <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                <path d="M16 8a6 6 0 016 6v7h-4v-7a2 2 0 00-2-2 2 2 0 00-2 2v7h-4v-7a6 6 0 016-6zM2 9h4v12H2z"/>
                <circle cx="4" cy="4" r="2"/>
              </svg>
            </a>
          </div>
        </div>

        <!-- Opening Column -->
        <div class="footer-column">
          <h3 class="footer-title">Opening</h3>
          <div class="footer-title-line"></div>
          <div class="opening-hours-footer">
            <div class="hours-item">
              <span class="hours-text">Tuesday - Saturday</span>
              <span class="hours-time">11AM - 10PM</span>
            </div>
            <div class="hours-item">
              <span class="hours-text">Sunday</span>
              <span class="hours-time">10AM - 10PM</span>
            </div>
          </div>
        </div>

        <!-- Newsletter Column -->
        <div class="footer-column">
          <h3 class="footer-title">Newsletter</h3>
          <div class="footer-title-line"></div>
          <p class="newsletter-description">
            Experience authentic Malaysian flavors delivered to your inbox. Stay updated with our latest specials and exclusive offers.
          </p>
          <div class="newsletter-form">
            <input type="email" placeholder="Your email" class="newsletter-input" v-model="newsletterEmail" />
            <button class="newsletter-submit" @click.prevent="handleNewsletterSignup">SIGNUP</button>
          </div>
        </div>
      </div>

      <!-- Footer Bottom Bar -->
      <div class="footer-bottom-bar">
        <div class="footer-bottom-content">
          <div class="copyright">
            © Sajian Nusantara Restaurant, All Right Reserved. Designed By HTML Codex
          </div>
          <div class="footer-bottom-links">
            <a href="#">Home</a>
            <a href="#">Cookies</a>
            <a href="#">Help</a>
            <a href="#">FQAS</a>
          </div>
          <button class="scroll-to-top" @click="scrollToTop" aria-label="Scroll to top">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M18 15l-6-6-6 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </button>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'CuisineWaveRestaurant',
  data() {
    return {
      isMobileMenuOpen: false,
      currentSlide: 0,
      autoSlideInterval: null,
      newsletterEmail: '',
      menuItems: [],
      displayedMenuItems: [],
      loadingMenuItems: false,
      carouselOffset: 0,
      carouselSpeed: 1,
      autoScrollInterval: null,
      isAutoScrollPaused: false,
      selectedGalleryCategory: 'All',
      loadingGalleryItems: false,
      isUserMenuOpen: false,
      currentUser: null,
      aboutImageVisible: false,
      aboutTextVisible: false,
      enjoyImageVisible: false,
      enjoyTextVisible: false,
      menuVisible: false,
      deliveryVisible: false,
      galleryVisible: false,
      bannerVisible: false,
      slides: [
        {
          subtitle: 'Taste & Feel the Spirit of Best Quality',
          title: 'Sajian Nusantara',
          button1: 'View Menu',
          button2: 'Make Reservation',
          imageClass: 'slide-pizza'
        },
        {
          subtitle: 'Authentic Malaysian Flavours',
          title: 'Traditional Recipes',
          button1: 'View Menu',
          button2: 'Make Reservation',
          imageClass: 'slide-nasi-goreng'
        },
        {
          subtitle: 'Fresh Ingredients Daily',
          title: 'Premium Quality',
          button1: 'View Menu',
          button2: 'Make Reservation',
          imageClass: 'slide-sate'
        },
        {
          subtitle: 'Experience the Richness',
          title: 'Malaysian Cuisine',
          button1: 'View Menu',
          button2: 'Make Reservation',
          imageClass: 'slide-rendang'
        }
      ]
    }
  },
  mounted() {
    this.startAutoSlide()
    this.loadMenuItems()
    this.checkUserLogin()
    // Close user menu when clicking outside
    document.addEventListener('click', this.handleClickOutside)
    // Setup scroll animations
    this.$nextTick(() => {
      this.setupScrollAnimations()
    })
  },
  beforeUnmount() {
    this.stopAutoSlide()
    this.stopAutoScroll()
    document.removeEventListener('click', this.handleClickOutside)
    // Clean up intersection observers
    if (this.aboutObserver) {
      this.aboutObserver.disconnect()
    }
    if (this.enjoyObserver) {
      this.enjoyObserver.disconnect()
    }
    if (this.menuObserver) {
      this.menuObserver.disconnect()
    }
    if (this.deliveryObserver) {
      this.deliveryObserver.disconnect()
    }
    if (this.galleryObserver) {
      this.galleryObserver.disconnect()
    }
    if (this.bannerObserver) {
      this.bannerObserver.disconnect()
    }
  },
  methods: {
    toggleMobileMenu() {
      this.isMobileMenuOpen = !this.isMobileMenuOpen
    },
    closeMobileMenu() {
      this.isMobileMenuOpen = false
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
      window.scrollTo({
        top: document.documentElement.scrollHeight,
        behavior: 'smooth'
      })
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
    checkUserLogin() {
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
    handleClickOutside(event) {
      const userMenuContainer = event.target.closest('.user-menu-container')
      if (!userMenuContainer && this.isUserMenuOpen) {
        this.isUserMenuOpen = false
      }
    },
    getUserInitial() {
      if (!this.currentUser) return 'U'
      const name = this.currentUser.username || this.currentUser.email || 'User'
      return name.charAt(0).toUpperCase()
    },
    changeSlide(index) {
      this.currentSlide = index
      // Restart auto-slide after manual change
      this.stopAutoSlide()
      this.startAutoSlide()
    },
    nextSlide() {
      this.currentSlide = (this.currentSlide + 1) % this.slides.length
      this.stopAutoSlide()
      this.startAutoSlide()
    },
    previousSlide() {
      this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length
      this.stopAutoSlide()
      this.startAutoSlide()
    },
    startAutoSlide() {
      this.autoSlideInterval = setInterval(() => {
        this.currentSlide = (this.currentSlide + 1) % this.slides.length
      }, 5000) // Change slide every 5 seconds
    },
    stopAutoSlide() {
      if (this.autoSlideInterval) {
        clearInterval(this.autoSlideInterval)
        this.autoSlideInterval = null
      }
    },
    handleNewsletterSignup() {
      if (this.newsletterEmail && this.newsletterEmail.includes('@')) {
        // Handle newsletter signup - you can add API call here
        alert('Thank you for subscribing to our newsletter!')
        this.newsletterEmail = ''
      } else {
        alert('Please enter a valid email address')
      }
    },
    scrollToTop() {
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      })
    },
    async loadMenuItems() {
      this.loadingMenuItems = true
      this.loadingGalleryItems = true
      try {
        const response = await fetch('http://localhost:8080/api/menu')
        if (response.ok) {
          const menus = await response.json()
          // Filter only available items
          this.menuItems = menus.filter(item => item.available !== false)
          // Randomly select items to display (shuffle and take up to 12)
          this.selectRandomMenuItems()
          // Start auto-scrolling
          this.startAutoScroll()
        } else {
          console.error('Failed to load menu items')
        }
      } catch (error) {
        console.error('Error loading menu items:', error)
      } finally {
        this.loadingMenuItems = false
        this.loadingGalleryItems = false
      }
    },
    selectRandomMenuItems() {
      // Shuffle array and take up to 12 items
      const shuffled = [...this.menuItems].sort(() => Math.random() - 0.5)
      this.displayedMenuItems = shuffled.slice(0, Math.min(12, shuffled.length))
      // Duplicate items for seamless infinite scroll
      this.displayedMenuItems = [...this.displayedMenuItems, ...this.displayedMenuItems]
    },
    startAutoScroll() {
      this.stopAutoScroll()
      if (this.displayedMenuItems.length === 0) return
      
      this.autoScrollInterval = setInterval(() => {
        if (!this.isAutoScrollPaused && this.displayedMenuItems.length > 0) {
          this.carouselOffset -= this.carouselSpeed
          
          // Reset position when scrolled past one set of items for seamless loop
          // Each card is 300px wide + 20px gap = 320px total
          const itemWidth = 320
          const itemsPerSet = this.displayedMenuItems.length / 2
          const totalWidth = itemsPerSet * itemWidth
          
          if (Math.abs(this.carouselOffset) >= totalWidth) {
            // Temporarily disable transition for seamless reset
            const carousel = document.querySelector('.menu-carousel')
            if (carousel) {
              carousel.style.transition = 'none'
              this.carouselOffset = 0
              // Re-enable transition after a brief moment
              setTimeout(() => {
                if (carousel) {
                  carousel.style.transition = ''
                }
              }, 50)
            } else {
              this.carouselOffset = 0
            }
          }
        }
      }, 20) // Update every 20ms for smooth scrolling
    },
    stopAutoScroll() {
      if (this.autoScrollInterval) {
        clearInterval(this.autoScrollInterval)
        this.autoScrollInterval = null
      }
    },
    pauseAutoScroll() {
      this.isAutoScrollPaused = true
    },
    resumeAutoScroll() {
      this.isAutoScrollPaused = false
    },
    handleImageError(event) {
      event.target.style.display = 'none'
      if (event.target.nextElementSibling) {
        event.target.nextElementSibling.style.display = 'flex'
      }
    },
    handleGalleryImageError(event) {
      event.target.style.display = 'none'
      const placeholder = event.target.nextElementSibling
      if (placeholder) {
        placeholder.style.display = 'flex'
      }
    },
    capitalizeCategory(category) {
      if (!category) return ''
      // Capitalize first letter of each word
      return category
        .split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
        .join(' ')
    },
    getHeroImage(slideIndex) {
      const imageNames = ['1.png', '2.png', '3.png', '4.png']
      const imageName = imageNames[slideIndex] || imageNames[0]
      return new URL(`./${imageName}`, import.meta.url).href
    },
    setupScrollAnimations() {
      // Check if Intersection Observer is supported
      if (!('IntersectionObserver' in window)) {
        // Fallback: show animations immediately
        this.aboutImageVisible = true
        this.aboutTextVisible = true
        this.enjoyImageVisible = true
        this.enjoyTextVisible = true
        this.menuVisible = true
        this.deliveryVisible = true
        this.galleryVisible = true
        this.bannerVisible = true
        return
      }

      // About section observer
      if (this.$refs.aboutSection) {
        this.aboutObserver = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                // Stagger the animations
                setTimeout(() => {
                  this.aboutImageVisible = true
                }, 100)
                setTimeout(() => {
                  this.aboutTextVisible = true
                }, 300)
                // Unobserve after animation triggers
                this.aboutObserver.unobserve(entry.target)
              }
            })
          },
          {
            threshold: 0.2,
            rootMargin: '0px 0px -50px 0px'
          }
        )
        this.aboutObserver.observe(this.$refs.aboutSection)
      }

      // Enjoy section observer
      if (this.$refs.enjoySection) {
        this.enjoyObserver = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                // Stagger the animations
                setTimeout(() => {
                  this.enjoyTextVisible = true
                }, 100)
                setTimeout(() => {
                  this.enjoyImageVisible = true
                }, 300)
                // Unobserve after animation triggers
                this.enjoyObserver.unobserve(entry.target)
              }
            })
          },
          {
            threshold: 0.2,
            rootMargin: '0px 0px -50px 0px'
          }
        )
        this.enjoyObserver.observe(this.$refs.enjoySection)
      }

      // Menu section observer
      if (this.$refs.menuSection) {
        this.menuObserver = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                setTimeout(() => {
                  this.menuVisible = true
                }, 100)
                this.menuObserver.unobserve(entry.target)
              }
            })
          },
          {
            threshold: 0.2,
            rootMargin: '0px 0px -50px 0px'
          }
        )
        this.menuObserver.observe(this.$refs.menuSection)
      }

      // Delivery section observer
      if (this.$refs.deliverySection) {
        this.deliveryObserver = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                setTimeout(() => {
                  this.deliveryVisible = true
                }, 100)
                this.deliveryObserver.unobserve(entry.target)
              }
            })
          },
          {
            threshold: 0.2,
            rootMargin: '0px 0px -50px 0px'
          }
        )
        this.deliveryObserver.observe(this.$refs.deliverySection)
      }

      // Gallery section observer
      if (this.$refs.gallerySection) {
        this.galleryObserver = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                setTimeout(() => {
                  this.galleryVisible = true
                }, 100)
                this.galleryObserver.unobserve(entry.target)
              }
            })
          },
          {
            threshold: 0.2,
            rootMargin: '0px 0px -50px 0px'
          }
        )
        this.galleryObserver.observe(this.$refs.gallerySection)
      }

      // Banner section observer
      if (this.$refs.bannerSection) {
        this.bannerObserver = new IntersectionObserver(
          (entries) => {
            entries.forEach((entry) => {
              if (entry.isIntersecting) {
                setTimeout(() => {
                  this.bannerVisible = true
                }, 100)
                this.bannerObserver.unobserve(entry.target)
              }
            })
          },
          {
            threshold: 0.2,
            rootMargin: '0px 0px -50px 0px'
          }
        )
        this.bannerObserver.observe(this.$refs.bannerSection)
      }
    }
  },
  computed: {
    availableCategories() {
      // Get unique categories from menu items
      const categories = [...new Set(this.menuItems
        .filter(item => item.category && item.available !== false)
        .map(item => item.category)
      )]
      // Sort categories alphabetically
      return categories.sort()
    },
    filteredGalleryItems() {
      if (this.selectedGalleryCategory === 'All') {
        // Return random items from all categories
        const shuffled = [...this.menuItems].sort(() => Math.random() - 0.5)
        return shuffled.slice(0, 6)
      }
      // Filter by selected category
      const filtered = this.menuItems.filter(item => 
        item.category === this.selectedGalleryCategory && item.available !== false
      )
      // Shuffle and limit to 6 items
      const shuffled = [...filtered].sort(() => Math.random() - 0.5)
      return shuffled.slice(0, 6)
    },
    isLoggedIn() {
      return !!this.currentUser && !!localStorage.getItem('token')
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&family=Playfair+Display:wght@400;500;600;700;800;900&family=Cormorant+Garamond:wght@300;400;500;600;700&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  border: none;
  outline: none;
  overflow-x: hidden;
}

.app {
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 20% 80%, rgba(139, 92, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 159, 28, 0.03) 0%, transparent 50%);
  background-attachment: fixed;
  color: #ffffff;
  min-height: 100vh;
  width: 100%;
  max-width: 100%;
  margin: 0;
  padding: 0;
  border: none;
  outline: none;
  overflow-x: hidden;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  perspective: 1000px;
  position: relative;
}

.app::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: 
    linear-gradient(90deg, transparent 0%, rgba(255, 255, 255, 0.01) 50%, transparent 100%);
  animation: shimmer 3s infinite;
  pointer-events: none;
  z-index: 0;
}

@keyframes shimmer {
  0% { transform: translateX(-100%); }
  100% { transform: translateX(100%); }
}

/* Remove borders from Vue app wrapper */
[data-v-app],
div[data-v-app] {
  margin: 0 !important;
  padding: 0 !important;
  border: none !important;
  outline: none !important;
  width: 100% !important;
  max-width: 100% !important;
}

/* Top Bar */
.top-bar {
  background-color: #dc3545;
  color: white;
  text-align: center;
  padding: 10px 0;
  font-size: 14px;
  font-weight: 500;
  letter-spacing: 0.3px;
}

/* Header */
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

.nav-links {
  display: flex;
  align-items: center;
  gap: 10px;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 5px 10px;
  transition: color 0.3s;
  font-weight: 500;
  font-size: 15px;
  letter-spacing: 0.3px;
}

.nav-link.active {
  color: #ffffff;
  font-weight: 600;
  position: relative;
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

.separator {
  color: #555;
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
}

/* Buttons */
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
  position: relative;
  overflow: hidden;
  transform: translateY(0) translateZ(0);
}

.btn-red::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.btn-red:hover {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px) translateZ(0);
  box-shadow: 
    0 10px 25px rgba(139, 92, 246, 0.3),
    0 4px 12px rgba(0, 0, 0, 0.4);
}

.btn-red:hover::before {
  left: 100%;
}

.btn-red:active {
  transform: translateY(0) translateZ(0);
  box-shadow: 
    0 2px 8px rgba(139, 92, 246, 0.3),
    inset 0 2px 4px rgba(0, 0, 0, 0.2);
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
    0 4px 20px rgba(255, 159, 28, 0.3),
    0 2px 8px rgba(0, 0, 0, 0.3);
  position: relative;
  overflow: hidden;
  transform: translateY(0) translateZ(0);
}

.btn-yellow::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.5s;
}

.btn-yellow:hover {
  background: linear-gradient(135deg, #FFB84D 0%, #FF9F1C 100%);
  transform: translateY(-2px) translateZ(0);
  box-shadow: 
    0 10px 30px rgba(255, 159, 28, 0.4),
    0 4px 12px rgba(0, 0, 0, 0.4);
}

.btn-yellow:hover::before {
  left: 100%;
}

.btn-yellow:active {
  transform: translateY(0) translateZ(0);
  box-shadow: 
    0 2px 8px rgba(255, 159, 28, 0.3),
    inset 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* User Menu */
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
  cursor: pointer;
}

.user-menu-item:hover {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

.user-menu-item svg {
  color: rgba(255, 255, 255, 0.7);
  transition: color 0.2s ease;
}

.user-menu-item:hover svg {
  color: white;
}

.btn-orange {
  background: linear-gradient(135deg, #FF9F1C 0%, #FF8C00 100%);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 10px;
  cursor: pointer;
  font-size: 14px;
  font-weight: 600;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
  letter-spacing: 0.3px;
  transition: all 0.3s ease;
}

.btn-orange:hover {
  background: linear-gradient(135deg, #FFB84D 0%, #FF9F1C 100%);
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(255, 159, 28, 0.3);
}

/* Hero Section */
.hero {
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 30% 70%, rgba(139, 92, 246, 0.05) 0%, transparent 50%),
    radial-gradient(circle at 70% 30%, rgba(255, 159, 28, 0.05) 0%, transparent 50%);
  padding: 100px 20px;
  position: relative;
  min-height: 700px;
  width: 100%;
  overflow: hidden;
  transform-style: preserve-3d;
}

.hero::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: 
    radial-gradient(circle, rgba(139, 92, 246, 0.03) 0%, transparent 70%);
  animation: rotate 20s linear infinite;
  pointer-events: none;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

/* Slider Arrow Buttons */
.slider-arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 50px;
  height: 50px;
  background-color: rgba(255, 255, 255, 0.1);
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  z-index: 10;
  transition: all 0.3s ease;
  color: #ffffff;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
}

.slider-arrow:hover {
  background-color: rgba(139, 92, 246, 0.2);
  border-color: #8B5CF6;
  color: #8B5CF6;
  transform: translateY(-50%) scale(1.1);
  box-shadow: 0 4px 15px rgba(139, 92, 246, 0.3);
}

.slider-arrow:active {
  transform: translateY(-50%) scale(0.95);
}

.slider-arrow-left {
  left: 30px;
}

.slider-arrow-right {
  right: 30px;
}

.slider-arrow svg {
  width: 24px;
  height: 24px;
  stroke-width: 2.5;
}

.hero-content {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 100%;
  margin: 0 auto;
  padding: 0;
  gap: 20px;
  position: relative;
  min-height: 500px;
  text-align: center;
}

.hero-content > * {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.hero-slide {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  gap: 20px;
  text-align: center;
}

/* Fade transition */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.hero-left {
  flex: 1;
  max-width: 500px;
  text-align: center;
  margin: 0 auto 0 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 0 20px;
}

.hero-left > * {
  text-align: center;
  width: 100%;
  max-width: 100%;
}

.hero-subtitle {
  font-size: 18px;
  color: #ccc;
  margin-bottom: 10px;
  font-weight: 400;
  letter-spacing: 0.5px;
  line-height: 1.6;
  text-align: center;
  width: 100%;
}

.hero-title {
  font-size: 64px;
  font-weight: 800;
  background: linear-gradient(135deg, #8B5CF6 0%, #FF9F1C 50%, #8B5CF6 100%);
  background-size: 200% auto;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 30px;
  line-height: 1.1;
  letter-spacing: -1px;
  font-family: 'Playfair Display', 'Cormorant Garamond', 'Georgia', serif;
  text-align: center;
  width: 100%;
  text-shadow: 0 0 40px rgba(139, 92, 246, 0.3);
  animation: shimmer 3s ease-in-out infinite;
  filter: drop-shadow(0 4px 20px rgba(139, 92, 246, 0.4));
}

@keyframes shimmer {
  0%, 100% { background-position: 0% center; }
  50% { background-position: 100% center; }
}

.hero-buttons {
  display: flex;
  gap: 15px;
  justify-content: center;
  align-items: center;
}

.slider-dots {
  position: relative;
  display: flex;
  gap: 10px;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
}

.slider-dots-mobile {
  display: none;
}

.dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: #555;
  cursor: pointer;
  transition: all 0.3s ease;
}

.dot:hover {
  background-color: #888;
  transform: scale(1.2);
}

.dot.active {
  background: linear-gradient(135deg, #8B5CF6, #FF9F1C);
  transform: scale(1.3);
}

/* Pizza Section */
.hero-right {
  flex: 1;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 0 20px;
}

.hero-image {
  max-width: 100%;
  max-height: 500px;
  width: auto;
  height: auto;
  object-fit: contain;
  animation: rotateImage 20s linear infinite;
}

@keyframes rotateImage {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.hero-image:hover {
  animation-play-state: paused;
}

.pizza-container {
  position: relative;
  width: 500px;
  height: 500px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 auto;
  transform-style: preserve-3d;
  animation: float 6s ease-in-out infinite;
  filter: drop-shadow(0 20px 60px rgba(139, 92, 246, 0.3));
}

@keyframes float {
  0%, 100% { transform: translateY(0px) rotateY(0deg); }
  50% { transform: translateY(-20px) rotateY(5deg); }
}

.pizza {
  width: 400px;
  height: 400px;
  border-radius: 50%;
  background: radial-gradient(circle, #ff6b6b 0%, #c92a2a 100%);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) translateZ(20px);
  box-shadow: 
    0 20px 60px rgba(0, 0, 0, 0.6),
    0 0 80px rgba(255, 107, 107, 0.3),
    inset 0 -20px 40px rgba(0, 0, 0, 0.3),
    inset 0 20px 40px rgba(255, 255, 255, 0.1);
  transition: transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.pizza:hover {
  transform: translate(-50%, -50%) translateZ(40px) scale(1.05);
}

.pizza::before {
  content: '';
  position: absolute;
  top: 20%;
  left: 20%;
  width: 60%;
  height: 60%;
  border-radius: 50%;
  background: rgba(255,255,255,0.9);
}

.pizza::after {
  content: '';
  position: absolute;
  top: 25%;
  left: 25%;
  width: 50%;
  height: 50%;
  border-radius: 50%;
  background: 
    radial-gradient(circle at 30% 30%, #8b4513 2px, transparent 2px),
    radial-gradient(circle at 70% 40%, #8b4513 2px, transparent 2px),
    radial-gradient(circle at 50% 60%, #8b4513 2px, transparent 2px),
    radial-gradient(circle at 20% 70%, #8b4513 2px, transparent 2px),
    radial-gradient(circle at 80% 70%, #8b4513 2px, transparent 2px);
  background-size: 50px 50px;
}

.ingredients {
  position: absolute;
  width: 100%;
  height: 100%;
}

.ingredient {
  position: absolute;
}

.garlic {
  width: 20px;
  height: 20px;
  background-color: #f5f5dc;
  border-radius: 50%;
  top: 10%;
  right: 15%;
}

.garlic:nth-child(2) {
  top: 15%;
  right: 20%;
}

.chili {
  width: 30px;
  height: 40px;
  background: linear-gradient(180deg, #dc3545 0%, #8b0000 100%);
  border-radius: 20px 20px 5px 5px;
  top: 5%;
  right: 10%;
}

.chili:nth-child(4) {
  bottom: 10%;
  right: 15%;
  transform: rotate(15deg);
}

.basil {
  width: 25px;
  height: 25px;
  background-color: #228b22;
  border-radius: 50% 50% 50% 0;
  top: 20%;
  right: 5%;
}

.basil:nth-child(6) {
  bottom: 20%;
  right: 8%;
}

.mushroom {
  width: 25px;
  height: 20px;
  background-color: #8b7355;
  border-radius: 50% 50% 0 0;
  bottom: 15%;
  right: 25%;
}

.tomato-slice {
  width: 40px;
  height: 5px;
  background: radial-gradient(circle, #ff6b6b 0%, #dc3545 100%);
  border-radius: 50%;
  bottom: 5%;
  right: 30%;
}

/* Slide-specific image styles */
.slide-pizza .pizza {
  background: radial-gradient(circle, #ff6b6b 0%, #c92a2a 100%);
}

.slide-nasi-goreng .pizza {
  background: linear-gradient(135deg, #f5deb3 0%, #daa520 100%);
}

.slide-nasi-goreng .pizza::before {
  background: linear-gradient(135deg, #ffffff 0%, #f5deb3 100%);
}

.slide-nasi-goreng .pizza::after {
  background: 
    radial-gradient(circle at 30% 30%, #8b4513 2px, transparent 2px),
    radial-gradient(circle at 70% 40%, #228b22 2px, transparent 2px),
    radial-gradient(circle at 50% 60%, #ff6b6b 2px, transparent 2px),
    radial-gradient(circle at 20% 70%, #ffa500 2px, transparent 2px),
    radial-gradient(circle at 80% 70%, #8b4513 2px, transparent 2px);
  background-size: 50px 50px;
}

.slide-sate .pizza {
  background: linear-gradient(135deg, #8b4513 0%, #654321 100%);
}

.slide-sate .pizza::before {
  background: linear-gradient(135deg, #daa520 0%, #b8860b 100%);
}

.slide-sate .pizza::after {
  background: 
    radial-gradient(circle at 30% 30%, #ff6b6b 3px, transparent 3px),
    radial-gradient(circle at 70% 40%, #ff6b6b 3px, transparent 3px),
    radial-gradient(circle at 50% 60%, #ff6b6b 3px, transparent 3px),
    radial-gradient(circle at 20% 70%, #ff6b6b 3px, transparent 3px),
    radial-gradient(circle at 80% 70%, #ff6b6b 3px, transparent 3px);
  background-size: 40px 40px;
}

.slide-rendang .pizza {
  background: linear-gradient(135deg, #8b4513 0%, #654321 100%);
}

.slide-rendang .pizza::before {
  background: linear-gradient(135deg, #654321 0%, #4a2c2a 100%);
}

.slide-rendang .pizza::after {
  background: 
    radial-gradient(circle at 30% 30%, #8b4513 3px, transparent 3px),
    radial-gradient(circle at 70% 40%, #654321 3px, transparent 3px),
    radial-gradient(circle at 50% 60%, #8b4513 3px, transparent 3px),
    radial-gradient(circle at 20% 70%, #654321 3px, transparent 3px),
    radial-gradient(circle at 80% 70%, #8b4513 3px, transparent 3px);
  background-size: 45px 45px;
}

/* About Section */
.about {
  padding: 120px 20px;
  background: 
    linear-gradient(180deg, #1a1a1a 0%, #0a0a0a 100%),
    radial-gradient(circle at 20% 30%, rgba(139, 92, 246, 0.03) 0%, transparent 50%),
    radial-gradient(circle at 80% 70%, rgba(255, 159, 28, 0.03) 0%, transparent 50%);
  position: relative;
  overflow: hidden;
  width: 100%;
  transform-style: preserve-3d;
}

.about::before {
  display: none;
}

.about::after {
  display: none;
}

.about-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 100%;
  margin: 0 auto;
  gap: 50px;
}

.about-left {
  flex: 1;
  position: relative;
  z-index: 1;
  opacity: 0;
  transform: translateX(-30px) scale(0.95);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1), transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
  will-change: opacity, transform;
}

.about-left.animate-in {
  opacity: 1;
  transform: translateX(0) scale(1);
  will-change: auto;
}

.orange-band {
  width: 100%;
  height: 500px;
  position: relative;
  clip-path: polygon(0 0, 70% 0, 100% 100%, 0 100%);
  box-shadow: 
    0 20px 60px rgba(139, 92, 246, 0.3);
  transform: translateZ(10px);
  overflow: hidden;
}

.about-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  transition: transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
}

.about-left.animate-in .about-image {
  animation: fadeInScale 1s cubic-bezier(0.4, 0, 0.2, 1) 0.2s both;
}

@keyframes gradientShift {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

.food-bowls {
  position: relative;
  width: 100%;
  height: 100%;
}

.large-bowl {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
  border-radius: 50%;
  position: absolute;
  top: 50%;
  left: 30%;
  transform: translate(-50%, -50%);
  box-shadow: 0 10px 30px rgba(0,0,0,0.5);
}

.large-bowl::before {
  content: '';
  position: absolute;
  top: 10%;
  left: 10%;
  width: 80%;
  height: 80%;
  border-radius: 50%;
  background: 
    linear-gradient(135deg, #228b22 0%, #32cd32 100%),
    linear-gradient(45deg, #ff6b6b 0%, #dc3545 100%),
    linear-gradient(225deg, #87ceeb 0%, #4682b4 100%);
  background-size: 33% 33%, 33% 33%, 33% 33%;
  background-position: top left, top right, bottom;
}

.small-bowls {
  position: absolute;
  right: 10%;
  top: 20%;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.small-bowl {
  width: 120px;
  height: 120px;
  background-color: #2a2a2a;
  border-radius: 50%;
  box-shadow: 0 5px 15px rgba(0,0,0,0.5);
}

.bowl-1::before {
  content: '';
  position: absolute;
  width: 100px;
  height: 100px;
  background: radial-gradient(circle, #8b4513 0%, #654321 100%);
  border-radius: 50%;
  top: 10px;
  left: 10px;
}

.bowl-2::before {
  content: '';
  position: absolute;
  width: 100px;
  height: 100px;
  background: linear-gradient(45deg, #ffc107 0%, #ff9800 100%);
  border-radius: 50%;
  top: 10px;
  left: 10px;
}

.bowl-3::before {
  content: '';
  position: absolute;
  width: 100px;
  height: 100px;
  background: linear-gradient(135deg, #ff6b6b 0%, #dc3545 100%);
  border-radius: 50%;
  top: 10px;
  left: 10px;
}

.about-right {
  flex: 1;
  opacity: 0;
  transform: translateX(30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s;
  will-change: opacity, transform;
}

.about-right.animate-in {
  opacity: 1;
  transform: translateX(0);
  will-change: auto;
}

.about-right.animate-in .section-title {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s both;
}

.about-right.animate-in .title-separator {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s both;
}

.about-right.animate-in .section-text:nth-child(3) {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.5s both;
}

.about-right.animate-in .section-text:nth-child(4) {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.6s both;
}

.about-right.animate-in .section-text:nth-child(5) {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.7s both;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.section-title {
  font-size: 48px;
  font-weight: 800;
  margin-bottom: 20px;
  background: linear-gradient(135deg, #ffffff 0%, #8B5CF6 50%, #FF9F1C 100%);
  background-size: 200% auto;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  position: relative;
  z-index: 1;
  letter-spacing: -1px;
  line-height: 1.2;
  font-family: 'Playfair Display', 'Cormorant Garamond', 'Georgia', serif;
  filter: drop-shadow(0 2px 10px rgba(139, 92, 246, 0.3));
  animation: titleShimmer 4s ease-in-out infinite;
  text-align: center;
}

@keyframes titleShimmer {
  0%, 100% { background-position: 0% center; }
  50% { background-position: 100% center; }
}

.title-separator {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.dot-yellow {
  width: 8px;
  height: 8px;
  background: linear-gradient(135deg, #8B5CF6, #FF9F1C);
  border-radius: 50%;
}

.diamond {
  width: 20px;
  height: 20px;
  background: linear-gradient(135deg, #8B5CF6, #FF9F1C);
  transform: rotate(45deg);
}

.section-text {
  font-size: 16px;
  line-height: 1.8;
  color: #ccc;
  margin-bottom: 20px;
  font-weight: 400;
  letter-spacing: 0.2px;
  text-align: justify;
}

/* Enjoy Section */
.enjoy {
  padding: 100px 20px;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 30% 30%, rgba(139, 92, 246, 0.02) 0%, transparent 50%);
  width: 100%;
}

.enjoy-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: 100%;
  margin: 0 auto;
  gap: 50px;
}

.enjoy-left {
  flex: 1;
  opacity: 0;
  transform: translateX(-30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s;
  will-change: opacity, transform;
}

.enjoy-left.animate-in {
  opacity: 1;
  transform: translateX(0);
  will-change: auto;
}

.enjoy-left.animate-in .section-title {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s both;
}

.enjoy-left.animate-in .title-separator {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s both;
}

.enjoy-left.animate-in .section-text:nth-child(3) {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.5s both;
}

.enjoy-left.animate-in .section-text:nth-child(4) {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.6s both;
}

.enjoy-left.animate-in .section-text:nth-child(5) {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.7s both;
}

.enjoy-left.animate-in .btn-red {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.8s both;
}

.enjoy-right {
  flex: 1;
  position: relative;
  opacity: 0;
  transform: translateX(30px) scale(0.95);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s;
  will-change: opacity, transform;
}

.enjoy-right.animate-in {
  opacity: 1;
  transform: translateX(0) scale(1);
  will-change: auto;
}

.enjoy-right.animate-in .restaurant-img {
  animation: fadeInScale 1s cubic-bezier(0.4, 0, 0.2, 1) 0.5s both;
}

.enjoy-right.animate-in .chef-img {
  animation: fadeInScale 1s cubic-bezier(0.4, 0, 0.2, 1) 0.7s both;
}

@keyframes fadeInScale {
  from {
    opacity: 0;
    transform: scale(0.9) translateY(20px);
  }
  to {
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}

.restaurant-images {
  position: relative;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.restaurant-img {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 10px;
  display: block;
  transition: transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.restaurant-img:hover {
  transform: scale(1.02);
}

.chef-img {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 10px;
  display: block;
  transition: transform 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}

.chef-img:hover {
  transform: scale(1.02);
}

.divine-logo {
  position: absolute;
  top: 20px;
  right: 20px;
  z-index: 10;
}

.logo-circle {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  background: linear-gradient(135deg, #8B5CF6, #FF9F1C);
  padding: 5px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  box-shadow: 0 10px 30px rgba(139, 92, 246, 0.3);
}

.logo-circle::before {
  content: '';
  position: absolute;
  inset: 5px;
  border-radius: 50%;
  background-color: #1a1a1a;
  z-index: 0;
}

.logo-circle > * {
  position: relative;
  z-index: 1;
}

.chef-hat-icon {
  width: 40px;
  height: 30px;
  background-color: white;
  border-radius: 50% 50% 0 0;
  margin-bottom: 10px;
  position: relative;
}

.chef-hat-icon::after {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 50%;
  transform: translateX(-50%);
  width: 30px;
  height: 5px;
  background-color: white;
}

.logo-text-divine {
  font-size: 24px;
  font-style: italic;
  color: white;
  font-weight: 700;
  font-family: 'Playfair Display', 'Georgia', serif;
  letter-spacing: 0.5px;
}

.logo-text-restaurant {
  font-size: 10px;
  color: white;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: 500;
}

/* Menu Section */
.menu {
  padding: 120px 20px;
  background: 
    linear-gradient(180deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(ellipse at center, rgba(139, 92, 246, 0.03) 0%, transparent 70%);
  text-align: center;
  position: relative;
  overflow: hidden;
  width: 100%;
  transform-style: preserve-3d;
}

.menu-header {
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1), transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
  will-change: opacity, transform;
}

.menu-header.animate-in {
  opacity: 1;
  transform: translateY(0);
  will-change: auto;
}

.menu-header.animate-in .menu-title {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s both;
}

.menu-header.animate-in .menu-separator {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s both;
}

.menu-carousel-container {
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s;
  will-change: opacity, transform;
}

.menu-carousel-container.animate-in {
  opacity: 1;
  transform: translateY(0);
  will-change: auto;
}

.menu-loading {
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1), transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
  will-change: opacity, transform;
}

.menu-loading.animate-in {
  opacity: 1;
  transform: translateY(0);
  will-change: auto;
}

.menu-loading {
  color: #ccc;
  font-size: 18px;
  padding: 60px 0;
}

.menu-carousel-container {
  position: relative;
  width: 100%;
  overflow: hidden;
  margin: 40px 0;
  padding: 20px 0;
}

.menu-carousel {
  display: flex;
  gap: 20px;
  transition: transform 0.3s ease-out;
  will-change: transform;
}

.menu-item-card {
  flex: 0 0 300px;
  background: rgba(18, 18, 18, 0.85);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 
    0 20px 50px rgba(0, 0, 0, 0.5),
    0 0 60px rgba(139, 92, 246, 0.2),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.08);
  transition: all 0.3s ease;
  transform-style: preserve-3d;
}

.menu-item-card:hover {
  transform: translateY(-10px) scale(1.02);
  box-shadow: 
    0 30px 70px rgba(0, 0, 0, 0.7),
    0 0 80px rgba(139, 92, 246, 0.3),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
}

.menu-item-image {
  width: 100%;
  height: 200px;
  overflow: hidden;
  position: relative;
  background: linear-gradient(135deg, #2a2a2a 0%, #1a1a1a 100%);
}

.menu-item-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.menu-item-card:hover .menu-item-image img {
  transform: scale(1.1);
}

.menu-item-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 60px;
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
}

.menu-item-info {
  padding: 20px;
  text-align: left;
}

.menu-item-name {
  font-size: 20px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 8px;
  font-family: 'Playfair Display', 'Georgia', serif;
  letter-spacing: 0.3px;
  line-height: 1.3;
}

.menu-item-description {
  font-size: 14px;
  color: #ccc;
  line-height: 1.6;
  margin-bottom: 12px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  min-height: 40px;
}

.menu-item-price {
  font-size: 22px;
  font-weight: 700;
  color: #FF9F1C;
  margin-bottom: 8px;
  letter-spacing: 0.3px;
}

.menu-item-category {
  display: inline-block;
  padding: 4px 12px;
  background: linear-gradient(135deg, rgba(139, 92, 246, 0.2), rgba(255, 159, 28, 0.2));
  border: 1px solid rgba(139, 92, 246, 0.3);
  border-radius: 12px;
  font-size: 12px;
  color: #8B5CF6;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.menu::before {
  display: none;
}

.menu::after {
  display: none;
}

.menu-title {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 20px;
  color: white;
  font-family: 'Playfair Display', 'Georgia', serif;
  position: relative;
  z-index: 1;
  letter-spacing: -0.5px;
  line-height: 1.3;
}

.menu-separator {
  display: flex;
  justify-content: center;
  gap: 5px;
  margin-bottom: 60px;
}

.menu-categories {
  display: flex;
  justify-content: center;
  gap: 40px;
  margin-bottom: 40px;
  flex-wrap: wrap;
}

.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  z-index: 1;
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  transform-style: preserve-3d;
}

.menu-item:hover {
  transform: translateY(-15px) translateZ(20px) scale(1.05);
}

.menu-image {
  width: 200px;
  height: 200px;
  border-radius: 50%;
  margin-bottom: 20px;
  box-shadow: 
    0 20px 50px rgba(0, 0, 0, 0.6),
    0 0 60px rgba(139, 92, 246, 0.2),
    inset 0 -10px 30px rgba(0, 0, 0, 0.4),
    inset 0 10px 30px rgba(255, 255, 255, 0.1);
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  transform: translateZ(0);
  position: relative;
  overflow: hidden;
}

.menu-item:hover .menu-image {
  box-shadow: 
    0 30px 70px rgba(0, 0, 0, 0.7),
    0 0 80px rgba(139, 92, 246, 0.3),
    inset 0 -10px 30px rgba(0, 0, 0, 0.4),
    inset 0 10px 30px rgba(255, 255, 255, 0.15);
  transform: translateZ(30px) rotateY(10deg);
}

.salad-bowl {
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
  position: relative;
}

.salad-bowl::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  border-radius: 50%;
  background: 
    radial-gradient(circle at 30% 30%, #228b22 0%, transparent 50%),
    radial-gradient(circle at 70% 40%, #dc3545 0%, transparent 50%),
    radial-gradient(circle at 50% 60%, #87ceeb 0%, transparent 50%),
    linear-gradient(135deg, #f5f5dc 0%, #ffffff 100%);
}

.rice-bowl {
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
  position: relative;
}

.rice-bowl::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  border-radius: 50%;
  background: 
    linear-gradient(135deg, #ffffff 0%, #f5f5dc 100%),
    radial-gradient(circle at 30% 30%, #228b22 0%, transparent 30%),
    radial-gradient(circle at 70% 50%, #ff6b6b 0%, transparent 30%);
}

.chicken-plate {
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
  position: relative;
}

.chicken-plate::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  border-radius: 50%;
  background: 
    linear-gradient(135deg, #8b7355 0%, #654321 100%),
    radial-gradient(circle at 50% 50%, #228b22 0%, transparent 40%);
}

.dessert-plate {
  background: linear-gradient(135deg, #6a6a6a 0%, #4a4a4a 100%);
  position: relative;
}

.dessert-plate::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  border-radius: 50%;
  background: 
    radial-gradient(circle, #ffffff 0%, #f0f0f0 100%),
    radial-gradient(circle at 50% 30%, #dc3545 0%, transparent 50%);
}

.menu-category {
  font-size: 18px;
  color: white;
  font-family: 'Playfair Display', 'Georgia', serif;
  margin-bottom: 10px;
  font-weight: 600;
  letter-spacing: 0.3px;
}

.menu-pattern {
  width: 40px;
  height: 10px;
  background: repeating-linear-gradient(
    90deg,
    #8B5CF6 0px,
    #8B5CF6 5px,
    transparent 5px,
    transparent 10px
  );
  margin-top: 10px;
}

.menu-item.active .active-line {
  width: 100px;
  height: 2px;
  background: linear-gradient(90deg, #8B5CF6, #FF9F1C);
  margin-top: 5px;
}

.menu-dots {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
}

/* Delivery Section */
.delivery {
  padding: 100px 20px;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 70% 70%, rgba(255, 159, 28, 0.02) 0%, transparent 50%);
  width: 100%;
}

.delivery-left {
  opacity: 0;
  transform: translateX(-30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s;
  will-change: opacity, transform;
}

.delivery-left.animate-in {
  opacity: 1;
  transform: translateX(0);
  will-change: auto;
}

.delivery-left.animate-in .section-title {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s both;
}

.delivery-left.animate-in .title-separator {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s both;
}

.delivery-left.animate-in .section-text {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.5s both;
}

.delivery-right {
  opacity: 0;
  transform: translateX(30px) scale(0.95);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s;
  will-change: opacity, transform;
}

.delivery-right.animate-in {
  opacity: 1;
  transform: translateX(0) scale(1);
  will-change: auto;
}

.delivery-right.animate-in .map-container {
  animation: fadeInScale 1s cubic-bezier(0.4, 0, 0.2, 1) 0.5s both;
}

.delivery-content {
  display: flex;
  justify-content: space-between;
  gap: 50px;
  width: 100%;
  max-width: 100%;
  margin: 0 auto;
}

.delivery-left {
  flex: 1;
}

.delivery-fee {
  margin-top: 30px;
}

.fee-title {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 15px;
  color: white;
  letter-spacing: 0.3px;
}

.fee-item {
  color: #FF9F1C;
  font-size: 16px;
  margin-bottom: 10px;
}

.delivery-right {
  flex: 1;
}

.map-container {
  width: 100%;
  height: 500px;
  background-color: #2a2a2a;
  border-radius: 10px;
  position: relative;
  overflow: hidden;
}

.map-iframe {
  width: 100%;
  height: 100%;
  border: none;
  border-radius: 10px;
  display: block;
}

/* Opening Hours Section */
.opening-hours {
  padding: 100px 20px;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%);
  position: relative;
  width: 100%;
}

.opening-content {
  width: 100%;
  max-width: 100%;
  margin: 0 auto;
  position: relative;
}

.opening-images {
  display: flex;
  gap: 20px;
  margin-bottom: 40px;
}

.opening-img-left,
.opening-img-right {
  flex: 1;
  height: 300px;
  background: linear-gradient(135deg, #2a2a2a 0%, #1a1a1a 100%);
  border-radius: 10px;
  position: relative;
  overflow: hidden;
}

.opening-img-left::before,
.opening-img-right::before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background: 
    linear-gradient(45deg, #3a3a3a 0%, transparent 50%),
    linear-gradient(135deg, #228b22 0%, transparent 50%);
  background-size: 200% 200%;
}

.opening-center {
  background-color: #2a2a2a;
  padding: 60px 40px;
  border-radius: 10px;
  text-align: center;
  margin-bottom: 40px;
}

.hours-panel {
  margin: 40px 0;
}

.hours-section {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #444;
}

.hours-section:last-child {
  border-bottom: none;
}

.hours-label {
  color: #8B5CF6;
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 10px;
  letter-spacing: 0.3px;
}

.hours-days {
  color: white;
  font-size: 16px;
  margin-bottom: 5px;
  font-weight: 500;
  letter-spacing: 0.2px;
}

.hours-time {
  color: #ccc;
  font-size: 16px;
  font-weight: 400;
  letter-spacing: 0.2px;
}

.theme-slider {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  margin-top: 30px;
}

.sun-icon {
  font-size: 24px;
}

.slider-track {
  width: 200px;
  height: 8px;
  background-color: #4a4a4a;
  border-radius: 4px;
  position: relative;
}

.slider-handle {
  width: 20px;
  height: 20px;
  background-color: white;
  border-radius: 50%;
  position: absolute;
  top: 50%;
  left: 30%;
  transform: translateY(-50%);
  box-shadow: 0 2px 5px rgba(0,0,0,0.3);
}

.divine-logo-bottom {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

/* Gallery Section */
.gallery {
  padding: 100px 20px;
  background: 
    linear-gradient(135deg, #0a0a0a 0%, #1a1a1a 50%, #0a0a0a 100%),
    radial-gradient(circle at 50% 50%, rgba(139, 92, 246, 0.02) 0%, transparent 50%);
  text-align: center;
  position: relative;
  overflow: hidden;
  width: 100%;
}

.gallery-header {
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1), transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
  will-change: opacity, transform;
}

.gallery-header.animate-in {
  opacity: 1;
  transform: translateY(0);
  will-change: auto;
}

.gallery-header.animate-in .section-title {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s both;
}

.gallery-header.animate-in .title-separator {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s both;
}

.gallery-filters {
  opacity: 0;
  transform: translateY(20px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.3s;
  will-change: opacity, transform;
}

.gallery-filters.animate-in {
  opacity: 1;
  transform: translateY(0);
  will-change: auto;
}

.gallery-grid {
  opacity: 0;
  transform: translateY(30px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s, transform 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s;
  will-change: opacity, transform;
}

.gallery-grid.animate-in {
  opacity: 1;
  transform: translateY(0);
  will-change: auto;
}

.gallery-item {
  opacity: 0;
  transform: translateY(20px) scale(0.95);
  animation: fadeInUpScale 0.6s cubic-bezier(0.4, 0, 0.2, 1) both;
}

.gallery-grid.animate-in .gallery-item {
  opacity: 1;
  transform: translateY(0) scale(1);
}

@keyframes fadeInUpScale {
  from {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.gallery::before {
  display: none;
}

.gallery::after {
  display: none;
}

.gallery-filters {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-bottom: 50px;
  flex-wrap: wrap;
  position: relative;
  z-index: 1;
}

.filter-item {
  color: white;
  cursor: pointer;
  padding: 5px 15px;
  transition: all 0.3s;
  font-weight: 500;
  font-size: 15px;
  letter-spacing: 0.3px;
  position: relative;
  user-select: none;
}

.filter-item:hover {
  color: #8B5CF6;
}

.filter-item.active {
  color: #8B5CF6;
  font-weight: 600;
  position: relative;
}

.filter-item.active::after {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, #8B5CF6, #FF9F1C);
}

.filter-separator {
  color: #555;
}

.gallery-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  width: 100%;
  max-width: 100%;
  margin: 0 auto;
}

.gallery-item {
  position: relative;
  overflow: hidden;
  border-radius: 10px;
  z-index: 1;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.gallery-item:hover {
  transform: translateY(-5px);
}

.gallery-img {
  width: 100%;
  height: 300px;
  background: linear-gradient(135deg, #2a2a2a 0%, #1a1a1a 100%);
  border-radius: 10px;
  position: relative;
  overflow: hidden;
}

.gallery-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.gallery-item:hover .gallery-img img {
  transform: scale(1.1);
}

.gallery-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 80px;
  background: linear-gradient(135deg, #4a4a4a 0%, #2a2a2a 100%);
}

.gallery-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.9) 0%, rgba(0, 0, 0, 0.5) 50%, transparent 100%);
  padding: 20px;
  transform: translateY(100%);
  transition: transform 0.3s ease;
}

.gallery-item:hover .gallery-overlay {
  transform: translateY(0);
}

.gallery-item-name {
  font-size: 18px;
  font-weight: 700;
  color: #ffffff;
  margin-bottom: 8px;
  font-family: 'Playfair Display', 'Georgia', serif;
}

.gallery-item-price {
  font-size: 20px;
  font-weight: 700;
  color: #FF9F1C;
}

.gallery-loading,
.gallery-empty {
  color: #ccc;
  font-size: 18px;
  padding: 60px 0;
  text-align: center;
}

.stir-fry::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  background: 
    radial-gradient(circle at 30% 30%, #228b22 0%, transparent 40%),
    radial-gradient(circle at 70% 40%, #ff6b6b 0%, transparent 40%),
    radial-gradient(circle at 50% 60%, #ffc107 0%, transparent 40%);
  border-radius: 10px;
}

.restaurant-interior::before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background: 
    linear-gradient(45deg, #3a3a3a 0%, transparent 50%),
    linear-gradient(135deg, #ff9800 0%, transparent 50%);
  border-radius: 10px;
}

.fried-rice {
  position: relative;
}

.fried-rice::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  background: 
    linear-gradient(135deg, #f5f5dc 0%, #ffffff 100%),
    radial-gradient(circle at 30% 30%, #dc3545 0%, transparent 30%),
    radial-gradient(circle at 70% 50%, #228b22 0%, transparent 30%);
  border-radius: 10px;
}

.zoom-icon {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 40px;
  color: #8B5CF6;
  background-color: rgba(0,0,0,0.5);
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.fish-dish::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  background: 
    linear-gradient(135deg, #ff9800 0%, #ff6b6b 100%),
    radial-gradient(circle at 50% 50%, #228b22 0%, transparent 40%);
  border-radius: 10px;
}

.chicken-drumsticks::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  background: 
    linear-gradient(135deg, #8b7355 0%, #654321 100%),
    radial-gradient(circle at 50% 30%, #228b22 0%, transparent 50%);
  border-radius: 10px;
}

.lobster::before {
  content: '';
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  background: 
    linear-gradient(135deg, #dc3545 0%, #8b0000 100%),
    radial-gradient(circle at 50% 50%, #228b22 0%, transparent 40%);
  border-radius: 10px;
}

/* Banner Section */
.banner {
  padding: 100px 20px;
  background: linear-gradient(135deg, rgba(0,0,0,0.8) 0%, rgba(0,0,0,0.6) 100%),
              url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1200 400"><rect fill="%232a2a2a" width="1200" height="400"/><circle fill="%23228b22" cx="200" cy="100" r="50" opacity="0.3"/><circle fill="%23dc3545" cx="400" cy="200" r="60" opacity="0.3"/><circle fill="%23ff9800" cx="800" cy="150" r="40" opacity="0.3"/></svg>');
  background-size: cover;
  background-position: center;
  text-align: center;
  position: relative;
  width: 100%;
}

.banner-content {
  opacity: 0;
  transform: translateY(40px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1), transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
  will-change: opacity, transform;
}

.banner-content.animate-in {
  opacity: 1;
  transform: translateY(0);
  will-change: auto;
}

.banner-content.animate-in .banner-title {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.2s both;
}

.banner-content.animate-in .banner-subtitle {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.4s both;
}

.banner-content.animate-in .banner-buttons {
  animation: fadeInUp 0.8s cubic-bezier(0.4, 0, 0.2, 1) 0.6s both;
}

.banner-content {
  width: 100%;
  max-width: 100%;
  margin: 0 auto;
  position: relative;
}

.banner-title {
  font-size: 48px;
  font-weight: 700;
  color: white;
  margin-bottom: 20px;
  letter-spacing: -0.5px;
  line-height: 1.3;
  font-family: 'Playfair Display', 'Georgia', serif;
}

.banner-subtitle {
  font-size: 20px;
  color: white;
  margin-bottom: 40px;
  font-weight: 400;
  letter-spacing: 0.3px;
}

.banner-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 20px;
}

.view-all-btn {
  position: absolute;
  top: 0;
  right: 0;
  background: linear-gradient(135deg, #8B5CF6 0%, #6366F1 100%);
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 10px;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.view-all-btn:hover {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(139, 92, 246, 0.3);
}

/* Footer */
.footer {
  background: 
    linear-gradient(180deg, #0a0a0a 0%, #1a1a1a 100%);
  padding: 80px 20px 0;
  width: 100%;
  position: relative;
  overflow: hidden;
}

.footer-content {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 40px;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding-bottom: 40px;
}

.footer-column {
  display: flex;
  flex-direction: column;
}

.footer-title {
  font-size: 22px;
  font-weight: 600;
  margin-bottom: 12px;
  color: #FF9F1C;
  letter-spacing: 0.5px;
  font-family: 'Playfair Display', 'Cormorant Garamond', 'Georgia', serif;
  font-style: italic;
}

.footer-title-line {
  width: 50px;
  height: 2px;
  background: linear-gradient(90deg, #8B5CF6, #FF9F1C);
  margin-bottom: 20px;
}

.footer-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-links li {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
  color: rgba(255, 255, 255, 0.8);
  transition: color 0.3s ease;
}

.footer-links li:hover {
  color: #FF9F1C;
}

.footer-links li a {
  color: inherit;
  text-decoration: none;
  font-size: 14px;
}

.link-arrow {
  color: rgba(255, 255, 255, 0.5);
  flex-shrink: 0;
}

.contact-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  margin-bottom: 16px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  line-height: 1.6;
}

.contact-icon {
  color: rgba(255, 255, 255, 0.5);
  flex-shrink: 0;
  margin-top: 2px;
}

.social-icons {
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

.social-icon {
  width: 36px;
  height: 36px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.3s ease;
  background: transparent;
  text-decoration: none;
}

.social-icon:hover {
  background: #FF9F1C;
  border-color: #FF9F1C;
  color: #ffffff;
  transform: translateY(-2px);
}

.social-icon svg {
  width: 18px;
  height: 18px;
}

.opening-hours-footer {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.hours-item {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.hours-text {
  color: rgba(255, 255, 255, 0.8);
  font-size: 14px;
  font-weight: 500;
}

.hours-time {
  color: rgba(255, 255, 255, 0.6);
  font-size: 13px;
}

.newsletter-description {
  color: rgba(255, 255, 255, 0.7);
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 20px;
}

.newsletter-form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.newsletter-input {
  background-color: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  padding: 12px 16px;
  border-radius: 4px;
  color: white;
  font-size: 14px;
  width: 100%;
  transition: all 0.3s ease;
}

.newsletter-input:focus {
  outline: none;
  border-color: #FF9F1C;
  background-color: rgba(255, 255, 255, 0.15);
}

.newsletter-input::placeholder {
  color: rgba(255, 255, 255, 0.4);
}

.newsletter-submit {
  background: linear-gradient(135deg, #FF9F1C 0%, #FF8C00 100%);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 600;
  font-size: 14px;
  transition: all 0.3s ease;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.newsletter-submit:hover {
  background: linear-gradient(135deg, #FFB84D 0%, #FF9F1C 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 159, 28, 0.3);
}

/* Footer Bottom Bar */
.footer-bottom-bar {
  background: rgba(0, 0, 0, 0.3);
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding: 20px 0;
  width: 100%;
}

.footer-bottom-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  gap: 20px;
  flex-wrap: wrap;
}

.copyright {
  color: rgba(255, 255, 255, 0.6);
  font-size: 13px;
  flex: 1;
  min-width: 200px;
}

.footer-bottom-links {
  display: flex;
  gap: 20px;
  align-items: center;
  flex-wrap: wrap;
}

.footer-bottom-links a {
  color: rgba(255, 255, 255, 0.6);
  text-decoration: none;
  font-size: 13px;
  transition: color 0.3s ease;
}

.footer-bottom-links a:hover {
  color: #FF9F1C;
}

.scroll-to-top {
  width: 40px;
  height: 40px;
  background: #FF9F1C;
  border: none;
  border-radius: 4px;
  color: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  flex-shrink: 0;
}

.scroll-to-top:hover {
  background: #FFB84D;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(255, 159, 28, 0.3);
}

.scroll-to-top svg {
  width: 16px;
  height: 16px;
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

/* Responsive Design */
/* Large Tablets and Small Desktops */
@media (max-width: 1400px) {
  .header-content {
    padding: 0 20px;
  }
}

/* Tablets */
@media (max-width: 1200px) {
  .header {
    padding: 15px 0;
  }

  .header-content {
    padding: 0 20px;
  }

  .hero-content,
  .about-content,
  .enjoy-content,
  .delivery-content {
    flex-direction: column;
    gap: 20px;
  }

  .hero-left,
  .about-right,
  .enjoy-left,
  .delivery-left {
    max-width: 100%;
  }

  .hero-right {
    width: 100%;
    display: flex;
    justify-content: center;
  }

  .pizza-container {
    width: 400px;
    height: 400px;
  }

  .pizza {
    width: 300px;
    height: 300px;
  }

  .gallery-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .footer-content {
    grid-template-columns: repeat(2, 1fr);
    gap: 40px;
  }

  .footer-bottom-content {
    flex-direction: column;
    text-align: center;
    gap: 15px;
  }

  .footer-bottom-links {
    justify-content: center;
  }

  .opening-images {
    flex-direction: column;
  }

  .opening-img-left,
  .opening-img-right {
    height: 200px;
  }

  .orange-band {
    height: 400px;
  }

  .large-bowl {
    width: 250px;
    height: 250px;
  }

  .small-bowl {
    width: 100px;
    height: 100px;
  }
}

/* Small Tablets and Large Phones */
@media (max-width: 968px) {
  .header {
    padding: 15px 0;
  }

  .header-content {
    padding: 0 15px;
  }

  .header-content {
    flex-wrap: wrap;
  }

  .header-right-section {
    order: 3;
    width: 100%;
    flex-direction: column;
    gap: 15px;
    margin-top: 15px;
  }

  .header-auth {
    width: 100%;
    justify-content: center;
    padding: 15px 0;
    border-top: 1px solid #444;
    border-bottom: 1px solid #444;
  }

  .nav-links {
    order: 4;
    width: 100%;
    flex-direction: column;
    gap: 0;
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.3s ease;
    background-color: #2a2a2a;
    margin-top: 0;
    padding: 0 20px;
  }

  .nav-links.mobile-open {
    max-height: 400px;
    padding: 20px;
  }

  .nav-links .separator {
    display: none;
  }

  .nav-link {
    padding: 15px 0;
    border-bottom: 1px solid #444;
    width: 100%;
    text-align: center;
  }

  .header-right-section {
    order: 2;
    flex-direction: row;
    width: auto;
    margin-top: 0;
  }

  .header-auth {
    margin-right: 15px;
  }

  .header-auth .auth-link {
    font-size: 13px;
    padding: 5px 8px;
  }

  .header-buttons {
    flex-wrap: wrap;
    gap: 10px;
  }

  .header-buttons button {
    font-size: 12px;
    padding: 10px 16px;
    flex: 1;
    min-width: 140px;
  }

  .mobile-menu-toggle {
    display: flex;
    order: 2;
    margin-left: auto;
  }

  .hero {
    padding: 40px 15px;
    min-height: auto;
  }

  .hero-content {
    padding: 0;
  }

  .hero-slide {
    flex-direction: column;
    gap: 15px;
    padding: 0;
    text-align: center;
  }

  .hero-left {
    width: 100%;
    max-width: 100%;
    margin: 0;
    padding: 0;
    order: 1;
  }

  .hero-right {
    width: 100%;
    order: 2;
    padding: 0;
  }

  .hero-image {
    max-width: 100%;
    max-height: 280px;
  }

  .hero-title {
    font-size: 36px;
    line-height: 1.2;
    margin-bottom: 15px;
  }

  .hero-subtitle {
    font-size: 15px;
    margin-bottom: 15px;
    line-height: 1.5;
  }

  .section-title {
    font-size: 36px;
  }

  .about,
  .enjoy,
  .delivery,
  .menu,
  .opening-hours,
  .gallery {
    padding: 60px 15px;
  }

  .menu-title {
    font-size: 36px;
  }

  .menu-categories {
    gap: 30px;
  }

  .menu-image {
    width: 150px;
    height: 150px;
  }

  .footer {
    padding: 60px 15px 0;
  }

  .footer-content {
    grid-template-columns: 1fr;
    gap: 40px;
    text-align: left;
  }

  .footer-bottom-content {
    flex-direction: column;
    text-align: center;
    gap: 15px;
  }

  .footer-bottom-links {
    justify-content: center;
    flex-wrap: wrap;
    gap: 15px;
  }

  .banner {
    padding: 60px 15px;
  }

  .banner-title {
    font-size: 36px;
  }

  .banner-subtitle {
    font-size: 18px;
  }

  .banner-buttons {
    flex-direction: column;
    gap: 15px;
  }

  .banner-buttons button {
    width: 100%;
  }

  .view-all-btn {
    position: static;
    margin-top: 20px;
    width: 100%;
  }

  .gallery-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
  }

  .gallery-filters {
    flex-wrap: wrap;
    gap: 5px;
  }

  .filter-item {
    font-size: 14px;
    padding: 5px 10px;
  }
}

/* Mobile Phones */
@media (max-width: 768px) {
  .top-bar {
    font-size: 12px;
    padding: 8px 0;
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

  .header-auth .separator,
  .header-auth .auth-separator {
    display: inline;
    color: rgba(255, 255, 255, 0.3);
    font-size: 14px;
  }

  .header-auth .nav-link,
  .header-auth .auth-link {
    font-size: 13px;
    padding: 8px 12px;
    text-align: center;
    border-bottom: none;
    transition: color 0.2s ease;
    white-space: nowrap;
  }

  .header-auth .nav-link:hover,
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

  .hero {
    padding: 30px 15px;
    min-height: auto;
  }

  .hero-content {
    padding: 0;
  }

  .hero-slide {
    flex-direction: column;
    gap: 15px;
    padding: 0;
    text-align: center;
  }

  .hero-left {
    width: 100%;
    max-width: 100%;
    margin: 0;
    padding: 0;
    order: 1;
  }

  .hero-left .slider-dots-desktop {
    display: none;
  }

  .hero-right {
    width: 100%;
    order: 2;
    padding: 0;
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .hero-image {
    max-width: 100%;
    max-height: 240px;
    width: auto;
  }

  .hero-right .slider-dots-mobile {
    display: flex;
    margin-top: 20px;
    gap: 10px;
  }

  .slider-arrow {
    width: 36px;
    height: 36px;
    opacity: 0.8;
  }

  .slider-arrow-left {
    left: 8px;
  }

  .slider-arrow-right {
    right: 8px;
  }

  .slider-arrow svg {
    width: 18px;
    height: 18px;
  }

  .hero-title {
    font-size: 28px;
    line-height: 1.2;
    margin-bottom: 12px;
  }

  .hero-subtitle {
    font-size: 14px;
    margin-bottom: 20px;
    line-height: 1.5;
    padding: 0 10px;
  }

  .hero-buttons {
    flex-direction: column;
    width: 100%;
    gap: 12px;
    padding: 0 5px;
  }

  .hero-buttons button {
    width: 100%;
    padding: 14px 24px;
    font-size: 15px;
    min-height: 48px;
  }

  .slider-dots {
    margin-top: 25px;
    gap: 8px;
  }

  .pizza-container {
    width: 300px;
    height: 300px;
  }

  .pizza {
    width: 250px;
    height: 250px;
  }

  .slider-dots {
    margin-top: 20px;
  }

  .about,
  .enjoy,
  .delivery,
  .menu,
  .opening-hours,
  .gallery {
    padding: 50px 15px;
  }

  .section-title {
    font-size: 28px;
    margin-bottom: 15px;
  }

  .section-text {
    font-size: 14px;
    line-height: 1.6;
  }

  .orange-band {
    height: 300px;
    clip-path: polygon(0 0, 100% 0, 100% 100%, 0 100%);
  }

  .large-bowl {
    width: 200px;
    height: 200px;
    left: 50%;
  }

  .small-bowls {
    right: 5%;
    top: 10%;
    gap: 15px;
  }

  .small-bowl {
    width: 80px;
    height: 80px;
  }

  .bowl-1::before,
  .bowl-2::before,
  .bowl-3::before {
    width: 70px;
    height: 70px;
    top: 5px;
    left: 5px;
  }

  .menu-title {
    font-size: 28px;
  }

  .menu-categories {
    flex-direction: column;
    gap: 40px;
  }

  .menu-image {
    width: 180px;
    height: 180px;
  }

  .menu-category {
    font-size: 16px;
  }

  .menu-carousel-container {
    margin: 30px 0;
    padding: 10px 0;
  }

  .menu-item-card {
    flex: 0 0 250px;
  }

  .menu-item-image {
    height: 150px;
  }

  .menu-item-info {
    padding: 15px;
  }

  .menu-item-name {
    font-size: 18px;
  }

  .menu-item-description {
    font-size: 13px;
    min-height: 36px;
  }

  .menu-item-price {
    font-size: 20px;
  }

  .delivery-fee {
    margin-top: 20px;
  }

  .fee-title {
    font-size: 20px;
  }

  .fee-item {
    font-size: 14px;
  }

  .map-container {
    height: 300px;
  }

  .opening-center {
    padding: 40px 15px;
  }

  .hours-label {
    font-size: 18px;
  }

  .hours-days,
  .hours-time {
    font-size: 14px;
  }

  .theme-slider {
    flex-direction: column;
    gap: 10px;
  }

  .slider-track {
    width: 150px;
  }

  .logo-circle {
    width: 120px;
    height: 120px;
    padding: 15px;
  }

  .logo-text-divine {
    font-size: 20px;
  }

  .logo-text-restaurant {
    font-size: 8px;
  }

  .gallery-grid {
    grid-template-columns: 1fr;
    gap: 15px;
  }

  .gallery-img {
    height: 250px;
  }

  .gallery-overlay {
    padding: 15px;
  }

  .gallery-item-name {
    font-size: 16px;
  }

  .gallery-item-price {
    font-size: 18px;
  }

  .gallery-filters {
    font-size: 12px;
  }

  .banner {
    padding: 50px 15px;
  }

  .banner-title {
    font-size: 28px;
  }

  .banner-subtitle {
    font-size: 16px;
  }

  .footer {
    padding: 50px 15px 0;
  }

  .footer-content {
    gap: 30px;
    padding-bottom: 30px;
  }

  .footer-title {
    font-size: 20px;
    text-align: center;
  }

  .footer-title-line {
    width: 40px;
    margin-left: auto;
    margin-right: auto;
  }

  .footer-column {
    align-items: center;
    text-align: center;
  }

  .footer-links {
    align-items: center;
  }

  .footer-links li {
    justify-content: center;
  }

  .contact-item {
    font-size: 13px;
    margin-bottom: 12px;
    justify-content: center;
    text-align: center;
  }

  .contact-item span {
    text-align: center;
  }

  .social-icons {
    justify-content: center;
  }

  .opening-hours-footer {
    align-items: center;
  }

  .hours-item {
    align-items: center;
    text-align: center;
  }

  .newsletter-description {
    font-size: 13px;
    text-align: center;
  }

  .newsletter-form {
    align-items: center;
  }

  .newsletter-input,
  .newsletter-submit {
    padding: 10px 14px;
    font-size: 13px;
  }

  .footer-bottom-content {
    padding: 0 15px;
    flex-direction: column;
    gap: 12px;
  }

  .copyright {
    font-size: 12px;
    text-align: center;
  }

  .footer-bottom-links {
    gap: 12px;
    font-size: 12px;
  }

  .scroll-to-top {
    width: 36px;
    height: 36px;
  }

  .restaurant-images {
    grid-template-columns: 1fr;
  }

  .restaurant-img,
  .chef-img {
    height: 250px;
  }
}

/* Small Mobile Phones */
@media (max-width: 480px) {
  .slider-arrow {
    width: 35px;
    height: 35px;
  }

  .slider-arrow-left {
    left: 5px;
  }

  .slider-arrow-right {
    right: 5px;
  }

  .slider-arrow svg {
    width: 18px;
    height: 18px;
  }

  .about::before,
  .about::after,
  .menu::before,
  .menu::after,
  .gallery::before,
  .gallery::after {
    display: none;
  }

  .hero-title {
    font-size: 26px;
    line-height: 1.2;
  }

  .hero-subtitle {
    font-size: 13px;
  }

  .hero-buttons button {
    padding: 12px 20px;
    font-size: 14px;
  }

  .hero-image {
    max-height: 200px;
  }

  .section-title,
  .menu-title,
  .banner-title {
    font-size: 24px;
  }

  .pizza-container {
    width: 250px;
    height: 250px;
  }

  .pizza {
    width: 200px;
    height: 200px;
  }

  .orange-band {
    height: 250px;
  }

  .large-bowl {
    width: 150px;
    height: 150px;
  }

  .small-bowl {
    width: 60px;
    height: 60px;
  }

  .bowl-1::before,
  .bowl-2::before,
  .bowl-3::before {
    width: 50px;
    height: 50px;
  }

  .menu-image {
    width: 150px;
    height: 150px;
  }

  .menu-carousel-container {
    margin: 20px 0;
    padding: 5px 0;
  }

  .menu-item-card {
    flex: 0 0 220px;
  }

  .menu-item-image {
    height: 130px;
  }

  .menu-item-info {
    padding: 12px;
  }

  .menu-item-name {
    font-size: 16px;
  }

  .menu-item-description {
    font-size: 12px;
    min-height: 32px;
  }

  .menu-item-price {
    font-size: 18px;
  }

  .menu-item-category {
    font-size: 10px;
    padding: 3px 8px;
  }

  .gallery-img {
    height: 200px;
  }

  .gallery-overlay {
    transform: translateY(0);
    padding: 12px;
  }

  .gallery-item-name {
    font-size: 14px;
  }

  .gallery-item-price {
    font-size: 16px;
  }

  .header-buttons {
    width: 100%;
  }

  .header-buttons button {
    flex: 1;
    min-width: 0;
  }
}

/* Landscape Orientation for Mobile */
@media (max-width: 968px) and (orientation: landscape) {
  .hero {
    min-height: 400px;
    padding: 40px 30px;
  }

  .pizza-container {
    width: 300px;
    height: 300px;
  }

  .pizza {
    width: 250px;
    height: 250px;
  }
}

/* Accessibility: Reduced Motion */
@media (prefers-reduced-motion: reduce) {
  .about-left,
  .about-right,
  .enjoy-left,
  .enjoy-right,
  .delivery-left,
  .delivery-right,
  .about-image,
  .restaurant-img,
  .chef-img,
  .menu-header,
  .menu-carousel-container,
  .menu-loading,
  .gallery-header,
  .gallery-filters,
  .gallery-grid,
  .gallery-item,
  .banner-content {
    transition: none;
    animation: none;
  }

  .about-left,
  .about-right,
  .enjoy-left,
  .enjoy-right,
  .delivery-left,
  .delivery-right,
  .menu-header,
  .menu-carousel-container,
  .menu-loading,
  .gallery-header,
  .gallery-filters,
  .gallery-grid,
  .gallery-item,
  .banner-content {
    opacity: 1;
    transform: none;
  }
}

/* Print Styles */
@media print {
  .top-bar,
  .header,
  .header-buttons,
  .banner,
  .footer {
    display: none;
  }

  .app {
    background: white;
    color: black;
  }
}
</style>
