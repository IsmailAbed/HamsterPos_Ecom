HamsterPOS E-Commerce
Welcome to HamsterPOS — a full-stack e-commerce platform built with Spring Boot on the backend and Next.js (with TypeScript) on the frontend.
It features:
•	A clean admin dashboard
•	Role-based access control
•	Drag-and-drop product management
•	A seamless shopping experience for users
•	Backend authentication/authorization (via Spring Security)
•	SSR in frontend
________________________________________
Getting Started
🔧 Backend (Spring Boot)
1.	Navigate to the backend folder:

cd ecommerce
________________________________________

2.	Start the server:
o	Option 1: Use your IDE (IntelliJ or VS Code)
o	Option 2: Run from terminal:

./mvnw spring-boot:run
________________________________________

3.	The backend will be running at:
👉 http://localhost:8080 (you will redirect to http://localhost:8080/req/login) automatically
________________________________________

4.	Authentication routes:

o	 Login page: /req/login

o	 Admin login:
username: abed | password: test

o	 User login:
username: abed2 | password: test2

o	 New users can register at: /req/signup
________________________________________
💻 Frontend (Next.js)
1.	Navigate to the frontend folder:

cd frontend-ecommerce
________________________________________

2.	Install dependencies:

npm install
________________________________________

3.	Start the development server:

npm run dev
________________________________________

4.	The frontend will be available at:
👉 http://localhost:3000
________________________________________


🔐 Key Features:
 1. User registration and login

 2. Role-based access (Admin vs User)

 3. Product browsing with shopping cart

 4. Order placement system

 5. Admin panel to manage products & categories (CRUD)

 6. Drag-and-drop reordering for products and categories

![image](https://github.com/user-attachments/assets/53791efd-a52e-4cca-91e0-a9d4409af1c0)
![image](https://github.com/user-attachments/assets/0724a831-8a84-419d-a28a-217cc2f534f3)
![image](https://github.com/user-attachments/assets/6873b2c1-c23c-4528-9b39-9ac04ed00dcb)
![image](https://github.com/user-attachments/assets/6dfdbc3f-65bc-4e3c-b8a2-ff122a8339f6)
![image](https://github.com/user-attachments/assets/dd5e4e31-f564-4627-a59e-9f31ae008dd9)


🧰 Tech Stack
Frontend: Next.js, React, TypeScript, Tailwind CSS, Material UI

Backend: Spring Boot, Spring Security, Java, Maven

Database: (e.g. H2, MySQL, or PostgreSQL — depending on your config) (I used PostgreSQL)

