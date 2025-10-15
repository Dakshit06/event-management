# 🔥 Firebase Setup - Quick Action Guide

## ✅ What's Done

Your EventPro AI app is now connected to Firebase project **`eventpro-deb95`**!

- ✅ Real `google-services.json` configured
- ✅ Firestore security rules created
- ✅ Storage security rules created
- ✅ App rebuilt with real Firebase (127 MB APK)
- ✅ All code pushed to GitHub

---

## 🚀 2 MINUTES TO GO LIVE!

### **Step 1: Deploy Firestore Rules (1 minute)**

1. Open: https://console.firebase.google.com/project/eventpro-deb95/firestore/rules

2. **Replace ALL the rules** with this:

```javascript
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {
    
    function isSignedIn() {
      return request.auth != null;
    }
    
    function isOwner(userId) {
      return isSignedIn() && request.auth.uid == userId;
    }
    
    match /users/{userId} {
      allow read: if isSignedIn();
      allow create: if isSignedIn() && request.auth.uid == userId;
      allow update, delete: if isOwner(userId);
    }
    
    match /events/{eventId} {
      allow read: if true;
      allow create: if isSignedIn();
      allow update, delete: if isSignedIn() && resource.data.organizerId == request.auth.uid;
      
      match /sessions/{sessionId} {
        allow read: if true;
        allow create, update, delete: if isSignedIn();
      }
    }
    
    match /sessions/{sessionId} {
      allow read: if true;
      allow create, update, delete: if isSignedIn();
    }
    
    match /tickets/{ticketId} {
      allow read: if isSignedIn() && resource.data.userId == request.auth.uid;
      allow create: if isSignedIn() && request.resource.data.userId == request.auth.uid;
      allow update: if isSignedIn() && resource.data.userId == request.auth.uid;
      allow delete: if isOwner(resource.data.userId);
    }
    
    match /speakers/{speakerId} {
      allow read: if true;
      allow create, update, delete: if isSignedIn();
    }
    
    match /sponsors/{sponsorId} {
      allow read: if true;
      allow create, update, delete: if isSignedIn();
    }
    
    match /chats/{chatId} {
      allow read, write: if isSignedIn() && request.auth.uid in resource.data.participants;
      
      match /messages/{messageId} {
        allow read, create: if isSignedIn();
        allow update, delete: if isSignedIn() && resource.data.senderId == request.auth.uid;
      }
    }
    
    match /notifications/{notificationId} {
      allow read, write: if isSignedIn() && resource.data.userId == request.auth.uid;
    }
    
    match /ai_recommendations/{recommendationId} {
      allow read: if isSignedIn() && resource.data.userId == request.auth.uid;
      allow create, update: if isSignedIn();
    }
    
    match /analytics/{analyticsId} {
      allow read, write: if isSignedIn();
    }
  }
}
```

3. Click **"Publish"**

---

### **Step 2: Deploy Storage Rules (1 minute)**

1. Open: https://console.firebase.google.com/project/eventpro-deb95/storage/rules

2. **Replace ALL the rules** with this:

```javascript
rules_version = '2';

service firebase.storage {
  match /b/{bucket}/o {
    
    function isSignedIn() {
      return request.auth != null;
    }
    
    function isValidImage() {
      return request.resource.size < 5 * 1024 * 1024 
             && request.resource.contentType.matches('image/.*');
    }
    
    match /profile_images/{userId}/{fileName} {
      allow read: if true;
      allow write: if isSignedIn() && request.auth.uid == userId && isValidImage();
    }
    
    match /event_images/{eventId}/{fileName} {
      allow read: if true;
      allow write: if isSignedIn() && isValidImage();
    }
    
    match /event_banners/{eventId}/{fileName} {
      allow read: if true;
      allow write: if isSignedIn() && isValidImage();
    }
    
    match /speaker_images/{speakerId}/{fileName} {
      allow read: if true;
      allow write: if isSignedIn() && isValidImage();
    }
    
    match /sponsor_logos/{sponsorId}/{fileName} {
      allow read: if true;
      allow write: if isSignedIn() && isValidImage();
    }
    
    match /venue_maps/{eventId}/{fileName} {
      allow read: if true;
      allow write: if isSignedIn();
    }
    
    match /{allPaths=**} {
      allow read: if isSignedIn();
      allow write: if isSignedIn();
    }
  }
}
```

