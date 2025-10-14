# EventPro AI - Setup Guide

This guide will help you set up the EventPro AI Android application from scratch.

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio Hedgehog (2023.1.1) or later**
  - Download from: https://developer.android.com/studio

- **JDK 17**
  - Android Studio includes JDK, or download from: https://adoptium.net/

- **Git**
  - Download from: https://git-scm.com/downloads

- **Android Device or Emulator**
  - Physical device with USB debugging enabled, OR
  - Android Virtual Device (AVD) with API 24+ 

## 🔥 Firebase Setup (Critical)

### Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **"Add project"**
3. Enter project name: **"EventPro AI"** (or your preferred name)
4. Enable/Disable Google Analytics (optional)
5. Click **"Create project"**

### Step 2: Register Android App

1. In Firebase Console, click on **Android icon** to add an Android app
2. Enter package name: **`com.eventpro.ai`** (must match exactly)
3. Enter app nickname: **EventPro AI**
4. Leave Debug signing certificate SHA-1 empty for now
5. Click **"Register app"**

### Step 3: Download Configuration File

1. Download **`google-services.json`**
2. Place it in the `app/` directory of your project
3. **IMPORTANT**: Do NOT commit this file to version control

### Step 4: Enable Authentication

1. In Firebase Console, go to **Authentication** → **Sign-in method**
2. Enable **Email/Password**
   - Toggle the switch to enable
   - Click **Save**

3. Enable **Google Sign-In**
   - Click on Google provider
   - Toggle the switch to enable
   - Enter project support email
   - Click **Save**

### Step 5: Create Firestore Database

1. Go to **Firestore Database** in Firebase Console
2. Click **"Create database"**
3. Choose **"Start in production mode"**
4. Select a location (choose closest to your users)
5. Click **"Enable"**

6. **Set up Security Rules** (replace existing rules):
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users collection
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth.uid == userId;
    }
    
    // Events collection
    match /events/{eventId} {
      allow read: if true;
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null && 
        resource.data.organizerId == request.auth.uid;
    }
    
    // Sessions collection
    match /sessions/{sessionId} {
      allow read: if true;
      allow write: if request.auth != null;
    }
    
    // Tickets collection
    match /tickets/{ticketId} {
      allow read: if request.auth != null && 
        resource.data.userId == request.auth.uid;
      allow create: if request.auth != null;
    }
    
    // Chat messages
    match /chatMessages/{messageId} {
      allow read, write: if request.auth != null;
    }
  }
}
```

### Step 6: Enable Firebase Storage

1. Go to **Storage** in Firebase Console
2. Click **"Get started"**
3. Choose **"Start in production mode"**
4. Click **"Next"** and **"Done"**

5. **Set up Storage Rules**:
```javascript
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /events/{eventId}/{allPaths=**} {
      allow read: if true;
      allow write: if request.auth != null;
    }
    match /users/{userId}/{allPaths=**} {
      allow read: if true;
      allow write: if request.auth.uid == userId;
    }
  }
}
```

### Step 7: Enable Cloud Messaging

1. Go to **Cloud Messaging** in Firebase Console
2. Cloud Messaging is automatically enabled
3. No additional configuration needed for basic setup

### Step 8: Get SHA-1 Fingerprint (for Google Sign-In)

1. Open terminal in your project directory
2. Run:
```bash
# For Linux/Mac
./gradlew signingReport

