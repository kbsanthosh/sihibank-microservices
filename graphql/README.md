# SihiBank GraphQL Gateway Service

This microservice acts as an asynchronous proxy layer (BFF) that aggregates downstream account REST APIs and compliance metrics from MongoDB into a single type-safe GraphQL endpoint.

## 🛠️ Tech Stack
- **Runtime:** Java 26 / Spring Boot 4.1.0 (WebFlux / Netty)
- **Engine:** Spring for GraphQL
- **Database:** MongoDB (Reactive)

## 🚀 Local Development Setup

### 1. Prerequisites
Ensure your local Docker Desktop application is running, then spin up the infrastructure container:
```bash
docker run -d --name sihibank-mongo -p 27017:27017 -v sihibank_mongo_data:/data/db -e MONGO_INITDB_ROOT_USERNAME=sihibank_admin -e MONGO_INITDB_ROOT_PASSWORD=password123 mongo:latest
```

### 2. Network Layout
- **Service Port:** `8085`
- **GraphiQL Sandbox:** http://localhost:8085/graphiql
- **GraphQL Endpoint:** `POST http://localhost:8085/graphql`

## 🔍 Sample Testing Query
```graphql
query GetBlendedCustomerDetails($mobile: String!) {
    fetchCustomerSummary(mobileNumber: $mobile) {
        customer {
            fullName
            cellNumber
            emailId
            accounts {
                number
                type
            }
            cards {
                number
                type
            }
            loans {
                number
                type
            }
            kycStatus   # Dynamically read from your Docker Mongo Database!
            riskScore   # Dynamically read from your Docker Mongo Database!
        }
    }
}
```
```variables
{
  "mobile": "9876543255"
}
```
```input-sample-to-be-prepared
http://localhost:8072/sihibank/accounts/api/fetchCustomerDetails?mobileNumber=9876543255
```
```output-sample
{
"data": {
"fetchCustomerSummary": {
"customer": {
"fullName": "Anamika Gowda",
"cellNumber": "9876543255",
"emailId": "anamika@sihibank.com",
"accounts": {
"number": "1074870210",
"type": "Savings"
},
"cards": {
"number": "100523904420",
"type": "Credit Card"
},
"loans": {
"number": "100212754491",
"type": "Home Loan"
},
"kycStatus": "NOT_FOUND",
"riskScore": 0
}
}
}
}
```