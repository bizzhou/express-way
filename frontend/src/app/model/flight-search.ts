export class FlightSearch {

  depatureDate: string;
  returnDate: string;
  fromAirport: string;
  toAirport: string;
  fareType: string;
  classType: string;
  isFlexible: boolean;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
