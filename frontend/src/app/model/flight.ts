 export class Flight {
    flight_number : string;
    fare: number;
    class: string;
    stops: number;
    airline: string;

    constructor(values: Object={}){
        Object.assign(this, values);
    }
}