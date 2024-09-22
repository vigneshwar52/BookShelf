# Book Library App

This is a Book Library application built using **Jetpack Compose** in **Android Studio**. The app allows users to sign up, log in, view a list of books, and mark books as favorites. The book list is grouped by the year of publication, with seamless switching between year tabs. The app integrates external APIs for country selection and book data fetching, ensuring a smooth user experience.

## Features

### Part 1: User Authentication (Login-Logout Flow)

- **Sign-Up:**
  - Users can sign up by entering their email, password, and selecting their country from a dropdown list.
  - Country list is populated using the **Country List API**.
  - Passwords must meet these criteria: minimum of 8 characters, including one number, one special character (!@#$%^&*()), one lowercase letter, and one uppercase letter.

- **Login Screen:**
  - Users can log in with their registered email and password.
  - After successful login, users are redirected to the **Book List Page**.

- **Logout Functionality:**
  - Users can log out, which takes them back to the login screen.

- **Expectations:**
  1. All edge cases are handled, including network issues and validation errors.
  2. Smooth and seamless sign-up/login user experience.
  3. Proper integration of the **Country List API**.

### Part 2: Book List Page

- **Book Data Display:**
  - Books are fetched from the **Book List API** and displayed on the home page.
  - Each book is shown with its title, rank, and cover thumbnail in a visually appealing manner.

- **Favorite Marking:**
  - Users can mark or unmark books as favorites using a heart/bookmark/like/thumb icon.
  - The UI reflects whether a book is marked as a favorite.

- **Year Tabs:**
  - Books are grouped into tabs based on their published year.
  - Year tabs switch automatically when scrolling through books from different years.
  - Only years with books are shown, and if there are many years, the tabs are horizontally scrollable.
  - Tabs are listed in descending order (from the most recent to the oldest year).

- **Expectations:**
  - No performance issues or UI jank when scrolling.
  - Proper handling of cases where there are many years, ensuring a smooth horizontal scroll for the year tabs.
  - The list of books is sorted by publication year, and the year tab switches as the user scrolls through books from different years.

## APIs Integrated

1. **Country List API:** Used to populate the country dropdown in the sign-up screen.
2. **IP Location API:** Automatically selects the default country during sign-up based on the user's IP address.
3. **Book List API:** Fetches book data, including title, rank, and cover image, for display on the Book List Page.

## Requirements

- Android Studio Arctic Fox or later.
- Kotlin with Jetpack Compose.
- Network libraries such as Retrofit for API calls.
- Internet connection for API integration.
  
## How to Run the Project

1. Clone the repository:
   ```bash
   git clone git@github.com:vigneshwar52/BookShelf.git
   ```
2. Open the project in Android Studio.
3. Build the project and run it on an Android emulator or a physical device.

## Project Structure

- **MainActivity.kt:** The entry point of the application, responsible for handling navigation between screens.
- **Authentication Components:** Includes sign-up, login, and logout functionality.
- **BookList Components:** Handles fetching and displaying books, marking books as favorites, and managing year tabs.

## Edge Cases Handled

- Invalid email or password inputs during sign-up and login.
- API failures and network issues during country list or book data fetching.
- Smooth transitions between screens, avoiding crashes or unexpected behavior.
  
## Future Enhancements

- Add user profile and settings management.
- Allow users to search books by title or author.
- Implement a local database for offline book viewing.

# BookShelf App Build and ScreenShots

<h2>Build for Installation</h2>
<p>Please check the GitHub repository for the application build.</p>

<h2>Screenshots</h2>
<h3>Login</h3>
<img src="https://github.com/user-attachments/assets/b6699d59-6177-4c75-97eb-520bf0e95f9f" width="250" height="500" />

<h3>Country LoginSuccess with Progress Dialog</h3>
<img src="https://github.com/user-attachments/assets/1ca9c03c-b6bf-4bc6-bf70-5276bcf81491" width="250" height="500" />

<h3>Book List Screen</h3>
<img src="https://github.com/user-attachments/assets/5fd5a859-0513-4551-80d2-3b4533763261" width="250" height="500" />

<h3>Sign Up</h3>
<img src="https://github.com/user-attachments/assets/f581bc45-04a8-47d4-bef2-7c5f37639ddc" width="250" height="500" />

<h3>Country List by Selecting Drop Down Menu</h3>
<img src="https://github.com/user-attachments/assets/7bcfabfa-0745-4b09-9190-8cdd868c1cc2" width="250" height="500" />
