# Testing Guide - Admin Dashboard Backend

## 🚀 How to Start the Backend

### Option 1: Using Maven Wrapper (Recommended)
```bash
cd restaurant-api
.\mvnw.cmd spring-boot:run
```

### Option 2: Using IDE
- Open the project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
- Run `RestaurantApiApplication.java` as a Spring Boot Application

### Option 3: Build and Run JAR
```bash
cd restaurant-api
.\mvnw.cmd clean package
java -jar target\restaurant-api-0.0.1-SNAPSHOT.jar
```

**The server will start on:** `http://localhost:8080`

---

## ✅ Prerequisites

1. **MongoDB must be running** on `localhost:27017`
2. **Database:** `food-booking-db` (auto-created)
3. **Create an ADMIN user** first (see below)

---

## 🔐 Step 1: Create Admin User

### Sign up with admin email pattern:
```bash
POST http://localhost:8080/api/auth/signup
Content-Type: application/json

{
  "username": "admin",
  "email": "admin@admin.com",
  "password": "admin123",
  "phoneNumber": "1234567890"
}
```

**Note:** Users with email ending in `@admin.com` automatically get ADMIN role.

### Login to get JWT token:
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "admin@admin.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": "...",
    "username": "admin",
    "email": "admin@admin.com",
    "role": "ADMIN"
  }
}
```

**Save the token** - you'll need it for all admin endpoints!

---

## 📋 Step 2: Test Admin Endpoints

### Replace `YOUR_JWT_TOKEN` with the actual token from login

---

### 🍽️ **Menu Management**

#### 1. Create Menu Item (with image)
```bash
POST http://localhost:8080/api/admin/menus
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: multipart/form-data

Form Data:
- name: "Nasi Goreng"
- description: "Traditional Indonesian fried rice"
- price: "15.99"
- category: "Main Course"
- available: "true"
- image: [select image file]
```

#### 2. Get All Menu Items
```bash
GET http://localhost:8080/api/admin/menus
Authorization: Bearer YOUR_JWT_TOKEN
```

#### 3. Get Menu by ID
```bash
GET http://localhost:8080/api/admin/menus/{menuId}
Authorization: Bearer YOUR_JWT_TOKEN
```

#### 4. Update Menu Item
```bash
PUT http://localhost:8080/api/admin/menus/{menuId}
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: multipart/form-data

Form Data:
- name: "Nasi Goreng Special"
- description: "Updated description"
- price: "18.99"
- category: "Main Course"
- available: "true"
- image: [optional new image file]
```

#### 5. Delete Menu Item
```bash
DELETE http://localhost:8080/api/admin/menus/{menuId}
Authorization: Bearer YOUR_JWT_TOKEN
```

---

### 👥 **User Management**

#### 1. Get All Users
```bash
GET http://localhost:8080/api/admin/users
Authorization: Bearer YOUR_JWT_TOKEN
```

#### 2. Get User by ID
```bash
GET http://localhost:8080/api/admin/users/{userId}
Authorization: Bearer YOUR_JWT_TOKEN
```

#### 3. Update User Role
```bash
PUT http://localhost:8080/api/admin/users/{userId}/role
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "role": "ADMIN"
}
```

#### 4. Delete User
```bash
DELETE http://localhost:8080/api/admin/users/{userId}
Authorization: Bearer YOUR_JWT_TOKEN
```

---

### 📅 **Reservation Management**

#### 1. Get All Reservations
```bash
GET http://localhost:8080/api/admin/reservations
Authorization: Bearer YOUR_JWT_TOKEN
```

#### 2. Get Reservation by ID
```bash
GET http://localhost:8080/api/admin/reservations/{reservationId}
Authorization: Bearer YOUR_JWT_TOKEN
```

#### 3. Update Reservation Status
```bash
PUT http://localhost:8080/api/admin/reservations/{reservationId}/status
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "status": "CONFIRMED"
}
```

**Valid statuses:** `PENDING`, `CONFIRMED`, `CANCELLED`

#### 4. Delete Reservation
```bash
DELETE http://localhost:8080/api/admin/reservations/{reservationId}
Authorization: Bearer YOUR_JWT_TOKEN
```

---

### 📊 **Dashboard Statistics**

#### Get Dashboard Stats
```bash
GET http://localhost:8080/api/admin/dashboard/stats
Authorization: Bearer YOUR_JWT_TOKEN
```

**Response:**
```json
{
  "totalUsers": 10,
  "totalReservations": 25,
  "totalMenuItems": 15,
  "totalRevenue": 0.0
}
```

---

### 🖼️ **Image Retrieval (Public)**

#### Get Image by ID
```bash
GET http://localhost:8080/api/images/{imageId}
```

**No authentication required!** This is a public endpoint.

---

## 🧪 Testing with cURL (Command Line)

### Windows PowerShell Examples:

#### 1. Login
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -ContentType "application/json" -Body '{"email":"admin@admin.com","password":"admin123"}'
$token = $response.token
Write-Host "Token: $token"
```

