export class FlightSearch {

  depatureDate: string;
  returnDate: string;
  fromAirport: string;
  toAirport: string;
  fareType: string;
  classType: string;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
