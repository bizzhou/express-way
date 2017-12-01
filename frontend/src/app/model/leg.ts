 export class Leg {
    airline: string;
    flight_number: string;
    leg_number: number;
   fromAirport: string;
   toAirport: string;
   depatureDate: string;
   returnDate: string;

   constructor(values: Object= {}) {
        Object.assign(this, values);
    }

}