#### 2. Get Dashboard Stats
```powershell
$headers = @{ "Authorization" = "Bearer $token" }
Invoke-RestMethod -Uri "http://localhost:8080/api/admin/dashboard/stats" -Method GET -Headers $headers
```

#### 3. Get All Users
```powershell
$headers = @{ "Authorization" = "Bearer $token" }
Invoke-RestMethod -Uri "http://localhost:8080/api/admin/users" -Method GET -Headers $headers
```

---

## 🧪 Testing with Postman

1. **Import Collection:**
   - Create a new collection
   - Add all endpoints listed above
   - Set Authorization type to "Bearer Token"
   - Use the token from login response

2. **Environment Variables:**
   - Create environment with:
     - `baseUrl`: `http://localhost:8080`
     - `token`: (set after login)

3. **Test Flow:**
   - Login → Save token to environment
   - Test dashboard stats
   - Test menu management
   - Test user management
   - Test reservation management

---

## 🧪 Testing with Browser

### Public Endpoints (No Auth):
- `http://localhost:8080/api/images/{imageId}` - View image

### Protected Endpoints:
Use browser extensions like:
- **ModHeader** (Chrome) - Add Authorization header
- **REST Client** (VS Code extension)

---

## ✅ Expected Responses

### Success Responses:
- **200 OK** - GET, PUT requests
- **201 Created** - POST requests
- **204 No Content** - DELETE requests

### Error Responses:
- **400 Bad Request** - Invalid input
- **401 Unauthorized** - Missing/invalid token
- **403 Forbidden** - Not ADMIN role
- **404 Not Found** - Resource doesn't exist
- **500 Internal Server Error** - Server error

---

## 🔍 Verify Backend is Running

### Check Health:
```bash
# Should return 401 (expected - endpoint requires auth)
curl http://localhost:8080/api/admin/dashboard/stats
```

### Check Logs:
Look for:
```
Started RestaurantApiApplication in X.XXX seconds
```

---

## 🐛 Troubleshooting

### Issue: "Connection refused"
- **Solution:** Make sure Spring Boot is running on port 8080

### Issue: "401 Unauthorized"
- **Solution:** Check that JWT token is valid and included in Authorization header

### Issue: "403 Forbidden"
- **Solution:** User must have ADMIN role. Check user role in database or login response.

### Issue: "MongoDB connection error"
- **Solution:** Ensure MongoDB is running: `mongod` or start MongoDB service

### Issue: "Image not found"
- **Solution:** Make sure image was uploaded successfully and imageId is correct

---

## 📝 Quick Test Checklist

- [ ] Backend starts without errors
- [ ] Can create admin user
- [ ] Can login and get JWT token
- [ ] Can get dashboard stats (requires ADMIN)
- [ ] Can create menu item with image
- [ ] Can retrieve image from GridFS
- [ ] Can get all users
- [ ] Can update user role
- [ ] Can get all reservations
- [ ] Can update reservation status

---

## 🎯 Next Steps

1. **Test with Vue Frontend:**
   - Start Vue dev server: `npm run dev` (in cuisine-wave-frontend)
   - Login as admin
   - Test Admin Dashboard UI

2. **Integration Testing:**
   - Test full flow: Create menu → View in dashboard → Update → Delete
   - Test user management flow
   - Test reservation management flow

---

**Happy Testing! 🚀**

