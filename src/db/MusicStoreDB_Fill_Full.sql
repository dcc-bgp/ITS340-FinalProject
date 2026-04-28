-- MusicStoreDB_Fill_Full.sql
USE MusicStoreDB;

-- -----------------------------------------------------
-- 1. Store Locations
-- -----------------------------------------------------
INSERT INTO StoreLocation (Address, City) VALUES
('101 Southlake Mall', 'Hobart'),
('500 Griffiss Parkway', 'Rome'),
('123 State Street', 'Chicago');

-- -----------------------------------------------------
-- 2. Employee Roles & Employees
-- -----------------------------------------------------
INSERT INTO EmployeeRole (RoleName) VALUES
('Manager'),
('Cashier'),
('Inventory Specialist');

INSERT INTO Employee (LocationID, RoleID, Username, PasswordHash, FirstName, LastName) VALUES
(1, 1, 'gclark', 'hash892374', 'Goldie', 'Clark'),
(2, 1, 'dcortes', 'hash564738', 'Dylan', 'Cortes');

-- -----------------------------------------------------
-- 3. Customers
-- -----------------------------------------------------
INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber) VALUES
('Alice', 'Johnson', 'alice.j@email.com', '555-0101'),
('Bob', 'Williams', 'bwilliams99@email.com', '555-0102'),
('Charlie', 'Brown', 'cbrown@email.com', '555-0103');

-- -----------------------------------------------------
-- 4. Artists
-- -----------------------------------------------------
INSERT INTO Artist (ArtistName) VALUES
('Pink Floyd'),
('The Beatles'),
('Taylor Swift'),
('Kendrick Lamar'),
('Fleetwood Mac'),
('Daft Punk'),
('Radiohead');

-- -----------------------------------------------------
-- 5. Albums (Now includes 'Price' and 'Format' for the Factory)
-- -----------------------------------------------------
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

-- -----------------------------------------------------
-- 6. Inventory (Linking Albums to Locations with quantities)
-- -----------------------------------------------------
-- Hobart Store Inventory (Location 1)
INSERT INTO Inventory (LocationID, AlbumID, Quantity) VALUES
(1, 1, 10), -- 10 copies of Dark Side (Vinyl)
(1, 3, 5),  -- 5 copies of Abbey Road (Vinyl)
(1, 4, 20), -- 20 copies of 1989 (CD)
(1, 6, 8);  -- 8 copies of DAMN. (CD)

-- Rome Store Inventory (Location 2)
INSERT INTO Inventory (LocationID, AlbumID, Quantity) VALUES
(2, 2, 15), -- 15 copies of Wish You Were Here (CD)
(2, 5, 12), -- 12 copies of Midnights (Vinyl)
(2, 7, 6);  -- 6 copies of Rumours (Vinyl)

-- -----------------------------------------------------
-- 7. Sales Transactions & Items (Simulating a completed checkout)
-- -----------------------------------------------------
-- Transaction 1: Alice buys a Taylor Swift Vinyl from Goldie at the Hobart store
INSERT INTO SalesTransaction (CustomerID, EmployeeID, LocationID) VALUES
(1, 1, 1);

-- Get the last inserted TransactionID (which is 1) and add the items
INSERT INTO TransactionItem (TransactionID, AlbumID, Quantity, PriceAtSale) VALUES
(1, 5, 1, 32.99);

-- Transaction 2: Bob buys Pink Floyd and The Beatles from Dylan at the Rome store
INSERT INTO SalesTransaction (CustomerID, EmployeeID, LocationID) VALUES
(2, 3, 2);

INSERT INTO TransactionItem (TransactionID, AlbumID, Quantity, PriceAtSale) VALUES
(2, 1, 1, 29.99),
(2, 3, 1, 35.00);