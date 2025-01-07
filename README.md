## ViewNAdd App
 The app consists of two primary screens: a Product Listing Screen and an Add Product Screen. It adheres to the latest Android development best practices and incorporates modern technologies.

---

## Features

### **Product Listing Screen**
- Displays a list of products fetched from the API.
- Users can:
  - Search for products.
  - View all products.
  - Navigate to the Add Product screen via a floating action button (FAB).
- Loads product images from URLs using **Coil**; a default image is shown if the URL is empty.
- Progress bar to indicate loading progress.
- **Error Handling**: Displays a clear error message or badge in case of no internet connection or server issues.

### **Add Product Screen**
- Built as a separate screen for a clean and focused UI.
- Allows users to:
  - Select product type from a dropdown list.
  - Enter product name, selling price, and tax rate with input validation.
  - Optionally select images in JPEG/PNG format with a 1:1 ratio.
  - can view the selected image, option to **remove the image** and select a new one if needed.
- Submit product details to the API via a **POST** request.
- Shows progress indication while uploading.
- Provides clear feedback with dialogs and toast upon successful or failed submission.

---

## Utilized
- **MVVM Architecture**: Ensures separation of concerns and scalability.
- **Jetpack Compose**: Simplifies UI development and delivers a modern, responsive interface.
- **Navigation Component (Compose Navigation)**: For seamless and intuitive navigation between screens.
- **Retrofit**: Simplifies REST API integration.
- **Coil**: For efficient image loading.
- **Dagger Hilt**: Simplifies dependency injection.
- **Kotlin Coroutines and Flows**: Ensures smooth and reactive data handling.
- **Sealed Classes**: For managing product states in a structured manner.
- **Splash API**: Implemented a professional splash screen experience.

---

## How to Build and Run the App

Follow these steps to build and run the **ViewNAdd App**:

Step 1: Clone the Repository
git clone https://github.com/narendra1022/ViewNAdd.git


Step 2: Open in Android Studio
Open the project in Android Studio (latest stable version recommended).

Step 3: Sync Gradle
Ensure all dependencies are synced.

Step 4: Run the App
Use the Run button in Android Studio to build and install the app on an emulator or device.

Step 5: Explore the App
View the product list on the Product Listing Screen.
Add a new product via the Add Product Screen.
