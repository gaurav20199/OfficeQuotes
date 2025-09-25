# 🖋️ OfficeQuotes

A fun and interactive application centered around **quotes from *The Office***. Relive the iconic moments of the show through quizzes, leaderboards, and a curated quote library.

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot  
- **Frontend:** Thymeleaf  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **Security:** Spring Security (user authentication & email verification)  

---

## 📁 Project Structure & Design

The project is organized to ensure clarity, modularity, and maintainability:  

- **Controller** → Handles HTTP requests and user interactions  
- **Service** → Contains the core business logic for quizzes, scores, and user management  
- **Repository** → Manages data persistence for quotes, users, and scores  

This design allows easy extension for new features, quizzes, or additional quotes.

---

## 🚀 Key Features

- 📝 **Curated Quote Library** – A handpicked collection of iconic *The Office* quotes for fans  
- 🎮 **Interactive Quiz Game** – Test your knowledge and relive the nostalgia with fun quizzes  
- 🏆 **Leaderboard** – Track top scorers and compete with other users for the highest score  
- 👤 **Complete User Management** – Seamless flow from **Sign Up → Email Verification → Account Activation → Login**, ensuring a secure and smooth user experience  

---

## ⚙️ Setup & Installation

### Prerequisites
- Java **17** or higher  
- Maven **3.8.1** or higher  
- MySQL database  

### Steps
```bash
# 1) Clone the repository
git clone https://github.com/gaurav20199/OfficeQuotes.git
cd OfficeQuotes

# 2) Build the application
mvn clean install

# 3) Run the application
mvn spring-boot:run
