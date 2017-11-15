export class Customer {

    firstname: string;
    lastname: string;
    email: string;
    username: string;
    address: string;
    city: string;
    state: string;
    zipcode: string;
    creditcard: string;
    telephone: string;
    password: string;
    personid: number;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

}