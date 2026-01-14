<template>
  <div class="login-page">
    <div class="login-container">
      <!-- Left Side - Branding -->
      <div class="login-left">
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

      <!-- Right Side - Login Form -->
      <div class="login-right">
        <div class="form-header">
          <nav class="login-nav">
            <a href="#" class="nav-link active" @click.prevent="goToLogin">SIGN IN</a>
            <a href="#" class="nav-link" @click.prevent="goToSignup">CREATE ACCOUNT</a>
          </nav>
        </div>

        <div class="login-form-container">
          <div class="welcome-section">
            <h1 class="login-title">Welcome Back</h1>
            <p class="login-subtitle">Sign in to continue to your account</p>
          </div>

          <form @submit.prevent="handleLogin" class="login-form">
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
              <label class="input-label">Password</label>
              <div class="input-wrapper">
                <svg class="input-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <rect x="3" y="10" width="18" height="11" rx="2" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                  <path d="M7 10V6a5 5 0 1 1 10 0v4" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
                <input :type="showPassword ? 'text' : 'password'" v-model="password" placeholder="Enter your password" class="form-input" required />
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
            </div>

            <div class="form-options">
              <label class="checkbox-wrapper">
                <input type="checkbox" v-model="rememberMe" />
                <span class="checkbox-label">Remember me</span>
              </label>
              <a href="#" class="forgot-password" @click.prevent="goToForgotPassword">Forgot password?</a>
            </div>

            <div v-if="errorMessage" class="error-message">
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 8v4m0 4h.01M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              {{ errorMessage }}
            </div>

            <div class="form-actions">
              <button type="submit" class="btn-login" :disabled="isLoading">
                <span v-if="isLoading" class="spinner"></span>
                {{ isLoading ? 'Signing in...' : 'Sign In' }}
              </button>
              <div class="signup-section">
                <p>Don't have an account? <a href="#" @click.prevent="goToSignup" class="signup-link">Sign up</a></p>
              </div>
            </div>

            <div class="separator">
              <span>Or continue with</span>
            </div>

            <div class="social-login">
              <div id="google-signin-button" v-if="googleLoaded && googleButtonRendered"></div>
              <button v-if="!googleButtonRendered" type="button" class="social-btn" @click="handleGoogleLogin" :disabled="isLoading">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4"/>
                  <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853"/>
                  <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05"/>
                  <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335"/>
                </svg>
                <span>{{ isLoading ? 'Signing in...' : 'Continue with Google' }}</span>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      email: '',
      password: '',
      showPassword: false,
      rememberMe: false,
      errorMessage: '',
      isLoading: false,
      googleLoaded: false,
      googleButtonRendered: false,
      googleInitialized: false
    }
  },
  mounted() {
    // Load saved email if "Remember me" was checked
    const rememberedEmail = localStorage.getItem('rememberedEmail')
    if (rememberedEmail) {
      this.email = rememberedEmail
      this.rememberMe = true
    }
    
    // Load Google Identity Services
    this.loadGoogleScript()
  },
  methods: {
    loadGoogleScript() {
      // Prevent multiple initializations
      if (this.googleInitialized) {
        return
      }
      
      // Check if script is already loaded
      if (window.google && window.google.accounts) {
        this.googleLoaded = true
        this.initializeGoogleSignIn()
        return
      }
      
      // Check if script is already in the document
      const existingScript = document.querySelector('script[src="https://accounts.google.com/gsi/client"]')
      if (existingScript) {
        // Script is loading, wait for it
        existingScript.addEventListener('load', () => {
          this.googleLoaded = true
          this.initializeGoogleSignIn()
        })
        return
      }
      
      // Load Google Identity Services script
      const script = document.createElement('script')
      script.src = 'https://accounts.google.com/gsi/client'
      script.async = true
      script.defer = true
      script.onload = () => {
        this.googleLoaded = true
        this.initializeGoogleSignIn()
      }
      document.head.appendChild(script)
    },
    initializeGoogleSignIn() {
      // Prevent multiple initializations
      if (this.googleInitialized) {
        console.log('Google Sign-In already initialized, skipping...')
        return
      }
      
      if (!window.google || !window.google.accounts) {
        console.error('Google Identity Services not loaded')
        return
      }
      
      // Initialize Google Sign In
      // To get your Google Client ID:
      // 1. Go to https://console.cloud.google.com/
      // 2. Create a new project or select existing one
      // 3. Enable Google+ API
      // 4. Go to "Credentials" -> "Create Credentials" -> "OAuth client ID"
      // 5. Choose "Web application"
      // 6. Add authorized JavaScript origins: http://localhost:5173 (for dev)
      // 7. Copy the Client ID and paste it below
      const clientId = '923429340318-6ht34qf2s0sv7rtfbkpf326jfs1e2hvq.apps.googleusercontent.com' // TODO: Replace with actual Google Client ID from Google Cloud Console
      
      if (!clientId || clientId === 'YOUR_GOOGLE_CLIENT_ID') {
        console.warn('Google Client ID not configured. Google Sign-In will not work until configured.')
        // Show fallback button
        this.googleButtonRendered = false
        return
      }
      
      // Bind callback to ensure 'this' context is preserved
      const callback = (response) => {
        console.log('Google callback received:', response)
        this.handleGoogleCallback(response)
      }
      
      try {
        window.google.accounts.id.initialize({
          client_id: clientId,
          callback: callback
        })
        this.googleInitialized = true
        console.log('Google Sign-In initialized with client ID:', clientId)
      } catch (error) {
        console.error('Error initializing Google Sign-In:', error)
        this.googleButtonRendered = false
        return
      }
      
      // Render Google button
      this.$nextTick(() => {
        const buttonDiv = document.getElementById('google-signin-button')
        if (buttonDiv) {
          try {
            console.log('Rendering Google button...')
            window.google.accounts.id.renderButton(buttonDiv, {
              theme: 'outline',
              size: 'large',
              type: 'standard',
              text: 'signin_with',
              shape: 'rectangular',
              logo_alignment: 'left',
              width: '100%' // This tells Google to use full width
            })
            console.log('Google button render called')
            
            // Force check and apply width after a short delay
            setTimeout(() => {
              const googleButton = buttonDiv.querySelector('.gsi-material-button')
              if (googleButton) {
                console.log('Google button element found')
                googleButton.style.width = '100%'
                googleButton.style.minWidth = '100%'
                googleButton.style.maxWidth = '100%'
                
                // Add click listener to debug
                googleButton.addEventListener('click', (e) => {
                  console.log('Google button clicked directly', e)
                }, true)
              } else {
                console.warn('Google button element (.gsi-material-button) not found')
                // Log all children to see what was rendered
                console.log('Button div children:', buttonDiv.children)
                console.log('Button div innerHTML:', buttonDiv.innerHTML.substring(0, 200))
              }
              
              // Check if button was actually rendered
              if (buttonDiv.children.length > 0) {
                this.googleButtonRendered = true
                console.log('Google button rendered successfully, children:', buttonDiv.children.length)
              } else {
                this.googleButtonRendered = false
                console.warn('Google button not rendered - no children found')
              }
            }, 500)
          } catch (error) {
            console.error('Failed to render Google button:', error)
            this.googleButtonRendered = false
          }
        } else {
          this.googleButtonRendered = false
        }
      })
    },
    handleGoogleButtonClick() {
      // This is called when the Google button container is clicked
      // The actual click should be handled by Google's button, but this is a fallback
      console.log('Google button container clicked')
    },
    handleGoogleLogin() {
      console.log('handleGoogleLogin called - this is fallback button')
      this.errorMessage = ''
      this.isLoading = true
      
      // This should only be called when the fallback button is shown
      // If Google button is rendered, clicks should be handled automatically by Google
      if (!this.googleLoaded || !window.google || !window.google.accounts) {
        this.errorMessage = 'Google Sign-In is not available. Please configure Google Client ID.'
        this.loadGoogleScript()
        this.isLoading = false
        return
      }
      
      // Use the OAuth 2.0 flow directly as a fallback
      const clientId = '923429340318-6ht34qf2s0sv7rtfbkpf326jfs1e2hvq.apps.googleusercontent.com'
      
      try {
        // Create a token client to handle the sign-in
        const tokenClient = window.google.accounts.oauth2.initTokenClient({
          client_id: clientId,
          scope: 'email profile',
          callback: (response) => {
            console.log('OAuth token response:', response)
            if (response.error) {
              console.error('OAuth error:', response.error)
              this.errorMessage = 'Google Sign-In failed: ' + response.error
              this.isLoading = false
              return
            }
            
            // Get user info using the access token
            fetch(`https://www.googleapis.com/oauth2/v2/userinfo?access_token=${response.access_token}`)
              .then(res => res.json())
              .then(userInfo => {
                console.log('User info from Google:', userInfo)
                this.handleGoogleUserInfo(userInfo)
              })
              .catch(error => {
                console.error('Error fetching user info:', error)
                this.errorMessage = 'Failed to get user information from Google.'
                this.isLoading = false
              })
          }
        })
        
        // Request the access token (this will open the popup)
        console.log('Requesting Google OAuth token...')
        tokenClient.requestAccessToken({ prompt: 'consent' })
        
        // Reset loading after a delay if nothing happens
        setTimeout(() => {
          if (this.isLoading) {
            this.isLoading = false
            this.errorMessage = 'Google Sign-In popup may be blocked. Please check your browser settings and ensure popups are allowed for this site.'
          }
        }, 10000)
      } catch (error) {
        console.error('Error initializing OAuth client:', error)
        this.errorMessage = 'Failed to initialize Google Sign-In. Please check your browser console for CORS errors and ensure authorized origins are set in Google Cloud Console.'
        this.isLoading = false
      }
    },
    handleGoogleUserInfo(userInfo) {
      // Extract user information and send to backend
      const email = userInfo.email
      const fullName = userInfo.name || `${userInfo.given_name || ''} ${userInfo.family_name || ''}`.trim()
      const username = userInfo.email?.split('@')[0] || userInfo.given_name || 'user'
      const googleId = userInfo.id
      
      console.log('Processing Google user:', { email, fullName, username, googleId })
      
      // Send to backend
      this.sendGoogleAuthToBackend(email, username, fullName, googleId)
    },
    async sendGoogleAuthToBackend(email, username, fullName, googleId) {
      try {
        const userInfo = {
          email: email,
          username: username,
          fullName: fullName,
          googleId: googleId
        }
        
        // Send user info to backend for verification and user creation/login
        const backendResponse = await fetch('http://localhost:8080/api/auth/google', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(userInfo)
        })

        if (!backendResponse.ok) {
          let errorMessage = 'Google login failed'
          try {
            const errorData = await backendResponse.json()
            errorMessage = errorData.error || errorMessage
          } catch (e) {
            errorMessage = backendResponse.statusText || errorMessage
          }
          throw new Error(errorMessage)
        }

        const data = await backendResponse.json()

        if (data.token) {
          localStorage.setItem('token', data.token)
          localStorage.setItem('user', JSON.stringify(data.user))
          
          // Save email for "Remember me" if user wants
          if (this.rememberMe && data.user?.email) {
            localStorage.setItem('rememberedEmail', data.user.email)
          }
        }

        const userRole = data.user?.role || 'USER'
        if (userRole === 'ADMIN') {
          this.$emit('show-admin-dashboard')
        } else {
          this.$emit('show-home')
        }
      } catch (error) {
        this.errorMessage = error.message || 'Google login failed. Please try again.'
        console.error('Google login error:', error)
        this.isLoading = false
      }
    },
    async handleGoogleCallback(response) {
      console.log('Google callback triggered', response)
      this.isLoading = true
      this.errorMessage = ''
      
      try {
        if (!response) {
          console.error('No response from Google')
          throw new Error('No response received from Google')
        }
        
        if (!response.credential) {
          console.error('No credential in response:', response)
          throw new Error('No credential received from Google')
        }
        
        // Decode the credential token (JWT) - Google JWT is base64 encoded
        const credential = response.credential
        console.log('Credential received, length:', credential.length)
        
        // Decode JWT payload (base64url decode)
        const base64Url = credential.split('.')[1]
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
        }).join(''))
        
        const googleUser = JSON.parse(jsonPayload)
        
        // Extract user info from Google token
        const userInfo = {
          email: googleUser.email,
          username: googleUser.email.split('@')[0] + '_google', // Generate username from email
          fullName: googleUser.name || googleUser.email.split('@')[0],
          picture: googleUser.picture,
          googleId: googleUser.sub
        }
        
        // Send user info to backend for verification and user creation/login
        const backendResponse = await fetch('http://localhost:8080/api/auth/google', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(userInfo)
        })

        if (!backendResponse.ok) {
          let errorMessage = 'Google login failed'
          try {
            const errorData = await backendResponse.json()
            errorMessage = errorData.error || errorMessage
          } catch (e) {
            errorMessage = backendResponse.statusText || errorMessage
          }
          throw new Error(errorMessage)
        }

        const data = await backendResponse.json()

        if (data.token) {
          localStorage.setItem('token', data.token)
          localStorage.setItem('user', JSON.stringify(data.user))
          
          // Save email for "Remember me" if user wants
          if (this.rememberMe && data.user?.email) {
            localStorage.setItem('rememberedEmail', data.user.email)
          }
        }

        const userRole = data.user?.role || 'USER'
        if (userRole === 'ADMIN') {
          this.$emit('show-admin-dashboard')
        } else {
          this.$emit('show-home')
        }
      } catch (error) {
        this.errorMessage = error.message || 'Google login failed. Please try again.'
        console.error('Google login error:', error)
      } finally {
        this.isLoading = false
      }
    },
    togglePassword() {
      this.showPassword = !this.showPassword
    },
    async handleLogin() {
      this.errorMessage = ''
      this.isLoading = true
      
      try {
        const response = await fetch('http://localhost:8080/api/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password
          })
        })

        if (!response.ok) {
          let errorMessage = 'Login failed'
          try {
            const errorData = await response.json()
            errorMessage = errorData.error || errorMessage
          } catch (e) {
            errorMessage = response.status === 403 ? 'Access forbidden. Please check your credentials.' : response.statusText || errorMessage
          }
          throw new Error(errorMessage)
        }

        const data = await response.json()

        if (data.token) {
          localStorage.setItem('token', data.token)
          localStorage.setItem('user', JSON.stringify(data.user))
          
          // Handle "Remember me" functionality
          if (this.rememberMe) {
            localStorage.setItem('rememberedEmail', this.email)
          } else {
            localStorage.removeItem('rememberedEmail')
          }
        }

        const userRole = data.user?.role || 'USER'
        if (userRole === 'ADMIN') {
          this.$emit('show-admin-dashboard')
        } else {
          this.$emit('show-home')
        }
      } catch (error) {
        this.errorMessage = error.message || 'Login failed. Please check your credentials.'
        console.error('Login error:', error)
      } finally {
        this.isLoading = false
      }
    },
    goToSignup() {
      this.$emit('switch-to-signup')
    },
    goToLogin() {
      // Already on login page
    },
    goToForgotPassword() {
      this.$emit('show-forgot-password')
    }
  }
}
</script>

