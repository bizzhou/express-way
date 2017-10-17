USE express_way;

INSERT INTO `Airline` (airline_id, airline_name)
VALUES ('AB', 'Air Berlin'),
				('AJ', 'Air Japan'),
                ('AM', 'Air Madagascar'),
                ('AA', 'American Airlines'),
                ('BA', 'British Airways'),
                ('DA', 'Delta Airlines'),
                ('JA', 'JetBlue Airways'),
                ('LA', 'Lufthansa'),
                ('SA', 'Southwest Airlines'),
                ('UA', 'United Airlines');

INSERT INTO `Airport` (airport_id, airport_name, city, country)
VALUES ('TXL', 'Berlin Tegel', 'Berlin' , 'Germany'),
			   ('ORD',"Chicago O'Hare International", 'Chicago' , 'Illinois'),
               ('ATL','Hartsfield-Jackson Atlanta Int', 'Atlanta' , 'United States of America'),
               ('TNR','Ivato International', 'Antananarivo' , 'Madagascar'),
               ('JFK','John F. Kennedy International', 'New York' , 'United States of America'),
               ('LGA','LaGuardia', 'New York' , 'United States of America'),
               ('BOS','Logan International', 'Boston' , 'United States of America'),
               ('LHR','London Heathrow', 'London' , 'United Kingdom'),
               ('LAX', 'Los Angeles International', 'Los Angeles' , 'United States of America'),
               ('SFO', 'San Francisco International', 'San Francisco' , 'United States of America'),
               ('NRT', 'Tokyo International', 'Tokyo' , 'Japan');
               
INSERT INTO `Flight` (flight_number, date_of_week, seating_capacity, airline)
VALUES ('111' , '1010100' , '100' , 'AA'),
			   ('111' , '1111111' , '150' , 'JA'),
               ('1337' , '0000011' , '33' , 'AM');
               
INSERT INTO `Legs` (arrival_date, leg_number, departure_date, to_airport, flight_number, airline_id)
VALUES ('2011-01-05 09:00:00' , '1' , '2011-01-05 11:00:00' , 'LaGuardia' , '111', 'American Airlines' ),
			   ('2011-01-05 17:00:00' , '2' , '2011-01-05 19:00:00' , 'Los Angeles International' , '111', 'American Airlines' ),
               ('2011-01-06 07:30:00' , '3' , '2011-01-06 10:00:00' , 'Tokyo International' , '111', 'American Airlines' ),
               ('2011-01-10 12:00:00' , '1' , '2011-01-10 14:00:00' , 'San Francisco International' , '111', 'JetBlue Airways' ),
               ('2011-01-10 19:30:00' , '2' , '2011-01-10 22:30:00' , 'Logan International' , '111', 'JetBlue Airways' ),
               ('2011-01-11 05:00:00' , '3' , '2011-01-11 08:00:00' , 'London Heathrow' , '111', 'JetBlue Airways' ),
               ('2011-01-13 05:00:00' , '1' , '2011-01-13 07:00:00' , 'John F. Kennedy International' , '1337', 'Air Madagascar' ),
               ('2011-01-13 23:00:00' , '2' , '2011-01-14 03:00:00' , 'Ivato International' , '1337', 'Air Madagascar' );
               
INSERT INTO `Passengers` (first_name, last_name, address, telephone, email)
VALUES ('Jane',' Smith', '100 Nicolls Rd, Stony Brook, New York 17790', '555-555-5555', 'awesomejane@ftw.com'),
			   ('John',' Doe', '123 N Fake Street, New York, New York 10001', '123-123-1234', 'jdoe@woot.com'),
               ('Rick',' Astley', '1337 Internet Lane, Los Angeles, California 90001', '314-159-2653', 'rickroller@rolld.com');
               
INSERT INTO `Reservations` (reservation_number, total_fare)
VALUES ('111', '1200'),
			   ('222', '500'),
               ('333', '3333.33');
               
INSERT INTO `Include` (dept_date, seat_number, meal, class, leg_number, from_stop_num,
													   reservation_number, flight_number, passenger_lname, passenger_fname, airline_id)
VALUES ('2011-01-05 11:00:00', '33F', 'Chips', 'Economy', '1', '1', '111', '111', 'John', 'Doe',  'AA'),
			   ('2011-01-05 19:00:00', '33F', 'Chips', 'Economy', '2', '2', '111', '111', 'John', 'Doe', 'AA'),
               ('2011-01-14 03:00:00', '13A', 'Fish and Chips', 'First', '1', '2', '222', '111', 'Jane', 'Smith',  'JA'),
               ('2011-01-13 07:00:00', '1A', 'Sushi', 'First', '1', '1', '333', '1337', 'Rick', 'Astley', 'AM');

INSERT INTO `Auctions` (reservation_number, leg_number, NYOP, is_accepted)
VALUES ('111', '1', '400', '1');

