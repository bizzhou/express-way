export class Employee{

    address: string;
    city: string;
    hourly_rate: number;
    last_name: string;
    first_name: string;
    telephone: string;
    state: string;
    zip_code: string;
    username: string;
    ssn: string;
    id: number

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

}