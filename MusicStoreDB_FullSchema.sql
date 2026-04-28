DROP DATABASE IF EXISTS MusicStoreDB;
CREATE DATABASE MusicStoreDB;
USE MusicStoreDB;

-- -----------------------------------------------------
-- 1. Locations & Personnel
-- -----------------------------------------------------
-- Manages the multiple store locations
CREATE TABLE StoreLocation (
    LocationID INT AUTO_INCREMENT PRIMARY KEY,
    Address VARCHAR(255) NOT NULL,
    City VARCHAR(100) NOT NULL
);

-- Handles roles for the employee login system
CREATE TABLE EmployeeRole (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL
);

-- Manages employee data and authentication
CREATE TABLE Employee (
    EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
    LocationID INT,
    RoleID INT,
    Username VARCHAR(50) UNIQUE NOT NULL,
    PasswordHash VARCHAR(255) NOT NULL,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    FOREIGN KEY (LocationID) REFERENCES StoreLocation(LocationID) ON DELETE SET NULL,
    FOREIGN KEY (RoleID) REFERENCES EmployeeRole(RoleID)
);

-- -----------------------------------------------------
-- 2. Customer Management
-- -----------------------------------------------------
-- Stores customer data for receipts and outreach
CREATE TABLE Customer (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    Email VARCHAR(150),
    PhoneNumber VARCHAR(20)
);

-- -----------------------------------------------------
-- 3. Inventory System (Your Original Tables + Upgrades)
-- -----------------------------------------------------
CREATE TABLE Artist (
    ArtistID INT AUTO_INCREMENT PRIMARY KEY,
    ArtistName VARCHAR(100) NOT NULL
);

CREATE TABLE Album (
    AlbumID INT AUTO_INCREMENT PRIMARY KEY,
    ArtistID INT NOT NULL,
    AlbumTitle VARCHAR(150) NOT NULL,
    Genre VARCHAR(50),
    ReleaseYear INT,
    Price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (ArtistID) REFERENCES Artist(ArtistID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Maps the stock of albums to specific store locations
CREATE TABLE Inventory (
    InventoryID INT AUTO_INCREMENT PRIMARY KEY,
    LocationID INT NOT NULL,
    AlbumID INT NOT NULL,
    Quantity INT NOT NULL DEFAULT 0,
    FOREIGN KEY (LocationID) REFERENCES StoreLocation(LocationID) ON DELETE CASCADE,
    FOREIGN KEY (AlbumID) REFERENCES Album(AlbumID) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- 4. Sales & Transactions
-- -----------------------------------------------------
-- Generates the base receipt/transaction record
CREATE TABLE SalesTransaction (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    EmployeeID INT NOT NULL,
    LocationID INT NOT NULL,
    TransactionDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE SET NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
    FOREIGN KEY (LocationID) REFERENCES StoreLocation(LocationID)
);

-- Maps the specific items (the "cart") to a transaction
CREATE TABLE TransactionItem (
    TransactionItemID INT AUTO_INCREMENT PRIMARY KEY,
    TransactionID INT NOT NULL,
    AlbumID INT NOT NULL,
    Quantity INT NOT NULL,
    PriceAtSale DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (TransactionID) REFERENCES SalesTransaction(TransactionID) ON DELETE CASCADE,
    FOREIGN KEY (AlbumID) REFERENCES Album(AlbumID)
);

-- -----------------------------------------------------
-- Indexes for Optimization
-- -----------------------------------------------------
CREATE INDEX idx_artist_name ON Artist(ArtistName);
CREATE INDEX idx_album_title ON Album(AlbumTitle);