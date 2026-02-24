# ThreadLine CSMS Backend — Capabilities, Routes, and Production Gap Plan

> Repo: `ThreadLine-csms-api`
>
> Goal of this document: **know exactly what the backend can do today**, and what we **must add** (by role + use case)
> to get to a **production-grade POS / store-management system** quickly without breaking things.

---

## 0) Scope & assumptions

- This doc is generated from the code in `src/main/java/edu/icet` (controllers + `SecurityConfig`).
- Roles used by this system: `OWNER`, `ADMIN`, `CASHIER`.
    - Seeder: `BootstrapDataSeeder` ensures these roles exist.
    - Bootstrap owner is created **only when there are no users**, and gets roles **OWNER + ADMIN**.
- Authentication is JWT-based with refresh tokens persisted in DB.

---

## 1) What’s implemented today (features)

### 1.1 Authentication & session management

Implemented in `AuthController` (`/auth/*`) + JWT filter.

- Login with username/password
- JWT access token generation (cookie-based + bearer-compatible)
- Refresh token rotation (stored in DB)
- Logout (refresh token revoked + cookies cleared)
- Profile endpoints for the currently authenticated user

**Important security behavior**

- The application is configured as **stateless** (`SessionCreationPolicy.STATELESS`).
- A disabled user is blocked (403 `USER_DISABLED`) even if they still have a valid JWT.

### 1.2 User & role administration

Implemented in `UserController` (`/api/admin/users/*`) + `@PreAuthorize`.

- List users (paged)
- Get user by id
- Create user
- Update user roles
- Enable/disable user
- Delete user

### 1.3 Catalog & master data

- Categories CRUD
- Products CRUD
- Product variants: list + filters + CRUD

### 1.4 Inventory

- Inventory CRUD (simple)

### 1.5 Suppliers

- Supplier list/get/add/delete

### 1.6 Sales entities (CRUD)

- Orders CRUD
- Order-items CRUD (nested under order)
- Invoices CRUD
- Payments CRUD (includes filter by payment mode)

---

## 2) Implemented routes (source of truth)

> These are the **current** routes as-coded. Many are **not** under `/api/*` yet.

### 2.1 Auth routes (`/auth/*`)

From `AuthController`:

- `POST /auth/login`
- `POST /auth/refresh`
- `POST /auth/logout`

Profile:

- `GET  /auth/me`
- `PUT  /auth/me`
- `PUT  /auth/me/password`

### 2.2 Admin user management (`/api/admin/users/*`)

From `UserController` (ADMIN-only via method security):

- `GET    /api/admin/users`
- `GET    /api/admin/users/{id}`
- `POST   /api/admin/users`
- `PUT    /api/admin/users/{id}/roles`
- `PATCH  /api/admin/users/{id}/enable`
- `PATCH  /api/admin/users/{id}/disable`
- `DELETE /api/admin/users/{id}`

### 2.3 Categories (`/categories`)

From `CategoryController`:

- `GET    /categories`
- `GET    /categories/{id}`
- `POST   /categories`
- `PUT    /categories/{id}`
- `DELETE /categories/{id}`

### 2.4 Products (`/products`)

From `ProductController`:

- `GET    /products`
- `GET    /products/{id}`
- `POST   /products`
- `PUT    /products/{id}`
- `DELETE /products/{id}`

### 2.5 Product variants (`/productVarients`)

From `ProductVarientController`:

- `GET    /productVarients`
- `GET    /productVarients/{id}`
- `GET    /productVarients/colors/{color}`
- `GET    /productVarients/sizes/{size}`
- `POST   /productVarients`
- `PUT    /productVarients/{id}`
- `DELETE /productVarients/{id}`

### 2.6 Inventories (`/inventories`)

From `InventoryController`:

- `GET    /inventories`
- `POST   /inventories`
- `PUT    /inventories/{id}`
- `DELETE /inventories/{id}`

### 2.7 Suppliers (`/supplier/`)

From `SupplierController`:

