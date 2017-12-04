export class Flight {

    flightNumber: string;
    fromAirport: string;
    toAirport: string;
    fare: number;
    class: string;
    stops: number;
    airlineId: string;
    legNumber: number;
    depatureTime: string;
    arriveTime: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
