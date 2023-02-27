/**
 * User model
 */
class User {
  constructor(data = {}) {
    this.id = null;
    this.username = null;
    this.creation_date = null;
    this.token = null;
    this.status = null;
    this.birthday = null;
    Object.assign(this, data);
  }
}
export default User;
