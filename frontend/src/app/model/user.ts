export class User {
    
    firstname: string;
    lastname: string;
    username: string;
    address: string;
    phone: string;

    constructor(values: Object={}){
        Object.assign(this, values);
    }

}