- `GET    /supplier/`
- `GET    /supplier/{id}`
- `POST   /supplier/`
- `DELETE /supplier/{id}`

### 2.8 Orders (`/orders`)

From `OrderController`:

- `GET    /orders`
- `GET    /orders/{id}`
- `POST   /orders`
- `PUT    /orders/{id}`
- `DELETE /orders/{id}`

Order-items (`/orders/{orderId}/items`), from `OrderItemController`:

- `GET    /orders/{orderId}/items`
- `POST   /orders/{orderId}/items`
- `PUT    /orders/{orderId}/items/{itemId}`
- `DELETE /orders/{orderId}/items/{itemId}`

### 2.9 Invoices (`/invoices`)

From `InvoiceController`:

- `GET    /invoices`
- `GET    /invoices/{id}`
- `POST   /invoices`
- `PUT    /invoices/{id}`
- `DELETE /invoices/{id}`

### 2.10 Payments (`/payments`)

From `PaymentController`:

- `GET    /payments`
- `GET    /payments/{id}`
- `GET    /payments/mode/{paymentMode}`
- `POST   /payments`
- `PUT    /payments/{id}`
- `DELETE /payments/{id}`

---

## 3) Current authorization behavior (IMPORTANT)

From `SecurityConfig`:

- Public:
    - `POST /auth/login`
    - `POST /auth/refresh`
    - `POST /auth/logout`
    - Swagger/OpenAPI: `/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**`

- Authenticated (any logged-in user):
    - `/auth/me/**`
    - **Any request not matched above** also requires authentication (`anyRequest().authenticated()`).

- Role-based restriction currently applies only to **`/api/**`** writes:
    - `POST /api/**` requires `ROLE_ADMIN`
    - `PUT /api/**` requires `ROLE_ADMIN`
    - `DELETE /api/**` requires `ROLE_ADMIN`

- Method-level security:
    - `/api/admin/users/**` is protected by `@PreAuthorize("hasRole('ADMIN')")`

### What that means in practice

- Most “business” endpoints are **not** under `/api/*` (they’re `/products`, `/orders`, etc.).
- Therefore today:
    - If a user is authenticated, they can call **almost all business write endpoints**, regardless of role.
    - Only `/api/admin/users/**` is strictly ADMIN-only.

---

## 4) Role intent (target production behavior) — permission matrix

This is what a typical cloth store POS needs.

| Area                        | Cashier                          | Admin       | Owner              |
|-----------------------------|----------------------------------|-------------|--------------------|
| Auth, profile               | Self only                        | Self only   | Self only          |
| Product & category browsing | Read                             | Read/Write  | Read               |
| Product/variant master data | No                               | Yes         | Read               |
| Inventory view              | Read (store stock)               | Read/Write  | Read               |
| Stock adjustments / GRN     | No                               | Yes         | Read               |
| Create sale (POS)           | Yes                              | Yes         | Optional           |
| Refund/return               | Yes (with limits/approval rules) | Yes         | Read               |
| Supplier management         | No                               | Yes         | Read               |
| User management             | No                               | Yes         | Yes (or delegated) |
| Reports/dashboard           | Limited (own shifts)             | Operational | Full               |

---

## 5) What’s missing for a production-grade CSMS/POS (gaps)

### 5.1 A single transactional POS workflow

Today you have separate CRUD for orders, invoices, payments, inventory. Production POS typically needs:

- `POST /api/pos/sales` (single transaction)
    - validates stock
    - decrements stock atomically
    - creates order + items
    - generates invoice number
    - records payment(s)
    - returns receipt payload

### 5.2 Inventory must be movement-based (not just CRUD)

Needed concepts:

- Stock movements ledger (SALE, RETURN, GRN/RECEIVE, ADJUSTMENT, TRANSFER)
- Variant-level stock (if not already consistent)
- Concurrency safety (avoid overselling)

### 5.3 Consistent API design

