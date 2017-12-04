export class Reservation {

    accountNumber: string;
    totalFare: number;
    bookingFare: number;
    
    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