# For Windows
gradlew.bat signingReport
```

3. Copy the SHA-1 fingerprint from the debug variant
4. In Firebase Console:
   - Go to **Project Settings** (gear icon)
   - Scroll to **Your apps**
   - Click on your Android app
   - Scroll to **SHA certificate fingerprints**
   - Click **"Add fingerprint"**
   - Paste SHA-1 and click **Save**

5. **Download the updated `google-services.json`**
6. Replace the old one in your `app/` directory

## 📦 Project Setup

### Step 1: Clone Repository

```bash
git clone https://github.com/Dakshit06/event-management.git
cd event-management
```

### Step 2: Add Firebase Configuration

1. Copy your `google-services.json` to `app/` directory
2. Verify it's in the correct location:
   ```
   event-management/
   └── app/
       └── google-services.json  ← Here
   ```

### Step 3: Open in Android Studio

1. Launch Android Studio
2. Click **"Open"**
3. Navigate to the `event-management` folder
4. Click **"OK"**
5. Wait for Gradle sync to complete

### Step 4: Sync Gradle

1. If not auto-synced, click **"Sync Now"** in the banner
2. Or go to **File** → **Sync Project with Gradle Files**
3. Wait for dependencies to download

### Step 5: Build Project

1. Click **Build** → **Make Project**
2. Or press **Ctrl+F9** (Windows/Linux) / **⌘F9** (Mac)
3. Ensure build is successful

## 🚀 Running the App

### On Physical Device

1. Enable Developer Options on your Android device:
   - Go to **Settings** → **About phone**
   - Tap **Build number** 7 times

2. Enable USB Debugging:
   - Go to **Settings** → **Developer options**
   - Enable **USB debugging**

3. Connect device via USB

4. In Android Studio:
   - Select your device from the dropdown
   - Click **Run** (green play button) or press **Shift+F10**

### On Emulator

1. Create AVD (if not exists):
   - Click **Device Manager** (phone icon)
   - Click **"Create device"**
   - Select a device (e.g., Pixel 5)
   - Select system image (API 24+, recommended: API 33)
   - Click **"Finish"**

2. Start emulator:
   - Click the play button next to your AVD

3. Run app:
   - Select emulator from dropdown
   - Click **Run**

## ✅ Verify Installation

After running the app, you should see:

1. **Splash Screen** → Auto navigates to Login
2. **Login Screen** → Try creating an account
3. **Register** → Create a test account
4. **Home Screen** → Should load successfully
5. **Event List** → May be empty (add sample data in Firestore)

## 🧪 Adding Sample Data

To test the app with sample events, add data to Firestore:

1. Go to Firebase Console → Firestore Database
2. Click **"Start collection"**
3. Collection ID: **`events`**
4. Add a sample document with these fields:

```json
{
  "id": "event_001",
  "title": "Tech Conference 2025",
  "description": "Annual technology conference",
  "category": "CONFERENCE",
  "imageUrl": "https://picsum.photos/400/300",
  "bannerUrl": "https://picsum.photos/800/400",
  "organizerId": "org_001",
  "organizerName": "TechOrg Inc",
  "venue": {
    "name": "Convention Center",
    "city": "San Francisco",
    "address": "123 Main St",
    "country": "USA"
  },
  "capacity": 500,
  "registeredCount": 142,
  "isPublished": true,
  "status": "PUBLISHED",
  "tags": ["technology", "ai", "innovation"],
  "hashtag": "#TechConf2025"
}
```

## 🐛 Troubleshooting

### Gradle Sync Issues
- **Solution**: File → Invalidate Caches → Invalidate and Restart

### Google Sign-In Not Working
- **Solution**: Ensure SHA-1 is added to Firebase Console
- Re-download `google-services.json`

### Firestore Permission Denied
- **Solution**: Check security rules
- Ensure user is authenticated

### App Crashes on Launch
- **Solution**: Check `google-services.json` is in correct location
- Verify package name matches: `com.eventpro.ai`

### Dependencies Not Resolving
- **Solution**: Check internet connection
- File → Sync Project with Gradle Files

## 📚 Next Steps

1. **Explore the App**: Navigate through different screens
2. **Add Features**: Implement remaining features from the roadmap
3. **Customize**: Update colors, branding, and content
4. **Deploy**: Set up CI/CD and prepare for production

## 🆘 Getting Help

- **Issues**: https://github.com/Dakshit06/event-management/issues
- **Discussions**: https://github.com/Dakshit06/event-management/discussions
- **Email**: dakshit@example.com

## 🎉 You're All Set!

Congratulations! You've successfully set up EventPro AI. Happy coding! 🚀