3. Click **"Publish"**

---

## ✅ Quick Verification Checklist

Visit these URLs to verify everything is set up:

### **1. Firestore Database**
- URL: https://console.firebase.google.com/project/eventpro-deb95/firestore
- Status: Should show "Cloud Firestore" ready
- Action: If not created, click "Create database" → Production mode → Select location

### **2. Authentication**
- URL: https://console.firebase.google.com/project/eventpro-deb95/authentication
- Enable: Email/Password ✅
- Enable: Google Sign-In ✅
- Support Email: jayborhade2004@gmail.com ✅

### **3. Cloud Storage**
- URL: https://console.firebase.google.com/project/eventpro-deb95/storage
- Status: Should be enabled
- Action: If not, click "Get started" → Production mode → Same location as Firestore

### **4. Cloud Messaging**
- URL: https://console.firebase.google.com/project/eventpro-deb95/settings/cloudmessaging
- Status: ✅ Already enabled (automatic)

---

## 🎯 Test Your Setup

### **Install & Test (3 minutes)**

```bash
# Download the APK
# File: /workspaces/event-management/app/build/outputs/apk/debug/app-debug.apk

# Install on Android device
adb install app/build/outputs/apk/debug/app-debug.apk

# Open the app and test:
# 1. Register new account ✅
# 2. Check Firebase Console → Authentication → Users (should see new user!)
# 3. Create an event in app ✅
# 4. Check Firebase Console → Firestore → events (should see new event!)
```

---

## 🐛 Troubleshooting

### **"Permission denied" errors:**
✅ Make sure you deployed Firestore rules (Step 1)  
✅ Make sure you deployed Storage rules (Step 2)

### **"User registration fails":**
✅ Enable Email/Password in Authentication  
✅ Enable Google Sign-In in Authentication

### **"Google Sign-In doesn't work":**
You need to add SHA-1 certificate:

```bash
# Get SHA-1 (debug keystore)
keytool -list -v -keystore ~/.android/debug.keystore \
  -alias androiddebugkey \
  -storepass android -keypass android | grep SHA1

# Then add to Firebase:
# https://console.firebase.google.com/project/eventpro-deb95/settings/general
# → Your apps → Add fingerprint
```

---

## 📊 Firebase Console Quick Links

| Service | URL |
|---------|-----|
| **Project Overview** | https://console.firebase.google.com/project/eventpro-deb95 |
| **Firestore Database** | https://console.firebase.google.com/project/eventpro-deb95/firestore |
| **Firestore Rules** | https://console.firebase.google.com/project/eventpro-deb95/firestore/rules |
| **Authentication** | https://console.firebase.google.com/project/eventpro-deb95/authentication |
| **Storage** | https://console.firebase.google.com/project/eventpro-deb95/storage |
| **Storage Rules** | https://console.firebase.google.com/project/eventpro-deb95/storage/rules |
| **Cloud Messaging** | https://console.firebase.google.com/project/eventpro-deb95/settings/cloudmessaging |
| **Project Settings** | https://console.firebase.google.com/project/eventpro-deb95/settings/general |

---

## 🎉 You're Almost Live!

After completing Steps 1 & 2, your EventPro AI app will have:

- ✅ **Working Authentication** - Users can sign up & sign in
- ✅ **Real Database** - Events, sessions, tickets stored
- ✅ **File Uploads** - Profile pics, event images
- ✅ **Push Notifications** - FCM ready
- ✅ **Production Security** - Rules deployed

**Total setup time: ~5 minutes** 🚀

For detailed documentation, see: **FIREBASE_INTEGRATION.md**
