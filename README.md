🎟️ Tickeflow (Event Management & Ticketing System) 

A robust Spring Boot application for managing events and ticket bookings — equipped with PDF ticket generation and email notifications.

✨ Features
* ✅ Event Management: Create, list, and search events by title or location
* 🧑‍💻 User Registration: Register users for event participation
* 🎟️ Ticket Booking: Book tickets and auto-generate PDF confirmations
* ❌ Ticket Cancellation: Cancel tickets and auto-update capacity with email notifications
* 📩 Email Integration: Ticket confirmation and cancellation sent via email
* 📄 PDF Ticket Generation: Stylish ticket with event and user details
* 👥 View Event Attendees: See users registered for specific or all events

🧠 Tech Stack
* Java 17+
* Spring Boot 3.x
* Spring Data JPA (H2 DB)
* Jakarta Mail (Spring Mail)
* iText (PDF generation)
* H2 In-Memory DB
* Maven

🚀 API Endpoints

🎉 Event APIs

Method	 Endpoint	 Description

GET	/api/events	"List all events"

GET	/api/events/search	"Search by title or location"

POST	/api/events	 "Create a new event"

GET	/api/events/{id}/users	 "View users registered for a specific event"

👤 User APIs

Method	 Endpoint	 Description

POST	/api/users	"Register new user"

🎟️ Ticket Booking APIs

Method	 Endpoint	  Description

POST	/api/tickets	"Book ticket (Params: userId, eventId) Returns ticket ID + email PDF"

DELETE	/api/tickets/{id}	 "Cancel ticket by ticket ID Updates event capacity + sends email"

📩 Email & PDF Ticket

* Tickets are emailed as PDF with:
    * Event Name
    * User Email
    * Booking Date
    * Unique Ticket ID
    * 🎫 Emoji-styled Header
* Cancellation also triggers an email with cancellation notice.

🛠️ Setup Instructions
1. Clone the repository git clone https://github.com/your-repo/event-mvp.git
2. cd event-mvp
3. Configure SMTP settings in application.properties spring.mail.host=smtp.gmail.com
4. spring.mail.port=587
5. spring.mail.username=your_email@gmail.com
6. spring.mail.password=your_app_password
7. spring.mail.properties.mail.smtp.auth=true
8. spring.mail.properties.mail.smtp.starttls.enable=true
9. Run the app ./mvnw spring-boot:run
10. Access H2 Console (for debugging/testing)
    * URL: http://localhost:8080/h2-console
    * JDBC URL: jdbc:h2:mem:testdb

📦 Maven Dependencies
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
  </dependency>
  <dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
  </dependency>
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
  </dependency>
</dependencies>

🧪 Testing Tips
* Use Postman to test all endpoints
* Verify mail and PDF delivery in your inbox
* Cancel tickets and check updated capacity in /api/events
* Use /api/events/{id}/users to view registered users for that event

📌 Future Enhancements
* 🗂️ Event categories and filters
* 🌐 Frontend client (React/Vue)
* 🔐 Login + JWT Security
* 🧾 QR Code in PDF Ticket
* 📊 Admin Dashboard
* 💳 PaymentGateway

👨‍💻 Author
Developed with ❤️ by Arindam Chetia

Feel free to contribute, fork, and improve this project!


