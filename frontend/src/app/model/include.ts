export class Include {

    reservationNumber: string;
    airlineId: string;
    flightNumber: number;
    legNumber: string;
    lastName: string;
    firstName: string;
    deptDate: string;
    seatNumber: number;
    flightClass: string;
    meal: string;
    fromStop: number;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
    
}