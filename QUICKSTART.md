# EventPro AI - Quick Start Guide

Get up and running with EventPro AI in 10 minutes! ⚡

## Prerequisites Checklist

- [ ] Android Studio Hedgehog (2023.1.1) or later installed
- [ ] JDK 17 configured
- [ ] Android device or emulator available
- [ ] Firebase account created
- [ ] Git installed

## 5-Minute Firebase Setup

### Step 1: Create Firebase Project (1 minute)
1. Go to https://console.firebase.google.com/
2. Click "Add project"
3. Name it "EventPro AI"
4. Click through the setup (disable Analytics if you want)

### Step 2: Add Android App (1 minute)
1. Click Android icon in Firebase Console
2. Package name: `com.eventpro.ai`
3. Download `google-services.json`
4. Click "Register app"

### Step 3: Enable Services (2 minutes)
1. **Authentication** → Sign-in method → Enable "Email/Password" and "Google"
2. **Firestore Database** → Create database → Production mode → Enable
3. **Storage** → Get started → Production mode → Done
4. **Cloud Messaging** → Automatically enabled ✓

### Step 4: Security Rules (1 minute)
Copy-paste this into Firestore Rules:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## 5-Minute App Setup

### Step 1: Clone & Open (1 minute)
```bash
git clone https://github.com/Dakshit06/event-management.git
cd event-management
```
Open in Android Studio → Wait for indexing

### Step 2: Add Firebase Config (30 seconds)
- Copy your `google-services.json` to `app/` folder
- That's it!

### Step 3: Get SHA-1 (1 minute)
```bash
./gradlew signingReport
```
- Copy the SHA-1 from debug output
- Firebase Console → Project Settings → Add SHA-1
- Download updated `google-services.json`

### Step 4: Sync & Build (1 minute)
- Click "Sync Now" in Android Studio
- Wait for Gradle sync to complete

### Step 5: Run! (1.5 minutes)
- Click Run button (green play icon)
- Or press Shift + F10
- Wait for app to launch

## First Launch

When the app opens:
1. You'll see the splash screen
2. Then the login screen
3. Click "Sign Up" to create your first account
4. Fill in your details and register
5. You're in! 🎉

## Add Sample Data (Optional)

The event list will be empty initially. Add a test event in Firebase Console:

1. Go to Firestore Database
2. Start collection: `events`
3. Add document with ID: `test_event_001`
4. Copy-paste this JSON:

```json
{
  "id": "test_event_001",
  "title": "Tech Conference 2025",
  "description": "Join us for the biggest tech conference of the year!",
  "category": "CONFERENCE",
  "imageUrl": "https://picsum.photos/400/300",
  "bannerUrl": "https://picsum.photos/800/400",
  "organizerId": "org_001",
  "organizerName": "TechEvents Inc",
  "capacity": 500,
  "registeredCount": 150,
  "isPublished": true,
  "status": "PUBLISHED",
  "venue": {
    "name": "Grand Convention Center",
    "city": "San Francisco",
    "address": "123 Conference Way",
    "country": "USA",
    "latitude": 37.7749,
    "longitude": -122.4194
  },
  "tags": ["technology", "networking", "innovation"],
  "hashtag": "#TechConf2025"
}
```

5. Save → Refresh the app → You'll see your event!

## Troubleshooting

**Q: App crashes on launch**
- Make sure `google-services.json` is in `app/` folder
- Check package name is exactly `com.eventpro.ai`

**Q: Google Sign-In doesn't work**
- Did you add SHA-1 to Firebase Console?
- Did you download the updated `google-services.json`?

**Q: Gradle sync fails**
- File → Invalidate Caches → Invalidate and Restart

**Q: Build errors**
- Check your internet connection
- Sync project with Gradle files again

**Q: Events don't show up**
- Did you add the sample event to Firestore?
- Check Firestore security rules allow read access

## What to Explore

Now that you're set up, explore these features:

✅ **Working Now:**
- User registration and login
- Event browsing
- Event details
- User profile
- Firebase integration

🚧 **Coming Soon:**
- AI recommendations
- Networking matches
- QR code tickets
- Payment integration
- Real-time chat

## Next Steps

1. **Read the docs**: Check out README.md and SETUP.md
2. **Explore the code**: Navigate through the clean architecture
3. **Add features**: Pick a feature from CONTRIBUTING.md
4. **Join discussions**: Ask questions in GitHub Discussions
5. **Contribute**: Submit your first PR!

## Resources

- 📚 Full Documentation: [README.md](README.md)
- 🔧 Detailed Setup: [SETUP.md](SETUP.md)
- 🤝 Contributing: [CONTRIBUTING.md](CONTRIBUTING.md)
- 📊 Project Status: [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)

## Get Help

- **Issues**: https://github.com/Dakshit06/event-management/issues
- **Discussions**: https://github.com/Dakshit06/event-management/discussions

---

**🎉 That's it! You're all set up and ready to build amazing event experiences!**

*Happy coding!* 🚀
