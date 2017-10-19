# INSERT INTO Employee Table
INSERT INTO express_way.Employee (ssn, id, is_manager, start_date, hourly_rate, telephone) VALUES
  ('128484888', '3', FALSE, CURRENT_TIMESTAMP, '30', '5964949999');

# Update Employee Table
UPDATE express_way.Employee
SET hourly_rate = '28'
WHERE id = '3';

#Delete Employee Data
DELETE FROM express_way.Employee
WHERE id = '3';

#Total Fare
SELECT SUM(total_fare)
FROM Reservations
WHERE reservation_date BETWEEN '2011/01/01' AND '2011/01/31';

#Total Booking
SELECT SUM(booking_fee)
FROM Reservations
WHERE reservation_date BETWEEN '2011/01/01' AND '2011/01/31';

############################################




#GET Customer Name
CREATE VIEW full_name AS
  SELECT
    concat(first_name, ' ', last_name),
    id
  FROM Person;

#Get Account ID from Customer Name
# SELECT DISTINCT (account_number)
# FROM full_name, Customer
# WHERE full_name.`concat(first_name, ' ' , last_name)` =  AND full_name.id = Customer.id;

# Get Reservation From Name
SELECT *
FROM Reservations R
WHERE account_number IN
      (SELECT DISTINCT (account_number)
       FROM full_name, Customer
       WHERE full_name.`concat(first_name, ' ' , last_name)` = 'Jane Smith' AND full_name.id = Customer.id)

# Get Reservation From Flights
SELECT *
FROM Reservations
WHERE reservation_number IN
      (SELECT I.reservation_number
       FROM Include I
       WHERE I.flight_number = '111');






#Most Active Flight
SELECT *
FROM Flight
ORDER BY date_of_week DESC;

SELECT * FROM Airport



