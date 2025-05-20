# 📚 Bookstore Inventory Management App

A mobile inventory management application for bookstore owners, built using **Kotlin**, **Jetpack Compose**, and **Room** for local database storage. This app allows users to manage inventory efficiently with full offline support and intuitive UI.

---

## 🚀 Features

- 📦 **Inventory List**  
  View a dynamic list of all inventory items on the home screen.

- ➕ **Add/Edit Item**  
  Add new items or update existing ones with fields:
  - Item ID
  - Name
  - Category
  - Quantity
  - Price

- 🔍 **Item Details**  
  View full details of a selected inventory item.

- 🗑️ **Delete Items**  
  Easily remove items from your inventory with confirmation prompts.

- 🔎 **Search Functionality** (Optional)  
  Filter inventory by item name or ID for quick access.

- ✅ **Data Validation**  
  Prevent incorrect input with real-time validation checks.

- 📡 **Offline Access**  
  Data is stored locally using Room, making it accessible even without internet.

- 📢 **User Notifications**  
  Toast messages for success and failure events on data operations.

---

## 🛠 Tech Stack

| Technology     | Purpose                        |
|----------------|--------------------------------|
| Kotlin         | Programming language           |
| Android Studio | Development environment        |
| Jetpack Compose| Declarative UI framework       |
| Room Library   | Local database (SQLite wrapper)|

---

## 📱 Screenshots 
![WhatsApp Image 2025-04-20 at 21 40 52 (2)](https://github.com/user-attachments/assets/85247f25-0bc7-4aa5-89bc-2c1ee7a92c33)
![WhatsApp Image 2025-04-20 at 21 40 52](https://github.com/user-attachments/assets/8695fa1a-663f-455d-8974-524ffab1445b)
![WhatsApp Image 2025-04-20 at 21 40 52 (1)](https://github.com/user-attachments/assets/90c48c43-420f-45e2-82b1-ab4a101e576a)
![WhatsApp Image 2025-04-20 at 21 40 51](https://github.com/user-attachments/assets/af57a8ff-d366-4485-be9f-4dd294a8113a)





- Home Screen (Item List)
- Add/Edit Item Form
- Item Details Screen
- Search Feature (if implemented)

---





## 🗃️ Database Structure

### `InventoryItem` Entity
```kotlin
@Entity(tableName = "inventory")
data class InventoryItem(
    @PrimaryKey val id: Int,
    val name: String,
    val category: String,
    val quantity: Int,
    val price: Double
)
