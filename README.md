HamsterPOS E-Commerce
Welcome to HamsterPOS — a full-stack e-commerce platform built with Spring Boot on the backend and Next.js (with TypeScript) on the frontend. 
It features a clean admin dashboard, role-based access control, drag-and-drop product management, and a seamless shopping experience for users.

 Getting Started
🧱 Backend (Spring Boot)
Navigate to the backend folder:

cd ecommerce
Start the server:

You can run it from your IDE (like IntelliJ or VS Code)

Or via terminal:

./mvnw spring-boot:run
The backend will be up and running at:
👉 http://localhost:8080

Spring Security login page: /req/login

Admin username: abed | password: test

Users username: abed2 | password: test2

New users can register at /req/signup

 Frontend (Next.js)
Go to the frontend directory:


cd frontend-ecommerce
Install the dependencies:

npm install
Start the development server:

npm run dev
You can now view the frontend at:
👉 http://localhost:3000

🔐 Key Features:
 1. User registration and login

 2. Role-based access (Admin vs User)

 3. Product browsing with shopping cart

 4. Order placement system

 5. Admin panel to manage products & categories (CRUD)

 6. Drag-and-drop reordering for products and categories

![image](https://github.com/user-attachments/assets/53791efd-a52e-4cca-91e0-a9d4409af1c0)
![image](https://github.com/user-attachments/assets/0724a831-8a84-419d-a28a-217cc2f534f3)
![image](https://github.com/user-attachments/assets/14508ae9-efab-4b35-9df2-2816933198d6)
![image](https://github.com/user-attachments/assets/6dfdbc3f-65bc-4e3c-b8a2-ff122a8339f6)
![image](https://github.com/user-attachments/assets/dd5e4e31-f564-4627-a59e-9f31ae008dd9)


🧰 Tech Stack
Frontend: Next.js, React, TypeScript, Tailwind CSS, Material UI

Backend: Spring Boot, Spring Security, Java, Maven

Database: (e.g. H2, MySQL, or PostgreSQL — depending on your config)

