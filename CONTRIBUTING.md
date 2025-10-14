# Contributing to EventPro AI

First off, thank you for considering contributing to EventPro AI! It's people like you that make EventPro AI such a great tool.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the existing issues as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

* **Use a clear and descriptive title**
* **Describe the exact steps to reproduce the problem**
* **Provide specific examples**
* **Describe the behavior you observed and what you expected**
* **Include screenshots if possible**
* **Include your Android version and device information**

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion, please include:

* **Use a clear and descriptive title**
* **Provide a detailed description of the suggested enhancement**
* **Explain why this enhancement would be useful**
* **List some examples of how it would be used**

### Pull Requests

* Fill in the required template
* Follow the Kotlin coding style
* Include appropriate test cases
* Update documentation as needed
* End all files with a newline

## Development Setup

1. **Fork and clone the repo**
```bash
git clone https://github.com/YOUR_USERNAME/event-management.git
cd event-management
```

2. **Create a branch**
```bash
git checkout -b feature/amazing-feature
```

3. **Set up Firebase**
   - Follow the instructions in SETUP.md
   - Add your own `google-services.json`

4. **Build and run**
   - Open project in Android Studio
   - Sync Gradle files
   - Run on device or emulator

## Coding Guidelines

### Kotlin Style Guide

* Follow the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
* Use meaningful variable and function names
* Keep functions small and focused
* Add comments for complex logic
* Use data classes for model objects

### Architecture

* Follow MVVM architecture pattern
* Keep business logic in ViewModels
* Use Repository pattern for data access
* Keep UI logic in Composables

### Compose Guidelines

* Use `@Composable` functions for UI components
* Keep Composables small and reusable
* Use `remember` and state hoisting appropriately
* Follow Material Design 3 guidelines

### File Organization

```
com.eventpro.ai/
├── data/
│   ├── model/          # Data classes
│   └── repository/     # Data repositories
├── di/                 # Dependency injection
├── service/            # Background services
├── ui/
│   ├── screens/        # Screen composables
│   ├── components/     # Reusable UI components
│   ├── navigation/     # Navigation setup
│   ├── theme/          # Theme configuration
│   └── viewmodel/      # ViewModels
└── util/               # Utility classes
```

### Git Commit Messages

* Use the present tense ("Add feature" not "Added feature")
* Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
* Limit the first line to 72 characters or less
* Reference issues and pull requests liberally after the first line

Example:
```
Add AI recommendation feature

- Implement recommendation algorithm
- Add recommendation UI
- Update Firebase schema
- Add unit tests

Fixes #123
```

### Testing

* Write unit tests for ViewModels and Repositories
* Write UI tests for critical user flows
* Ensure all tests pass before submitting PR
* Aim for at least 70% code coverage

## Areas for Contribution

We especially welcome contributions in these areas:

### High Priority
- [ ] AI Recommendation Engine implementation
- [ ] Intelligent Networking Matchmaking
- [ ] AI Chatbot integration
- [ ] QR Code scanning functionality
- [ ] Payment gateway integration
- [ ] Real-time chat features

### Medium Priority
- [ ] Interactive venue maps
- [ ] Session scheduling with calendar integration
- [ ] Push notification handling improvements
- [ ] Offline mode support
- [ ] Advanced search and filtering
- [ ] User preferences and settings

### Low Priority
- [ ] UI/UX improvements
- [ ] Performance optimizations
- [ ] Accessibility improvements
- [ ] Localization (i18n)
- [ ] Dark mode enhancements
- [ ] Animation improvements

## Documentation

* Update README.md if needed
* Update SETUP.md for new setup steps
* Add KDoc comments to public APIs
* Update in-code comments for complex logic

## Firebase Development

### Firestore Best Practices
* Use appropriate indexes
* Minimize read/write operations
* Use batched writes when possible
* Implement proper security rules

### Storage Best Practices
* Compress images before upload
* Use appropriate file naming conventions
* Implement file size limits
* Clean up orphaned files

## Questions?

Feel free to create an issue with the `question` label or reach out to the maintainers.

## Recognition

Contributors will be recognized in our README.md file. Thank you for your contributions!

---

**Happy Coding!** 🚀
