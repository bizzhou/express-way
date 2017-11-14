export class User {
    
    username: string;
    role: string;
    person_id: string;

    constructor(values: Object={}){
        Object.assign(this, values);
    }

}
