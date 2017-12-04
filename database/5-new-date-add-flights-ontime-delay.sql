USE express_way;

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-05 09:00:00", actual_arrival_time = "2011-01-05 11:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-05 17:00:00", actual_arrival_time = "2011-01-05 19:00:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-06 07:40:00", actual_arrival_time = "2011-01-06 10:10:00"
WHERE airline_id = "AA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-10 12:30:00", actual_arrival_time = "2011-01-10 14:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "1";

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-10 19:30:00", actual_arrival_time = "2011-01-10 22:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "2";

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-11 05:30:00", actual_arrival_time = "2011-01-11 08:30:00"
WHERE airline_id = "JA" AND flight_number = "111" AND leg_number = "3";

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-13 05:10:00", actual_arrival_time = "2011-01-13 07:10:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "1";

UPDATE express_way.Legs
SET actual_departure_time = "2011-01-13 23:00:00", actual_arrival_time = "2011-01-14 03:00:00"
WHERE airline_id = "AM" AND flight_number = "1337" AND leg_number = "2";