Today responses vary (`void`, DTO, wrapped `ApiResponse`, etc.). Production needs:

- consistent response envelope (or intentionally no envelope)
- validation (`@Valid`) across all write DTOs
- consistent error format
- pagination for list endpoints

### 5.4 RBAC enforcement across business endpoints

Needed:

- Move business endpoints under `/api/*` (or add `/api/v1/*`) and protect properly
- Apply `@PreAuthorize` (or requestMatchers) for CASHIER/ADMIN/OWNER

### 5.5 Returns / refunds / exchanges

Must-have for POS:

- return by invoice
- partial return
- restock rules
- payment reversal / refund record

### 5.6 Pricing, tax, discount model

Needed:

- sell price vs cost price
- tax config (per product or global)
- discounts (line-level + invoice-level)
- promotions/coupons (optional)

### 5.7 Reporting for Owner/Admin

Minimum production reporting endpoints:

- daily/monthly sales totals
- top selling items
- gross profit approximation (requires cost)
- inventory valuation
- cashier performance

### 5.8 Operational hardening

- audit log (who changed what)
- soft delete + status transitions for invoices/orders
- idempotency for create-sale (avoid double charge)
- database constraints + indexes

---

## 6) Backlog as epics → user stories (production fast-track)

### Epic A — Secure the system properly (RBAC + route hygiene)

**Story A1 (Admin):** As an admin, I can manage users and roles securely.

- Status: ✅ implemented for `/api/admin/users/**`.

**Story A2 (Cashier/Admin/Owner):** As a logged-in user, I only see and can call endpoints allowed for my role.

- Status: ❌ missing (business endpoints not role-protected).

**Story A3 (Platform):** All protected endpoints live under `/api/v1/*`.

- Status: ❌ missing (most endpoints are root-level).

### Epic B — POS Sale workflow (must-have)

**Story B1 (Cashier):** Create a sale in one request and receive receipt data.

- Status: ❌ missing.

**Story B2 (System):** Sale decrements stock atomically and prevents oversell.

- Status: ❌ missing (no orchestration endpoint).

**Story B3 (Cashier):** Void/cancel a sale with proper reversal.

- Status: ❌ missing.

### Epic C — Inventory operations

**Story C1 (Admin):** Receive stock from supplier (GRN) and update stock.

- Status: ❌ missing.

**Story C2 (Admin):** Perform stock adjustments with audit trail.

- Status: ❌ missing.

### Epic D — Returns & refunds

**Story D1 (Cashier):** Return items from an invoice and restock correctly.

- Status: ❌ missing.

**Story D2 (Admin):** Approve/reject large refunds.

- Status: ❌ missing.

### Epic E — Reporting & dashboards

**Story E1 (Owner):** View daily/monthly revenue summaries.

- Status: ❌ missing.

**Story E2 (Owner/Admin):** View top products and inventory valuation.

- Status: ❌ missing.

---

## 7) Recommended route layout (migration-friendly)

To avoid breaking existing clients immediately:

- Keep existing routes for now
- Add new canonical routes under `/api/v1/...`
- Gradually migrate frontend/Postman
- Later, deprecate/remove old root routes

Suggested grouping:

- `/api/v1/catalog/categories`
- `/api/v1/catalog/products`
- `/api/v1/catalog/variants`
- `/api/v1/inventory/...`
- `/api/v1/pos/sales`
- `/api/v1/admin/users`
- `/api/v1/owner/reports/...`

---

## 8) Quick Postman collection setup

- Import OpenAPI: `GET http://localhost:8080/v3/api-docs`
- For Authorization header testing:
    - Call `POST /auth/login`
    - Copy `data.accessToken`
    - Use `Authorization: Bearer <token>`

---

## 9) Status summary

- Authentication: ✅
- Bootstrap roles + owner: ✅
- Admin user management: ✅
- Catalog CRUD: ✅
- POS one-step sale flow: ❌
- RBAC across business endpoints: ❌
- Returns/refunds: ❌
- Reports: ❌

