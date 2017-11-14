export class CustomerSignUp {

    firstname: string;
    lastname: string;
    email: string;
    username: string;
    password: string;
    address: string;
    city: string;
    state: string;
    zipcode: string;
    creditcard: string;
    telephone: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

}