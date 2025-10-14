# EventPro AI - API Reference

## Data Models

### User
```kotlin
data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val photoUrl: String,
    val role: UserRole,
    val interests: List<String>,
    val professionalBackground: String,
    val company: String,
    val jobTitle: String,
    val bio: String,
    val linkedInUrl: String,
    val location: String,
    val networkingGoals: String,
    val fcmToken: String,
    val createdAt: Timestamp,
    val updatedAt: Timestamp
)
```

### Event
```kotlin
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val organizerId: String,
    val organizerName: String,
    val category: EventCategory,
    val imageUrl: String,
    val bannerUrl: String,
    val venue: Venue,
    val startDate: Timestamp,
    val endDate: Timestamp,
    val registrationDeadline: Timestamp,
    val capacity: Int,
    val registeredCount: Int,
    val tags: List<String>,
    val ticketTypes: List<TicketType>,
    val speakerIds: List<String>,
    val sponsorIds: List<String>,
    val status: EventStatus,
    val isPublished: Boolean
)
```

## Repositories

### AuthRepository

#### Methods

**signUpWithEmail**
```kotlin
suspend fun signUpWithEmail(
    email: String, 
    password: String, 
    displayName: String
): Resource<User>
```
Creates a new user account with email and password.

**signInWithEmail**
```kotlin
suspend fun signInWithEmail(
    email: String, 
    password: String
): Resource<User>
```
Signs in an existing user.

**signInWithGoogle**
```kotlin
suspend fun signInWithGoogle(
    idToken: String
): Resource<User>
```
Signs in with Google OAuth.

**signOut**
```kotlin
suspend fun signOut()
```
Signs out the current user.

**updateUserProfile**
```kotlin
suspend fun updateUserProfile(
    user: User
): Resource<Unit>
```
Updates user profile information.

---

### EventRepository

#### Methods

**createEvent**
```kotlin
suspend fun createEvent(
    event: Event
): Resource<String>
```
Creates a new event and returns the event ID.

**updateEvent**
```kotlin
suspend fun updateEvent(
    event: Event
): Resource<Unit>
```
Updates an existing event.

**getEvent**
```kotlin
suspend fun getEvent(
    eventId: String
): Resource<Event>
```
Fetches a single event by ID.

**observeEvents**
```kotlin
fun observeEvents(): Flow<Resource<List<Event>>>
```
Returns a Flow that emits published events in real-time.

**searchEvents**
```kotlin
suspend fun searchEvents(
    query: String
): Resource<List<Event>>
```
Searches for events matching the query.

**registerForEvent**
```kotlin
suspend fun registerForEvent(
    eventId: String, 
    userId: String
): Resource<Unit>
```
Registers a user for an event.

---

### SessionRepository

**createSession**
```kotlin
suspend fun createSession(
    session: Session
): Resource<String>
```

**observeSessionsByEvent**
```kotlin
fun observeSessionsByEvent(
    eventId: String
): Flow<Resource<List<Session>>>
```

**addUserToSession**
```kotlin
suspend fun addUserToSession(
    sessionId: String, 
    userId: String
): Resource<Unit>
```

**removeUserFromSession**
```kotlin
suspend fun removeUserFromSession(
    sessionId: String, 
    userId: String
): Resource<Unit>
```

---

### TicketRepository

**createTicket**
```kotlin
suspend fun createTicket(
    ticket: Ticket
): Resource<String>
```

**observeUserTickets**
```kotlin
fun observeUserTickets(
    userId: String
): Flow<Resource<List<Ticket>>>
```

**checkInTicket**
```kotlin
suspend fun checkInTicket(
    ticketId: String
): Resource<Unit>
```

---

### ChatRepository

**createOrGetChatRoom**
```kotlin
suspend fun createOrGetChatRoom(
    userId1: String, 
    userId2: String, 
    eventId: String
): Resource<String>
```

**sendMessage**
```kotlin
suspend fun sendMessage(
    chatRoomId: String, 
    message: ChatMessage
): Resource<Unit>
```

