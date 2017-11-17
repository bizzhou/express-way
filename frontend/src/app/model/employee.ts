export class Employee{

    username: string;
    password: string;
    first_name: string;
    last_name: string;
    address: string;
    city: string;
    state: string;
    zip_code: string;
    hourly_rate: number;
    telephone: string;
    ssn: string;
    id: number;
    is_manager: boolean;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }

}