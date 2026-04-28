DROP DATABASE IF EXISTS MusicStoreDB;
CREATE DATABASE MusicStoreDB;
USE MusicStoreDB;

-- -----------------------------------------------------
-- 1. Create Tables
-- -----------------------------------------------------
CREATE TABLE StoreLocation (
    LocationID INT AUTO_INCREMENT PRIMARY KEY,
    Address VARCHAR(255) NOT NULL,
    City VARCHAR(100) NOT NULL
);

CREATE TABLE EmployeeRole (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL
);

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

CREATE TABLE Customer (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    Email VARCHAR(150),
    PhoneNumber VARCHAR(20)
);

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
    Format VARCHAR(50) NOT NULL, -- THE MISSING COLUMN IS NOW HERE
    FOREIGN KEY (ArtistID) REFERENCES Artist(ArtistID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Inventory (
    InventoryID INT AUTO_INCREMENT PRIMARY KEY,
    LocationID INT NOT NULL,
    AlbumID INT NOT NULL,
    Quantity INT NOT NULL DEFAULT 0,
    FOREIGN KEY (LocationID) REFERENCES StoreLocation(LocationID) ON DELETE CASCADE,
    FOREIGN KEY (AlbumID) REFERENCES Album(AlbumID) ON DELETE CASCADE
);

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

CREATE TABLE TransactionItem (
    TransactionItemID INT AUTO_INCREMENT PRIMARY KEY,
    TransactionID INT NOT NULL,
    AlbumID INT NOT NULL,
    Quantity INT NOT NULL,
    PriceAtSale DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (TransactionID) REFERENCES SalesTransaction(TransactionID) ON DELETE CASCADE,
    FOREIGN KEY (AlbumID) REFERENCES Album(AlbumID)
);

CREATE INDEX idx_artist_name ON Artist(ArtistName);
CREATE INDEX idx_album_title ON Album(AlbumTitle);

-- -----------------------------------------------------
-- 2. Insert Test Data
-- -----------------------------------------------------
INSERT INTO StoreLocation (Address, City) VALUES
('101 Southlake Mall', 'Hobart'),
('500 Griffiss Parkway', 'Rome'),
('123 State Street', 'Chicago');

INSERT INTO EmployeeRole (RoleName) VALUES ('Manager'), ('Cashier'), ('Inventory Specialist');

INSERT INTO Employee (LocationID, RoleID, Username, PasswordHash, FirstName, LastName) VALUES
(1, 1, 'gclark_admin', 'hash892374', 'Goldie', 'Clark'),
(1, 2, 'zane_c', 'hash129384', 'Zane', 'Smith'),
(2, 1, 'dcortes', 'hash564738', 'Dylan', 'Cortes'),
(3, 3, 'stock_john', 'hash000000', 'John', 'Doe');

INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber) VALUES
('Alice', 'Johnson', 'alice.j@email.com', '555-0101'),
('Bob', 'Williams', 'bwilliams99@email.com', '555-0102');

INSERT INTO Artist (ArtistName) VALUES
('Pink Floyd'), ('The Beatles'), ('Taylor Swift'), ('Kendrick Lamar'), ('Fleetwood Mac'), ('Daft Punk'), ('Radiohead');

INSERT INTO Album (ArtistID, AlbumTitle, Genre, ReleaseYear, Price, Format) VALUES
(1, 'The Dark Side of the Moon', 'Rock', 1973, 29.99, 'Vinyl'),
(1, 'Wish You Were Here', 'Rock', 1975, 14.99, 'CD'),
(2, 'Abbey Road', 'Rock', 1969, 35.00, 'Vinyl'),
(3, '1989', 'Pop', 2014, 12.99, 'CD'),
(3, 'Midnights', 'Pop', 2022, 32.99, 'Vinyl'),
(4, 'DAMN.', 'Hip-Hop', 2017, 15.99, 'CD'),
(5, 'Rumours', 'Rock', 1977, 28.50, 'Vinyl'),
(6, 'Discovery', 'Electronic', 2001, 19.99, 'CD'),
(7, 'OK Computer', 'Alternative', 1997, 25.00, 'Vinyl');

-- Hobart Store Inventory
INSERT INTO Inventory (LocationID, AlbumID, Quantity) VALUES
(1, 1, 10), (1, 3, 5), (1, 4, 20), (1, 6, 8);

-- Rome Store Inventory
INSERT INTO Inventory (LocationID, AlbumID, Quantity) VALUES
(2, 2, 15), (2, 5, 12), (2, 7, 6);