UPDATE express_way.Legs
SET departure_time = "2018-01-05 09:00:00", arrival_time = "2018-01-05 11:00:00", actual_departure_time = NULL, actual_arrival_time = NULL
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET departure_time = "2018-01-05 17:00:00", arrival_time = "2018-01-05 19:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET departure_time = "2018-01-06 07:30:00", arrival_time = "2018-01-06 10:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET departure_time = "2018-01-10 12:00:00", arrival_time = "2018-01-10 14:00:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET departure_time = "2018-01-10 19:30:00", arrival_time = "2018-01-10 22:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET departure_time = "2018-01-11 05:00:00", arrival_time = "2018-01-11 08:00:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET departure_time = "2018-01-13 05:00:00", arrival_time = "2018-01-13 07:00:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "1";

UPDATE express_way.Legs
SET departure_time = "2018-01-13 23:00:00", arrival_time = "2018-01-14 03:00:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "2";


SELECT * FROM express_way.Legs;
