# 📂 Custom File DBMS (FileDBMS)

## 📝 Project Overview
**Custom File DBMS (FileDBMS)** is a **file-based Database Management System** built in Java.  
It provides **SQL-like operations** for managing records using **Java Collections** and **Serialization** instead of a traditional database.  

The system demonstrates how data can be **stored, retrieved, updated, and backed up** directly via files, making it lightweight and portable.

---

## ⚙️ Technology Used
- Java (Core)  
- Java Collections (LinkedList)  
- Serialization / Deserialization (File I/O)  
- Console-based user interface  

---

## 🚀 Features

### 🔹 Basic Operations
- **Insert new records**  
- **Select all records**  
- **Select by ID**  
- **Select by Name**  
- **Delete records by ID**

### 🔹 Advanced Operations
- **Update salary (or any field) by ID**  
- **Sort records by salary**  
- **Search records within an age range**  

### 🔹 Aggregate Functions
- **Count total records**  
- **Find Maximum salary**  
- **Find Minimum salary**  
- **Calculate Average salary**

### 🔹 Backup & Restore
- **Take backup** of the database (`MarvellousDBMS.ser`)  
- **Restore database** from backup on program start  

### 🔹 Export
- Export all records to a **CSV file** for Excel or external use  

