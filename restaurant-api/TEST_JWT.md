# JWT Authentication Testing Guide

## Prerequisites
- Backend running on http://localhost:8080
- MongoDB running

## Step 1: Test Signup (Public Endpoint)
```
POST http://localhost:8080/api/auth/signup
Content-Type: application/json

{
  "username": "testuser",
  "email": "test@example.com",
  "password": "password123",
  "phoneNumber": "1234567890"
}
```

**Expected Response:**
```json
{
  "id": "...",
  "username": "testuser",
  "email": "test@example.com",
  "phoneNumber": "1234567890",
  "password": null
}
```

## Step 2: Test Login (Get JWT Token)
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "password123"
}
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNzM2NDE2ODAwLCJleHAiOjE3MzY1MDMyMDB9...",
  "user": {
    "id": "...",
    "username": "testuser",
    "email": "test@example.com",
    "phoneNumber": "1234567890",
    "password": null
  }
}
```

**Copy the token value!**

## Step 3: Test Protected Endpoint WITHOUT Token (Should Fail)
```
GET http://localhost:8080/api/test
```

**Expected Response:**
- Status: 403 Forbidden
- Or: 401 Unauthorized

## Step 4: Test Protected Endpoint WITH Token (Should Work)
```
GET http://localhost:8080/api/test
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGV4YW1wbGUuY29tIiwiaWF0IjoxNzM2NDE2ODAwLCJleHAiOjE3MzY1MDMyMDB9...
```

**Expected Response:**
```
JWT works!
```

## Step 5: Test with Invalid Token (Should Fail)
```
GET http://localhost:8080/api/test
Authorization: Bearer invalid_token_here
```

**Expected Response:**
- Status: 403 Forbidden

## Browser Console Test (Frontend)

Open browser console and run:
```javascript
// Check if token is stored
console.log('Token:', localStorage.getItem('token'))
console.log('User:', localStorage.getItem('user'))

// Test protected endpoint
fetch('http://localhost:8080/api/test', {
  headers: {
    'Authorization': `Bearer ${localStorage.getItem('token')}`
  }
})
.then(res => res.text())
.then(data => console.log('Response:', data))
.catch(err => console.error('Error:', err))
```

