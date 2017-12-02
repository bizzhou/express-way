export class Customer {

    account_number: string;
    first_name: string;
    last_name: string;
    email: string;
    username: string;
    address: string;
    city: string;
    state: string;
    zip_code: string;
    credit_card: string;
    telephone: string;
    rating: number;
    password: string;
    id: number;


    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

}