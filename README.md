
## Introduction

This is an android application that allows IIITA Students to post what they lost or found, this app requires you to register an account first and then login using that account to access the rest of the app. The main screen acts as an introduction to app and conveys the applications purpose. The Lost/Found Section displays the Item description and message provided by the users in a recycler view for everyone to see. This application is still under development as the deadline was short. I plan to add features like displaying the image uploaded by the user in an image view besides the RecyclerView TextView's, useage of the register details entered by the user like First and Last Name, Custom Profiles and Send Notification Button. Initially I was making a shopping e-commerce app but after the GeekHaven Task I switched to making a Lost and Found App.
## Technologies

* Kotlin
* XML
* Firebase Authentication
* Firestore
* Firebase Storage
* Glide Library

## Activities

* **SplashActivity** : This Activity is the first one to be displayed when the app is started. It displays "IIITA Lost and Found" text  and fades into the LoginActivity after 3 seconds.
* **LoginActivity** : Activity for the user to enter a registered email and password and go to the MainActivity.
* **RegisterActivity** : Activity for the user to create an account by entering First/Last name, email and password. The user data is stored in Firestore.
* **ForgotPasswordActivity** : Activity for the user to enter their email and get the option to change their password.
* **MainActivity** : Activity that tells the user about the purpose of the app.
* **LostActivity** : Activity that displays the details of the items in a RecyclerView.
* **AddProductActivity** : Activity that allows the user to upload a lost/found item.
* **BaseActivity** : Contains some methods that alot of classes can use like ErrorSnackBar etc.

## Wireframe
![Wireframe-1](https://user-images.githubusercontent.com/97402494/189099046-d52138c7-4d88-40b3-86da-519f5481c04a.jpg)

## References
* GeeksForGeeks Articles
* Denis Panjuta's Firebase Udemy course.
* Lastly but most importantly stackoverflow
