# 🏦 Sihibank Microservices Ecosystem

A production-ready, distributed retail banking core engine built using microservices architecture patterns to ensure strict database isolation, high availability, and decoupled configuration scaling.

---

## 🏗️ System Architecture & Service Registry

The ecosystem is built using the **Database-per-Service** pattern to enforce complete structural data isolation across domains, coordinated by an external configurations layer.

| Service Name | Component Role | Internal Core Port | Database Instance |
| :--- | :--- | :--- | :--- |
| **`configserver`** | Distributed Configuration Engine | `8071` | Shared Native Storage |
| **`accounts`** | Account Management & Ledgers | `8080` | MySQL (`accountsdb`) |
| **`loans`** | Credit and Loan Assignment Processing | `8090` | MySQL (`loansdb`) |
| **`cards`** | Card Tracking & Authorization Hub | `9000` | MySQL (`cardsdb`) |

---

## 🛠️ Technical Stack & Framework Implementation

*   **Core Backend Language:** Java 17 / 21
*   **Framework Foundation:** Spring Boot 4.x & Spring Cloud Config Infrastructure
*   **Data Tier Isolation:** Multi-instance isolated MySQL container orchestration
*   **Containerization Engine:** OCI Container compilation utilizing cloud-native Paketo Buildpacks
*   **Orchestration Architecture:** Local multi-container bridge meshes via Docker Compose

---

## ⚙️ Local Infrastructure Setup & Deployment

### Architectural Dependencies
Ensure you have the following services active on your MacBook Pro:
*   **Docker Desktop** (with allocated RAM for multi-container runtimes)
*   **Maven 3.x**

### Execution Matrix
1. **Clone the Repository:**
   ```bash
   git clone https://github.com
   cd YOUR_REPO_NAME
   ```

2. **Build Local Container Artifacts:**
   Execute compilation across targeted independent modules while bypassing remote infrastructure assets during compile phases:
   ```bash
   SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb" SPRING_DATASOURCE_DRIVER_CLASS_NAME="org.h2.Driver" SPRING_CONFIG_IMPORT="" SPRING_CLOUD_CONFIG_ENABLED=false mvn clean spring-boot:build-image -DskipTests -Dspring-boot.build-image.skipTests
   ```

3. **Orchestrate and Launch Stack Infrastructure:**
   Spin up all isolated relational database instances alongside operational microservice runtime nodes simultaneously:
   ```bash
   docker compose up -d
   ```

---

## 📬 API Integration Verification

Once local runtime health checks complete successfully (`UP`), use standard API test suites (such as Postman or Thunder Client) to hit active endpoint layers:

*   **Accounts Inquiries:** `POST http://localhost:8080/api/create`
*   **Loans Dashboard Processing:** `POST http://localhost:8090/api/loans`

