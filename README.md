# EventPro AI - AI-Powered Event Management System for Android

<p align="center">
  <strong>A next-generation, mobile-first event management platform for Android</strong>
</p>

## 📱 Overview

EventPro AI leverages Artificial Intelligence to create highly personalized, engaging, and efficient event experiences for attendees, while providing organizers with powerful predictive analytics and operational tools.

## ✨ Key Features

### For Attendees

#### 🎯 **AI-Powered Features**
- **Personalized Agenda Recommendations**: AI analyzes user profiles and LinkedIn data to recommend relevant sessions
- **Intelligent Matchmaking**: NLP-powered networking suggestions based on professional goals
- **AI Concierge Chatbot**: 24/7 LLM-powered assistant for event queries
- **Real-time Session Translation**: Speech-to-text and translation for international accessibility

#### 📅 **Core Features**
- Secure authentication (Email, Google, LinkedIn)
- Dynamic event discovery and search
- Interactive event schedules
- QR code-based ticketing
- In-app networking and chat
- Push notifications for updates
- Interactive venue maps

### For Organizers

#### 📊 **AI Analytics**
- **Predictive Analytics**: Forecast attendance, session popularity, and resource needs
- **Real-time Sentiment Analysis**: Monitor attendee sentiment via chat and social media
- **Automated Content Summarization**: Generate session summaries and highlights
- **Crowd Flow Heatmaps**: Track venue traffic patterns in real-time

#### 🛠️ **Management Tools**
- Event creation and management dashboard
- Speaker and sponsor management
- Ticket configuration and sales
- Real-time attendee tracking

## 🏗️ Technical Architecture

### **Frontend (Android App)**
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Clean Architecture
- **Dependency Injection**: Dagger Hilt
- **Navigation**: Navigation Compose
- **Async**: Kotlin Coroutines & Flow

### **Backend (Firebase)**
- **Authentication**: Firebase Authentication
- **Database**: Cloud Firestore
- **Storage**: Firebase Storage
- **Messaging**: Firebase Cloud Messaging (FCM)
- **Analytics**: Firebase Analytics
- **ML**: Firebase ML Kit

### **AI/ML Services**
- Google Cloud Platform (Vertex AI, Natural Language API, Translation API)
- ML Kit for on-device translation and text recognition

## 📂 Project Structure

```
app/src/main/java/com/eventpro/ai/
├── data/
│   ├── model/           # Data models (Event, User, Session, etc.)
│   └── repository/      # Data layer with Firebase operations
├── di/                  # Dependency injection modules
├── service/             # Background services (FCM)
├── ui/
│   ├── navigation/      # Navigation configuration
│   ├── screens/         # Composable screens
│   │   ├── auth/        # Login, Register
│   │   ├── events/      # Event list, details
│   │   ├── home/        # Home dashboard
│   │   ├── profile/     # User profile
│   │   └── splash/      # Splash screen
│   ├── theme/           # Material 3 theming
│   └── viewmodel/       # ViewModels for state management
├── util/                # Utility classes
├── EventProApplication.kt
└── MainActivity.kt
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or newer
- JDK 17
- Android SDK (API 24+)
- Firebase account
- Google Cloud Platform account (for AI features)

### Firebase Setup

1. **Create a Firebase Project**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project named "EventPro AI"
   - Enable Google Analytics (optional)

2. **Register Your Android App**
   - Package name: `com.eventpro.ai`
   - Download `google-services.json`
   - Place it in `app/` directory

3. **Enable Firebase Services**
   - **Authentication**: Enable Email/Password and Google Sign-In
   - **Firestore Database**: Create in production mode
   - **Storage**: Set up default bucket
   - **Cloud Messaging**: Enable FCM

4. **Firestore Security Rules** (Basic Setup)
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth.uid == userId;
    }
    match /events/{eventId} {
      allow read: if true;
      allow write: if request.auth != null;
    }
    match /sessions/{sessionId} {
      allow read: if true;
      allow write: if request.auth != null;
    }
  }
}
```

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/Dakshit06/event-management.git
cd event-management
```

2. **Add Firebase Configuration**
   - Place `google-services.json` in `app/` directory

3. **Google Sign-In Setup**
   - Get your SHA-1 fingerprint:
   ```bash
   ./gradlew signingReport
   ```
   - Add SHA-1 to Firebase Console (Project Settings → Your App)
   - Download updated `google-services.json`

4. **Sync Project**
   - Open project in Android Studio
   - Sync Gradle files
   - Build project

5. **Run the App**
   - Connect Android device or start emulator
   - Click Run or press Shift + F10

## 🔑 Key Dependencies

```kotlin
// Firebase
implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
implementation("com.google.firebase:firebase-auth-ktx")
implementation("com.google.firebase:firebase-firestore-ktx")
implementation("com.google.firebase:firebase-storage-ktx")
implementation("com.google.firebase:firebase-messaging-ktx")