**observeMessages**
```kotlin
fun observeMessages(
    chatRoomId: String
): Flow<Resource<List<ChatMessage>>>
```

**observeUserChatRooms**
```kotlin
fun observeUserChatRooms(
    userId: String
): Flow<Resource<List<ChatRoom>>>
```

---

### AIRepository

**generateRecommendations**
```kotlin
suspend fun generateRecommendations(
    userId: String, 
    eventId: String
): Resource<List<AIRecommendation>>
```

**observeRecommendations**
```kotlin
fun observeRecommendations(
    userId: String, 
    eventId: String
): Flow<Resource<List<AIRecommendation>>>
```

**generateNetworkingMatches**
```kotlin
suspend fun generateNetworkingMatches(
    userId: String, 
    eventId: String
): Resource<List<NetworkingMatch>>
```

## ViewModels

### AuthViewModel

#### State
```kotlin
data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
```

#### Methods

**signInWithEmail**
```kotlin
fun signInWithEmail(email: String, password: String)
```

**signUpWithEmail**
```kotlin
fun signUpWithEmail(
    email: String, 
    password: String, 
    displayName: String
)
```

**signOut**
```kotlin
fun signOut()
```

**updateProfile**
```kotlin
fun updateProfile(user: User)
```

---

### EventViewModel

#### State
```kotlin
data class EventListState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

#### Methods

**loadEvent**
```kotlin
fun loadEvent(eventId: String)
```

**searchEvents**
```kotlin
fun searchEvents(query: String)
```

**registerForEvent**
```kotlin
fun registerForEvent(
    eventId: String, 
    userId: String, 
    onResult: (Boolean, String) -> Unit
)
```

## Utility Classes

### QRCodeGenerator

**generateQRCode**
```kotlin
fun generateQRCode(
    content: String, 
    width: Int = 512, 
    height: Int = 512
): Bitmap?
```

**generateTicketQRData**
```kotlin
fun generateTicketQRData(
    ticketId: String,
    eventId: String,
    userId: String
): String
```

---

### DateTimeUtils

**formatDate**
```kotlin
fun formatDate(date: Date): String
```
Returns: "MMM dd, yyyy"

**formatTime**
```kotlin
fun formatTime(date: Date): String
```
Returns: "h:mm a"

**formatDateTime**
```kotlin
fun formatDateTime(date: Date): String
```
Returns: "MMM dd, yyyy 'at' h:mm a"

**getRelativeTime**
```kotlin
fun getRelativeTime(date: Date): String
```
Returns: "Just now", "5 minutes ago", etc.

---

### ValidationUtils

**isValidEmail**
```kotlin
fun isValidEmail(email: String): Boolean
```

**isValidPassword**
```kotlin
fun isValidPassword(password: String): Boolean
```

**isStrongPassword**
```kotlin
fun isStrongPassword(password: String): Boolean
```

**getPasswordStrength**
```kotlin
fun getPasswordStrength(password: String): PasswordStrength
```

## Resource Class

Sealed class for handling async operations:

```kotlin
sealed class Resource<T> {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
```

## Navigation Routes

```kotlin
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object EventList : Screen("event_list")
    object EventDetail : Screen("event_detail/{eventId}")
    object Schedule : Screen("schedule/{eventId}")
    object Profile : Screen("profile")
    // ... more routes
}
```

## Firestore Collections

### Collection Names
- `users` - User profiles
- `events` - Event data
- `sessions` - Conference sessions
- `tickets` - User tickets
- `speakers` - Speaker profiles
- `sponsors` - Sponsor information
- `chatRooms` - Chat rooms
- `chatMessages` - Chat messages
- `notifications` - Push notifications
- `aiRecommendations` - AI recommendations
- `networkingMatches` - Networking matches

### Security Rules Template

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
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null && 
        resource.data.organizerId == request.auth.uid;
    }
  }
}
```

## Constants

See `Constants.kt` for all app-wide constants including:
- Collection names
- Preference keys
- Request codes
- Pagination settings
- Error messages
- Success messages

---

*For more details, see the source code documentation.*