<style scoped>
.login-page {
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

.login-page::before {
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

.login-container {
  display: flex;
  width: 100%;
  max-width: 1000px;
  max-height: 95vh;
  min-height: 600px;
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
.login-left {
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
  max-width: 200px;
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

/* Right Side */
.login-right {
  flex: 1.2;
  padding: 28px 32px;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  overflow-x: hidden;
  width: 100%;
  max-height: 95vh;
}

.login-form-container {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.form-header {
  margin-bottom: 16px;
  width: 100%;
}

.login-nav {
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
  margin-bottom: 16px;
}

.login-title {
  font-size: 26px;
  font-weight: 600;
  color: #ffffff;
  margin-bottom: 4px;
  letter-spacing: -0.5px;
}

.login-subtitle {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.6);
  font-weight: 400;
}

.login-form {
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

.input-label {
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

.password-toggle {
  background: transparent;
  border: none;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.4);
  padding: 4px;
  display: flex;
  align-items: center;
  transition: color 0.3s ease;
}

.password-toggle:hover {
  color: #ffffff;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: -4px;
}

.checkbox-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.checkbox-wrapper input[type="checkbox"] {
  width: 16px;
  height: 16px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 4px;
  background: rgba(255, 255, 255, 0.05);
  cursor: pointer;
}

.checkbox-label {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
}

.forgot-password {
  color: rgba(255, 159, 28, 0.8);
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  transition: color 0.3s ease;
}

.forgot-password:hover {
  color: #FF9F1C;
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

.form-actions {
  margin-top: 4px;
  width: 100%;
  max-width: 100%;
}

.btn-login {
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
  gap: 8px;
}

.btn-login:hover {
  background: linear-gradient(135deg, #7C3AED 0%, #4F46E5 100%);
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(139, 92, 246, 0.3);
}

.btn-login:disabled {
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

.signup-section {
  text-align: center;
  margin-top: 8px;
  color: rgba(255, 255, 255, 0.6);
  font-size: 12px;
}

.signup-link {
  color: #8B5CF6;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.signup-link:hover {
  color: #A78BFA;
}

.separator {
  display: flex;
  align-items: center;
  text-align: center;
  margin: 6px 0;
  color: rgba(255, 255, 255, 0.3);
  font-size: 11px;
  width: 100%;
  max-width: 100%;
}

.separator::before,
.separator::after {
  content: '';
  flex: 1;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.separator span {
  padding: 0 16px;
}

.social-login {
  width: 100%;
  display: flex;
  flex-direction: column;
}

/* This ensures the Google button container is full width */
#google-signin-button {
  width: 100% !important;
  display: flex;
  justify-content: center;
}

/* Force the inner Google div to be full width */
#google-signin-button > div {
  width: 100% !important;
  min-width: 100% !important;
  max-width: 100% !important;
}

/* Ensure the div inside the Google container takes full width */
#google-signin-button > div > div {
  width: 100% !important;
}

/* Custom fallback button styling */
.social-btn {
  width: 100%;
  min-width: 100%;
  max-width: 100%;
  padding: 12px;
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  box-sizing: border-box;
  position: relative;
}

.social-btn:hover {
  background: rgba(255, 255, 255, 0.08);
  border-color: rgba(255, 255, 255, 0.2);
}

.social-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* Override Google's button styles if needed */
.gsi-material-button {
  width: 100% !important;
  min-width: 100% !important;
}

/* Add this to ensure proper button sizing */
.gsi-material-button > div {
  width: 100% !important;
}

/* Responsive */
@media (max-width: 968px) {
  .login-container {
    flex-direction: column;
    max-width: 500px;
    max-height: 98vh;
    min-height: 650px;
  }

  .login-left {
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

  .login-right {
    padding: 24px 20px;
    overflow: hidden;
  }

  .form-header {
    margin-bottom: 12px;
  }

  .welcome-section {
    margin-bottom: 12px;
  }

  .login-title {
    font-size: 24px;
  }

  .login-subtitle {
    font-size: 13px;
  }

  .login-nav {
    gap: 30px;
    justify-content: center;
  }
}

@media (max-width: 640px) {
  .login-page {
    padding: 12px;
    align-items: flex-start;
    padding-top: 20px;
  }

  .login-container {
    max-width: 100%;
    border-radius: 12px;
    max-height: calc(100vh - 40px);
    min-height: calc(100vh - 40px);
  }

  .login-left {
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

  .login-right {
    padding: 24px 20px;
  }

  .form-header {
    margin-bottom: 10px;
  }

  .login-nav {
    gap: 20px;
    font-size: 12px;
  }

  .welcome-section {
    margin-bottom: 10px;
  }

  .login-title {
    font-size: 22px;
    margin-bottom: 4px;
  }

  .login-subtitle {
    font-size: 12px;
  }

  .login-form {
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

  .btn-login {
    padding: 12px;
    font-size: 14px;
  }

  .signup-section {
    margin-top: 8px;
    font-size: 11px;
  }

  .separator {
    margin: 8px 0;
    font-size: 10px;
  }

  .social-btn {
    padding: 10px;
    font-size: 12px;
  }

  .social-btn svg {
    width: 18px;
    height: 18px;
  }
}

@media (max-width: 480px) {
  .login-page {
    padding: 8px;
    padding-top: 16px;
  }

  .login-container {
    max-height: calc(100vh - 32px);
    min-height: calc(100vh - 32px);
  }

  .login-left {
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

  .login-right {
    padding: 20px 16px;
    overflow: hidden;
  }

  .form-options {
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    gap: 8px;
    margin-top: -4px;
  }

  .forgot-password {
    font-size: 11px;
    white-space: nowrap;
  }

  .checkbox-label {
    font-size: 12px;
  }
}
</style>