// Jetpack Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.7.6")

// Hilt Dependency Injection
implementation("com.google.dagger:hilt-android:2.50")
kapt("com.google.dagger:hilt-android-compiler:2.50")

// ML Kit
implementation("com.google.mlkit:language-id:17.0.5")
implementation("com.google.mlkit:translate:17.0.2")

// QR Code
implementation("com.journeyapps:zxing-android-embedded:4.3.0")

// Image Loading
implementation("io.coil-kt:coil-compose:2.5.0")
```

## 📱 App Features Implementation Status

### ✅ Implemented
- [x] Firebase Authentication (Email/Password, Google Sign-In)
- [x] User Profile Management
- [x] Event Discovery and Search
- [x] Event Detail Views
- [x] Material 3 UI with Jetpack Compose
- [x] MVVM Architecture with ViewModels
- [x] Navigation System
- [x] Firebase Cloud Messaging Setup
- [x] Data Models for Events, Users, Sessions, Tickets, etc.
- [x] Repository Pattern for Data Layer

### 🚧 To Be Implemented
- [ ] QR Code Generation and Scanning
- [ ] Payment Gateway Integration
- [ ] AI Recommendation Engine
- [ ] Intelligent Networking Matchmaking
- [ ] AI Chatbot Integration
- [ ] Real-time Chat Functionality
- [ ] Session Scheduling
- [ ] Interactive Venue Maps
- [ ] Push Notification Handling
- [ ] Sentiment Analysis Dashboard
- [ ] Predictive Analytics
- [ ] Cloud Functions for Backend Logic

## 🎨 UI/UX Design

- **Design System**: Material Design 3
- **Theme**: Custom blue and orange color scheme
- **Typography**: Material 3 typography scale
- **Components**: Material 3 components (Cards, Buttons, FABs, etc.)
- **Dark Mode**: Supported (system preference)

## 🔒 Security

- Firebase Authentication for secure user management
- Firestore Security Rules for data protection
- HTTPS-only communication
- Encrypted local storage for sensitive data
- ProGuard/R8 code obfuscation in release builds

## 📊 Data Models

### Core Entities
- **User**: Profile, interests, professional background
- **Event**: Title, description, venue, dates, capacity
- **Session**: Schedule, speakers, room, attendees
- **Ticket**: QR code, purchase info, check-in status
- **Speaker**: Bio, expertise, sessions
- **Sponsor**: Tier, booth location, leads
- **ChatMessage**: Networking conversations
- **Notification**: Push notifications
- **AIRecommendation**: Personalized suggestions
- **SentimentAnalysis**: Event sentiment tracking

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Dakshit Patel**
- GitHub: [@Dakshit06](https://github.com/Dakshit06)

## 📞 Support

For support, email dakshit@example.com or open an issue in the GitHub repository.

## 🙏 Acknowledgments

- Firebase for backend infrastructure
- Google Cloud Platform for AI/ML services
- Jetpack Compose for modern Android UI
- Material Design for design guidelines
- Community contributors and open-source libraries

---

<p align="center">Made with ❤️ for the Event Management Community</p>
