export class Reservation {

    account_number: string;
    total_fare: number;
    booking_fee: number;
    customer_rep_ssn: string;
    airline_id: string;
    flight_number: number;
    leg_number: number;
    passenger_lname: string;
    passenger_fname: string;
    dept_date: string;
    seat_number: string;
    flight_class: string;
    meal: string;
    from_stop_num: number;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
