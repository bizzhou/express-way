export class Auction {

    accountNumber: string;
    reservationNumber: string;
    airlineId: string;
    flightNumber: number;
    legNumber: number;
    flightClass: string;
    depatureDate: string;
    bidPrice: number;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

}