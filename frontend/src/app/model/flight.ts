 export class Flight {
    flight_number: string;
    fare: number;
    class: string;
    stops: number;
    airline: string;

   // depatureDate: string;
   // returnDate: string;
   // fromAirport: string;
   // toAirport: string;
   // fareType: string;

    constructor(values: Object={}){
        Object.assign(this, values);
    }
}
